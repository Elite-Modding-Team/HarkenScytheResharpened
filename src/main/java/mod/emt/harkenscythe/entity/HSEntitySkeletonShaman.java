package mod.emt.harkenscythe.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

// TODO: Add actual mechanics and make it more related to souls
public class HSEntitySkeletonShaman extends AbstractSkeleton
{
    public HSEntitySkeletonShaman(World world)
    {
        super(world);
        this.setSize(0.55F, 1.8F);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
    }

    @Nonnull
    @Override
    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_STRAY_STEP;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.world.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - 0.25D,
                    this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
    }

    @Override
    public float getEyeHeight()
    {
        return 1.55F;
    }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_STRAY_AMBIENT;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return null;
    }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_STRAY_HURT;
    }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_STRAY_DEATH;
    }
}