package mod.emt.harkenscythe.entity;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSAdvancements;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSLootTables;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSEntityEctoglobin extends HSEntityGlobin
{
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.createKey(HSEntityEctoglobin.class, DataSerializers.VARINT);

    public HSEntityEctoglobin(World world)
    {
        super(world);
    }

    public int getSkin()
    {
        return this.dataManager.get(SKIN_TYPE);
    }

    public void setSkin(int skinType)
    {
        this.dataManager.set(SKIN_TYPE, skinType);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityPlayerMP)
        {
            HSAdvancements.ENCOUNTER_ECTOGLOBIN.trigger((EntityPlayerMP) cause.getTrueSource());
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, this.rand.nextInt(3));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("Variant", getSkin());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        setSkin(nbt.getInteger("Variant"));
    }

    @Override
    protected HSEntityEctoglobin createInstance()
    {
        return new HSEntityEctoglobin(this.world);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return HSSoundEvents.ENTITY_ECTOGLOBIN_IDLE.getSoundEvent();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return HSSoundEvents.ENTITY_ECTOGLOBIN_IDLE.getSoundEvent();
    }

    @Override
    protected SoundEvent getSquishSound()
    {
        return HSSoundEvents.BLOCK_BIOMASS_HARVEST.getSoundEvent();
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable()
    {
        return HSLootTables.ECTOGLOBIN;
    }

    @Override
    protected SoundEvent getJumpSound()
    {
        return HSSoundEvents.ENTITY_ECTOGLOBIN_IDLE.getSoundEvent();
    }

    @Override
    protected boolean spawnCustomParticles()
    {
        int i = this.getSlimeSize();
        for (int j = 0; j < i * 8; j++)
        {
            float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
            float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
            float f2 = MathHelper.sin(f) * (float) i * 0.5F * f1;
            float f3 = MathHelper.cos(f) * (float) i * 0.5F * f1;
            double d0 = this.posX + (double) f2;
            double d1 = this.posZ + (double) f3;
            this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, d0, this.getEntityBoundingBox().minY, d1, 0.0D, 0.0D, 0.0D, Item.getIdFromItem(HSItems.soul_essence));
        }
        return true;
    }
}
