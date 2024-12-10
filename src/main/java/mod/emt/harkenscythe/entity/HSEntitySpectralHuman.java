package mod.emt.harkenscythe.entity;

import javax.annotation.Nonnull;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSAdvancements;
import mod.emt.harkenscythe.init.HSLootTables;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSEntitySpectralHuman extends EntityMob
{
    public HSEntitySpectralHuman(World world)
    {
        super(world);
        this.setSize(0.6F, 1.95F);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityPlayerMP)
        {
            HSAdvancements.ENCOUNTER_SPECTRAL_HUMAN.trigger((EntityPlayerMP) cause.getTrueSource());
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return HSSoundEvents.ENTITY_SPECTRAL_HUMAN_HURT.getSoundEvent();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return HSSoundEvents.ENTITY_SPECTRAL_HUMAN_HURT.getSoundEvent();
    }

    @Override
    public boolean getCanSpawnHere()
    {
        BlockPos spawnPos = new BlockPos(this);
        return super.getCanSpawnHere() && spawnPos.getY() < 50 && !this.world.canSeeSky(spawnPos);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(HSConfig.ENTITIES.spectralHumanAttackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(HSConfig.ENTITIES.spectralHumanMaxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(HSConfig.ENTITIES.spectralHumanMovementSpeed);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable()
    {
        return HSLootTables.SPECTRAL_HUMAN;
    }
}
