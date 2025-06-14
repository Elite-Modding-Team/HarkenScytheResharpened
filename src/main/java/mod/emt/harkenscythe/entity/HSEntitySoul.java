package mod.emt.harkenscythe.entity;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.util.List;
import java.util.stream.Collectors;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.event.HSEventLivingDeath;
import mod.emt.harkenscythe.init.HSEnumFaction;
import mod.emt.harkenscythe.init.HSMaterials;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.armor.HSArmor;
import mod.emt.harkenscythe.item.tool.IHSTool;
import mod.emt.harkenscythe.network.HSNetworkHandler;
import mod.emt.harkenscythe.network.packet.HSEssenceTypePacket;
import mod.emt.harkenscythe.init.HSAttributes;

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

        if (entity instanceof HSEntitySpectralMiner) // Spectral Miner
        {
            soulType = 4; // Spectral
        }
        else if (!entity.isNonBoss() && entity.getMaxHealth() >= HSConfig.ENTITIES.essenceSoulWrathfulMaxHealth) // Boss with 300 max health or more
        {
            soulType = 3; // Wrathful
        }
        else if (entity.getMaxHealth() >= HSConfig.ENTITIES.essenceSoulCulledMaxHealth || !entity.isNonBoss()) // Mobs with 100 max health or more, bosses with less than 300 health
        {
            soulType = 2; // Culled
        }
        else if ((!world.isDaytime() && world.getCurrentMoonPhaseFactor() == 0.0F) || entity instanceof EntityPlayer || entity.getMaxHealth() >= HSConfig.ENTITIES.essenceSoulGrievingMaxHealth) // During new moons, players, mobs with 40 max health or more
        {
            soulType = 1; // Grieving
        }
        else // Anything else
        {
            soulType = 0; // Common
        }

        if (soulType < 2)
        {
            DamageSource lastDmgSource = entity.getLastDamageSource();
            if (lastDmgSource != null && lastDmgSource.getTrueSource() instanceof EntityLivingBase)
            {
                EntityLivingBase trueSource = (EntityLivingBase) lastDmgSource.getTrueSource();
                IAttributeInstance essenceAlteration = trueSource.getEntityAttribute(HSAttributes.ESSENCE_ALTERATION);
                if (essenceAlteration != null && !essenceAlteration.getModifiers().isEmpty())
                {
                    double essenceAlterationChance = 0.0D;
                    for (AttributeModifier attributemodifier : essenceAlteration.getModifiers())
                    {
                        essenceAlterationChance += attributemodifier.getAmount();
                    }
                    if (world.rand.nextDouble() < essenceAlterationChance)
                    {
                        soulType = Math.min(soulType + 1, 4);
                    }
                }
            }
        }

        this.getDataManager().set(SOUL_TYPE, soulType);

        if (FMLLaunchHandler.side().isClient() && !this.world.isRemote)
        {
            HSNetworkHandler.instance.sendToAllTracking(new HSEssenceTypePacket(this.getEntityId(), soulType), this);
        }
    }

    public int getSoulQuantity()
    {
        switch (this.getDataManager().get(SOUL_TYPE))
        {
            case 1: // Grieving (2)
                return HSConfig.ENTITIES.essenceSoulGrievingValue;
            case 2: // Culled (5)
                return HSConfig.ENTITIES.essenceSoulCulledValue;
            case 3: // Wrathful (40)
                return HSConfig.ENTITIES.essenceSoulWrathfulValue;
            case 4: // Spectral (20)
                return HSConfig.ENTITIES.essenceSoulSpectralValue;
            default: // Common (1)
                return HSConfig.ENTITIES.essenceSoulCommonValue;
        }
    }

    public void playLivingSound()
    {
        SoundEvent soundEvent = getAmbientSound();
        if (soundEvent != null)
        {
            playSound(soundEvent, getSoundVolume(), getSoundPitch());
        }
    }

    public void repairEquipment(ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            stack.setItemDamage(stack.getItemDamage() - this.getSoulQuantity());
        }
    }

    public ItemStack getRandomDamagedLivingmetalEquipment(EntityPlayer player)
    {
        List<ItemStack> equipmentList = this.getDamagedPlayerEquipment(player);
        equipmentList = equipmentList.stream().filter(stack -> isLivingmetalItem(stack.getItem())).collect(Collectors.toList());
        return equipmentList.isEmpty() ? ItemStack.EMPTY : equipmentList.get(player.getRNG().nextInt(equipmentList.size()));
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
        if (isEntityAlive() && this.ticksExisted % 200 == 0 && this.rand.nextBoolean())
        {
            playLivingSound();
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source.getTrueSource() instanceof HSEntityHarbinger)
        {
            this.setHealth(0);
            ((EntityLivingBase) source.getTrueSource()).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 5 * 20, 0));
            HSEventLivingDeath.spawnSpectralEntity(this.world, this.getOriginalEntity(), this.getPosition(), true);
            return true;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected HSEnumFaction getFaction()
    {
        return HSEnumFaction.SOUL;
    }

    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return getOriginalEntity() instanceof HSEntitySpectralMiner ? SoundEvents.AMBIENT_CAVE : null;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(SOUL_TYPE, 0);
    }

    @Override
    protected void onDeathUpdate()
    {
        super.onDeathUpdate();
        if (this.deathTime == 1) this.world.playSound(null, this.getPosition(), HSSoundEvents.ENTITY_ESSENCE_SOUL_DESPAWN.getSoundEvent(), SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
        return this.getSoulQuantity();
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
        this.getDataManager().set(SOUL_TYPE, compound.getInteger("SoulType"));
        if (compound.hasKey("OriginalEntity"))
        {
            NBTTagCompound originalEntityNBT = compound.getCompoundTag("OriginalEntity");
            Entity entityFromNBT = EntityList.createEntityFromNBT(originalEntityNBT, this.world);
            if (entityFromNBT instanceof EntityLivingBase) this.setOriginalEntity((EntityLivingBase) entityFromNBT);
        }
    }

    private List<ItemStack> getDamagedPlayerEquipment(EntityPlayer player)
    {
        List<ItemStack> list = Lists.newArrayList();
        for (int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.isItemDamaged())
            {
                list.add(stack);
            }
        }
        return list;
    }

    private boolean isLivingmetalItem(Item item)
    {
        return (item instanceof HSArmor && ((HSArmor) item).getArmorMaterial() == HSMaterials.ARMOR_LIVINGMETAL) || (item instanceof IHSTool && ((IHSTool) item).getToolMaterial() == HSMaterials.TOOL_LIVINGMETAL);
    }
}
