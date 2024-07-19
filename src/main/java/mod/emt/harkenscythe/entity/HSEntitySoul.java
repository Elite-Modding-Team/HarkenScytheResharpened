package mod.emt.harkenscythe.entity;

import java.util.List;
import java.util.stream.Collectors;
import mod.emt.harkenscythe.event.HSEventLivingDeath;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.item.armor.HSArmor;
import mod.emt.harkenscythe.item.tools.IHSTool;
import mod.emt.harkenscythe.network.HSNetworkHandler;
import mod.emt.harkenscythe.network.packet.HSSoulTypePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class HSEntitySoul extends HSEntityEssence
{
    public static final DataParameter<Integer> SOUL_TYPE = EntityDataManager.createKey(HSEntitySoul.class, DataSerializers.VARINT);
    private EntityLivingBase originalEntity;

    public HSEntitySoul(World world)
    {
        super(world);
        this.setSize(1.2F, 1.2F);
        if (this.getOriginalEntity() == null) this.setOriginalEntity(new HSEntityEctoglobin(world));
    }

    public HSEntitySoul(World world, EntityLivingBase entity)
    {
        super(world);
        this.setSize(1.2F, 1.2F);
        this.setOriginalEntity(entity);
        this.setSoulType(entity);
    }

    public EntityLivingBase getOriginalEntity()
    {
        return this.originalEntity;
    }

    public void setOriginalEntity(EntityLivingBase originalEntity)
    {
        this.originalEntity = originalEntity;
    }

    public void setSoulType(EntityLivingBase entity)
    {
        int soulType;

        if (entity instanceof HSEntitySpectralMiner)
        {
            soulType = 4; // Spectral
        }
        else if (!entity.isNonBoss())
        {
            soulType = 3; // Wrathful
        }
        else if (!world.isDaytime() && world.getCurrentMoonPhaseFactor() == 1.0F)
        {
            soulType = 2; // Culled
        }
        else if (entity instanceof EntityPlayer)
        {
            soulType = 1; // Grieving
        }
        else
        {
            soulType = 0; // Common
        }

        this.dataManager.set(SOUL_TYPE, soulType);

        if (!this.world.isRemote)
        {
            HSNetworkHandler.instance.sendToAllTracking(new HSSoulTypePacket(this.getEntityId(), soulType), this);
        }
    }

    public int getSoulQuantity()
    {
        switch (this.getDataManager().get(SOUL_TYPE))
        {
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
            case 4:
                return 40;
            default:
                return 1;
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source.getTrueSource() instanceof HSEntityHarbinger)
        {
            this.setDead();
            HSEventLivingDeath.spawnSpectralEntity(this.world, this.getOriginalEntity(), this.getPosition(), true);
            return true;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();
        if (item == HSItems.essence_keeper || item == HSItems.essence_vessel)
        {
            stack.shrink(1);
            ItemStack newStack = item == HSItems.essence_keeper ? new ItemStack(HSItems.essence_keeper_soul) : new ItemStack(HSItems.essence_vessel_soul);
            newStack.setItemDamage(newStack.getMaxDamage() - this.getSoulQuantity());
            player.setHeldItem(hand, newStack);
            this.repairEquipment(this.getRandomDamagedLivingmetalEquipment(player));
            float pitch = newStack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) newStack.getItemDamage() / newStack.getMaxDamage() * 0.5F);
            if (newStack.getItem() == HSItems.essence_keeper_soul) pitch += 0.5F;
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, pitch);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            this.setDead();
        }
        else if (item == HSItems.essence_keeper_soul || item == HSItems.essence_vessel_soul)
        {
            if (stack.getItemDamage() == 0) return false;
            if (stack.getItemDamage() > 0)
            {
                stack.setItemDamage(stack.getItemDamage() - this.getSoulQuantity());
            }
            if (stack.getItemDamage() <= 0)
            {
                stack.shrink(1);
                ItemStack newStack = item == HSItems.essence_keeper_soul ? new ItemStack(HSItems.essence_keeper_soul) : new ItemStack(HSItems.essence_vessel_soul);
                player.setHeldItem(hand, newStack);
            }
            this.repairEquipment(this.getRandomDamagedLivingmetalEquipment(player));
            float pitch = stack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) stack.getItemDamage() / stack.getMaxDamage() * 0.5F);
            if (stack.getItem() == HSItems.essence_keeper_soul) pitch += 0.5F;
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, pitch);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            this.setDead();
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SOUL_TYPE, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("SoulType", this.getDataManager().get(SOUL_TYPE));
        if (this.getOriginalEntity() != null)
        {
            NBTTagCompound originalEntityNBT = new NBTTagCompound();
            this.getOriginalEntity().writeToNBT(originalEntityNBT);
            originalEntityNBT.setString("id", EntityList.getKey(this.getOriginalEntity().getClass()).toString());
            originalEntityNBT.setString("name", this.getOriginalEntity().getName());
            compound.setTag("OriginalEntity", originalEntityNBT);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.dataManager.set(SOUL_TYPE, compound.getInteger("SoulType"));
        if (compound.hasKey("OriginalEntity"))
        {
            NBTTagCompound originalEntityNBT = compound.getCompoundTag("OriginalEntity");
            Entity entityFromNBT = EntityList.createEntityFromNBT(originalEntityNBT, this.world);
            if (entityFromNBT instanceof EntityLivingBase) this.setOriginalEntity((EntityLivingBase) entityFromNBT);
        }
    }

    private ItemStack getRandomDamagedLivingmetalEquipment(EntityPlayer player)
    {
        List<ItemStack> equipmentList = this.getDamagedEntityEquipment(player);
        equipmentList = equipmentList.stream().filter(stack -> isLivingmetalItem(stack.getItem())).collect(Collectors.toList());
        return equipmentList.isEmpty() ? ItemStack.EMPTY : equipmentList.get(player.getRNG().nextInt(equipmentList.size()));
    }

    private boolean isLivingmetalItem(Item item)
    {
        return (item instanceof HSArmor && ((HSArmor) item).getArmorMaterial() == HSItems.ARMOR_LIVINGMETAL) || (item instanceof IHSTool && ((IHSTool) item).getToolMaterial() == HSItems.TOOL_LIVINGMETAL);
    }
}
