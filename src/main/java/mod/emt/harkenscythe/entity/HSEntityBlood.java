package mod.emt.harkenscythe.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.network.HSNetworkHandler;
import mod.emt.harkenscythe.network.packet.HSEssenceTypePacket;

public class HSEntityBlood extends HSEntityEssence
{
    public static final DataParameter<Integer> BLOOD_TYPE = EntityDataManager.createKey(HSEntityBlood.class, DataSerializers.VARINT);

    public HSEntityBlood(World world)
    {
        super(world);
        this.setSize(0.8F, 0.8F);
    }

    public HSEntityBlood(World world, EntityLivingBase entity)
    {
        this(world);
        this.setBloodType(entity);
    }

    public void setBloodType(EntityLivingBase entity)
    {
        int bloodType;

        if (entity.getHealth() > entity.lastDamage)
        {
            bloodType = 0; // Common
        }
        else if (!entity.isNonBoss() && entity.getMaxHealth() >= HSConfig.ENTITIES.essenceBloodWarpedMaxHealth) // Boss with 300 max health or more
        {
            bloodType = 3; // Warped
        }
        else if (entity.getMaxHealth() >= HSConfig.ENTITIES.essenceBloodIntoxicatedMaxHealth || !entity.isNonBoss()) // Mobs with 100 max health or more, bosses with less than 300 health
        {
            bloodType = 2; // Intoxicated
        }
        else if ((!world.isDaytime() && world.getCurrentMoonPhaseFactor() == 0.0F) || entity instanceof EntityPlayer || entity.getMaxHealth() >= HSConfig.ENTITIES.essenceBloodSicklyMaxHealth) // During new moons, players, mobs with 40 max health or more
        {
            bloodType = 1; // Sickly
        }
        else // Anything else
        {
            bloodType = 0; // Common
        }

        this.getDataManager().set(BLOOD_TYPE, bloodType);

        if (FMLLaunchHandler.side().isClient() && !this.world.isRemote)
        {
            HSNetworkHandler.instance.sendToAllTracking(new HSEssenceTypePacket(this.getEntityId(), bloodType), this);
        }
    }

    public int getBloodQuantity()
    {
        switch (this.getDataManager().get(BLOOD_TYPE))
        {
            case 1: // Sickly (2)
                return HSConfig.ENTITIES.essenceBloodSicklyValue;
            case 2: // Intoxicated (5)
                return HSConfig.ENTITIES.essenceBloodIntoxicatedValue;
            case 3: // Warped (40)
                return HSConfig.ENTITIES.essenceBloodWarpedValue;
            default: // Common (1)
                return HSConfig.ENTITIES.essenceBloodCommonValue;
        }
    }

    // TODO: Streamline this with soul essence interaction
    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (!this.isDead && this.getHealth() > 0)
        {
            ItemStack stack = player.getHeldItem(hand);
            Item item = stack.getItem();
            if (item == HSItems.essence_keeper || item == HSItems.essence_vessel)
            {
                stack.shrink(1);
                ItemStack newStack = item == HSItems.essence_keeper ? new ItemStack(HSItems.essence_keeper_blood) : new ItemStack(HSItems.essence_vessel_blood);
                newStack.setItemDamage(newStack.getMaxDamage() - this.getBloodQuantity());
                player.setHeldItem(hand, newStack);
                float pitch = newStack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) newStack.getItemDamage() / newStack.getMaxDamage() * 0.5F);
                if (newStack.getItem() == HSItems.essence_keeper_blood) pitch += 0.5F;
                this.world.playSound(null, player.getPosition(), HSSoundEvents.ITEM_ESSENCE_BOTTLE.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, pitch);
                this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
                this.setHealth(0);
            }
            else if (item == HSItems.essence_keeper_blood || item == HSItems.essence_vessel_blood)
            {
                if (stack.getItemDamage() == 0) return false;
                if (stack.getItemDamage() > 0)
                {
                    stack.setItemDamage(stack.getItemDamage() - this.getBloodQuantity());
                }
                if (stack.getItemDamage() <= 0)
                {
                    stack.shrink(1);
                    ItemStack newStack = item == HSItems.essence_keeper_blood ? new ItemStack(HSItems.essence_keeper_blood) : new ItemStack(HSItems.essence_vessel_blood);
                    player.setHeldItem(hand, newStack);
                }
                float pitch = stack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) stack.getItemDamage() / stack.getMaxDamage() * 0.5F);
                if (stack.getItem() == HSItems.essence_keeper_blood) pitch += 0.5F;
                this.world.playSound(null, player.getPosition(), HSSoundEvents.ITEM_ESSENCE_BOTTLE.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, pitch);
                this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
                this.recentlyHit = 60;
                this.setHealth(0);
            }
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(BLOOD_TYPE, 0);
    }

    @Override
    protected void onDeathUpdate()
    {
        super.onDeathUpdate();
        if (this.deathTime == 1) this.world.playSound(null, this.getPosition(), HSSoundEvents.ENTITY_ESSENCE_BLOOD_DESPAWN.getSoundEvent(), SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
        return this.getBloodQuantity();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("BloodType", this.getDataManager().get(BLOOD_TYPE));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.getDataManager().set(BLOOD_TYPE, compound.getInteger("BloodType"));
    }
}
