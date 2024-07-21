package mod.emt.harkenscythe.entity;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class HSEntityHarbinger extends EntityMob
{
    public HSEntityHarbinger(World world)
    {
        super(world);
        this.setSize(0.8F, 1.8F);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (this.getAttackTarget() == null && !reapPlayer())
        {
            reapForFun();
        }
        else if (this.getHealth() > this.getMaxHealth() * 0.75)
        {
            onRushPhase();
        }
        else if (this.getHealth() > this.getMaxHealth() * 0.5)
        {
            onDodgePhase();
        }
        else if (this.getHealth() > this.getMaxHealth() * 0.25)
        {
            onSoulPhase();
        }
        else
        {
            onSneakPhase();
        }
        if (this.getAttackTarget() != null && this.getAttackTarget().getEntityData().getBoolean("IsSpectral"))
        {
            this.setAttackTarget(null);
        }
    }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_WITHER_SPAWN;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, HSEntitySoul.class, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED;
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable()
    {
        return HSLootTables.HARBINGER;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(HSItems.reaper_scythe));
        this.setDropChance(EntityEquipmentSlot.MAINHAND, 0.0F);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        if (HSConfig.GENERAL_SETTINGS.debug) HarkenScythe.LOGGER.debug(this.getDisplayName() + " spawned at " + this.getPosition());
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    private boolean reapPlayer()
    {
        EntityPlayer nearestPlayer = this.world.getClosestPlayerToEntity(this, 20.0D);
        if (nearestPlayer == null || nearestPlayer.isCreative() || nearestPlayer.getIsInvulnerable()) nearestPlayer = null;
        this.setAttackTarget(nearestPlayer);
        return this.getAttackTarget() instanceof EntityPlayer;
    }

    private boolean reapForFun()
    {
        List<Entity> nearbyEntities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(20.0D));
        for (Entity entity : nearbyEntities)
        {
            if (entity instanceof EntityLivingBase && isWhitelistedMob(entity))
            {
                this.setAttackTarget((EntityLivingBase) entity);
                break;
            }
        }
        return this.getAttackTarget() != null;
    }

    private boolean isWhitelistedMob(Entity entity)
    {
        // TODO: Replace with config-defined whitelist
        return entity instanceof EntityVillager || entity instanceof EntityAnimal;
    }

    // Phase 1: Rush towards player and attack logic
    private void onRushPhase()
    {
        if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) > 1.0)
        {
            this.getNavigator().tryMoveToEntityLiving(this.getAttackTarget(), 1.2);
        }
    }

    // Phase 2: Teleport frequently to dodge attacks
    private void onDodgePhase()
    {
        if (!this.world.isRemote && this.ticksExisted % 20 == 19) // Roughly every second
        {
            double x = this.posX + (this.rand.nextDouble() - 0.5) * 8.0;
            double y = this.posY + (this.rand.nextInt(8) - 4);
            double z = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0;
            this.attemptTeleport(x, y, z);
        }
    }

    // Phase 3: Drop and revive souls
    private void onSoulPhase()
    {
        if (this.ticksExisted % 200 == 199) // Roughly every 10 seconds
        {
            for (int i = 0; i < 4; i++)
            {
                EntityLivingBase soul = new HSEntitySoul(this.world);
                double angle = Math.toRadians(i * 90);
                double x = this.posX + 2.0 * Math.cos(angle);
                double z = this.posZ + 2.0 * Math.sin(angle);
                soul.setPosition(x, this.posY, z);
                if (!this.world.isRemote) this.world.spawnEntity(soul);
            }
            this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
        }
    }

    // Phase 4: Gain invisibility and continue Phase 2 attack pattern
    private void onSneakPhase()
    {
        if (this.getActivePotionEffect(MobEffects.INVISIBILITY) == null)
        {
            this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 100, 1, false, false));
        }
        onDodgePhase();
    }
}
