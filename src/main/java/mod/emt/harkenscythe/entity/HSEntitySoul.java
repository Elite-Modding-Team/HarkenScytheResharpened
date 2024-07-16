package mod.emt.harkenscythe.entity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import mod.emt.harkenscythe.event.HSEventLivingDeath;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.item.armor.HSArmor;
import mod.emt.harkenscythe.item.tools.IHSTool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class HSEntitySoul extends HSEntityEssence implements IEntityAdditionalSpawnData
{
    private EntityLivingBase originalEntity;
    private GameProfile playerGameProfile;
    private int soulType;

    public HSEntitySoul(World world)
    {
        super(world);
        this.setSize(1.2F, 1.2F);
        if (this.getOriginalEntity() == null) this.setOriginalEntity(new HSEntityEctoglobin(world));
        this.setSoulType(0);
    }

    public HSEntitySoul(World world, EntityLivingBase entity)
    {
        super(world);
        this.setSize(1.2F, 1.2F);
        this.setOriginalEntity(entity);
        this.setSoulType(entity);
        if (entity instanceof EntityPlayer)
        {
            this.setPlayerGameProfile(((EntityPlayer) entity).getGameProfile());
        }
    }

    public EntityLivingBase getOriginalEntity()
    {
        return this.originalEntity;
    }

    public void setOriginalEntity(EntityLivingBase originalEntity)
    {
        this.originalEntity = originalEntity;
    }

    public GameProfile getPlayerGameProfile()
    {
        return this.playerGameProfile;
    }

    public void setPlayerGameProfile(GameProfile playerGameProfile)
    {
        this.playerGameProfile = playerGameProfile;
    }

    public int getSoulType()
    {
        return this.soulType;
    }

    public void setSoulType(int soulType)
    {
        this.soulType = soulType;
    }

    public void setSoulType(EntityLivingBase entity)
    {
        if (!entity.isNonBoss())
        {
            this.soulType = 3; // Wrathful
        }
        else if (!world.isDaytime() && world.getCurrentMoonPhaseFactor() == 1.0F)
        {
            this.soulType = 2; // Culled
        }
        else if (entity instanceof EntityPlayer)
        {
            this.soulType = 1; // Grieving
        }
        else
        {
            this.soulType = 0; // Common
        }
    }

    public int getSoulQuantity()
    {
        switch (this.getSoulType())
        {
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
                return 50;
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
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("SoulType", this.getSoulType());
        if (this.getOriginalEntity() != null)
        {
            NBTTagCompound originalEntityNBT = new NBTTagCompound();
            this.getOriginalEntity().writeToNBT(originalEntityNBT);
            if (this.getOriginalEntity() instanceof EntityPlayer)
            {
                originalEntityNBT.setString("id", ((EntityPlayer) this.getOriginalEntity()).getGameProfile().getId().toString());
                originalEntityNBT.setString("name", ((EntityPlayer) this.getOriginalEntity()).getGameProfile().getName());
            }
            else
            {
                originalEntityNBT.setString("id", EntityList.getKey(this.getOriginalEntity().getClass()).toString());
                originalEntityNBT.setString("name", this.getOriginalEntity().getName());
            }
            compound.setTag("OriginalEntity", originalEntityNBT);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setSoulType(compound.getInteger("SoulType"));
        if (compound.hasKey("OriginalEntity"))
        {
            NBTTagCompound originalEntityNBT = compound.getCompoundTag("OriginalEntity");
            Entity entityFromNBT = EntityList.createEntityFromNBT(originalEntityNBT, this.world);
            if (entityFromNBT instanceof EntityLivingBase) this.setOriginalEntity((EntityLivingBase) entityFromNBT);
            if (entityFromNBT instanceof EntityPlayer)
            {
                UUID uuid = UUID.fromString(originalEntityNBT.getString("id"));
                String name = originalEntityNBT.getString("name");
                this.setPlayerGameProfile(new GameProfile(uuid, name));
            }
        }
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        data.writeInt(this.getSoulType());
        if (this.getOriginalEntity() instanceof EntityPlayer)
        {
            ByteBufUtils.writeUTF8String(data, ((EntityPlayer) this.getOriginalEntity()).getGameProfile().getId().toString());
            ByteBufUtils.writeUTF8String(data, ((EntityPlayer) this.getOriginalEntity()).getGameProfile().getName());
        }
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        this.setSoulType(data.readInt());
        if (this.getPlayerGameProfile() != null)
        {
            UUID uuid = UUID.fromString(ByteBufUtils.readUTF8String(data));
            String name = ByteBufUtils.readUTF8String(data);
            this.setPlayerGameProfile(new GameProfile(uuid, name));
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
