package mod.emt.harkenscythe.entity;

import java.awt.*;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.event.HSEventLivingDeath;
import mod.emt.harkenscythe.init.HSAdvancements;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSLootTables;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.util.HSEntityBlacklists;

public class HSEntityHarbinger extends EntityCreature implements IMob
{
    public static final DataParameter<Boolean> RARE = EntityDataManager.createKey(HSEntityHarbinger.class, DataSerializers.BOOLEAN);
    private int summonCooldown;
    private int ticksInSun;

    public HSEntityHarbinger(World world)
    {
        super(world);
        this.setSize(0.8F, 2.1F);
        this.getDataManager().set(RARE, this.world.rand.nextDouble() < 0.05D);
    }

    // Immune to all negative effects
    @Override
    public boolean isPotionApplicable(PotionEffect effect)
    {
        return !effect.getPotion().isBadEffect();
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source instanceof EntityDamageSourceIndirect)
        {
            for (int i = 0; i < 64; ++i)
            {
                if (this.teleportRandomly())
                {
                    return true;
                }
            }

            return false;
        }

        if (source == DamageSource.CACTUS) return false;

        if (source.getTrueSource() != null && source.getTrueSource().getEntityData().getBoolean("IsSpectral")) return false;

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityPlayerMP)
        {
            HSAdvancements.ENCOUNTER_HARBINGER.trigger((EntityPlayerMP) cause.getTrueSource());
        }
    }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return HSSoundEvents.ENTITY_HARBINGER_HURT.getSoundEvent();
    }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound()
    {
        return HSSoundEvents.ENTITY_HARBINGER_DEATH.getSoundEvent();
    }

    @Override
    protected SoundEvent getFallSound(int heightIn)
    {
        return null;
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        // No fall damage
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
    }

    protected boolean teleportRandomly()
    {
        double x = this.posX + (this.rand.nextDouble() - 0.5) * 8.0;
        double y = this.posY + (this.rand.nextInt(8) - 4);
        double z = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0;
        return this.teleportTo(x, y, z);
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        // No step sounds
    }

    @Override
    public boolean isNonBoss()
    {
        return false;
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

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(HSConfig.ENTITIES.harbingerArmorValue);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(HSConfig.ENTITIES.harbingerAttackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(HSConfig.ENTITIES.harbingerFollowRange);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(HSConfig.ENTITIES.harbingerMaxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(HSConfig.ENTITIES.harbingerMovementSpeed);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(RARE, false);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote)
        {
            if (this.rand.nextInt(100) == 0 && !this.isSilent())
            {
                this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, HSSoundEvents.RANDOM_SUMMON.getSoundEvent(), this.getSoundCategory(), 0.5F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }

            if (FMLLaunchHandler.side().isClient())
            {
                for (int i = 0; i < 2; i++)
                {
                    HSParticleHandler.spawnDarkColoredParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.getDataManager().get(HSEntityHarbinger.RARE) ? Color.getColor("Harken Red", 7614014) : Color.getColor("Harbinger Black", 1907997), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound()
    {
        return this.world.isDaytime() ? SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE : HSSoundEvents.ENTITY_HARBINGER_IDLE.getSoundEvent();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Rare", this.getDataManager().get(RARE));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.getDataManager().set(RARE, compound.getBoolean("Rare"));
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable()
    {
        return this.getDataManager().get(HSEntityHarbinger.RARE) ? HSLootTables.HARBINGER_RARE : HSLootTables.HARBINGER;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (this.world.isRemote) return;
        // Check if there is no attack target and reap mobs when no player is nearby
        if (this.getAttackTarget() == null /*&& !reapPlayer()*/)
        {
            reapForFun();
        }
        // Randomly summon vexes
        else if (this.world.rand.nextInt(100) == 0)
        {
            summonVexes();
        }
        // Engage Rush Phase when health is between 75-100%
        else if (this.getHealth() >= this.getMaxHealth() * 0.75)
        {
            onRushPhase();
        }
        // Engage Dodge Phase when health is between 50-75%
        else if (this.getHealth() >= this.getMaxHealth() * 0.5)
        {
            onDodgePhase();
        }
        // Engage Minion Phase when health is between 25-50%
        else if (this.getHealth() >= this.getMaxHealth() * 0.25)
        {
            onMinionPhase();
        }
        // Engage Sneak Phase when health is between 0-25%
        else
        {
            onSneakPhase();
        }
        // Reset attack target if the target is spectral
        if (this.getAttackTarget() != null && this.getAttackTarget().getEntityData().getBoolean("IsSpectral"))
        {
            this.setAttackTarget(null);
            this.setRevengeTarget(null);
            this.setLastAttackedEntity(null);
        }
        // Decrement summon cooldown
        if (this.summonCooldown > 0)
        {
            this.summonCooldown--;
        }
        // Reset attack target and increment sun exposure ticks if it is daytime and the Harbinger is in the open
        if (this.world.isDaytime() && this.world.canSeeSky(this.getPosition()))
        {
            this.setAttackTarget(null);
            this.setRevengeTarget(null);
            this.setLastAttackedEntity(null);
            this.ticksInSun++;
        }
        // If sun exposure exceeds 1200 ticks (1 minute), create smoke particles and despawn
        if (world.getDifficulty() == EnumDifficulty.PEACEFUL || this.ticksInSun > 1200)
        {
            // TODO: Better looking smoke particles
            this.spawnExplosionParticle();
            this.setDead();
        }
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(this.getDataManager().get(HSEntityHarbinger.RARE) ? HSItems.lady_harken_scythe : HSItems.reaper_scythe));
        this.setDropChance(EntityEquipmentSlot.MAINHAND, -100.0F);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        if (HSConfig.GENERAL.debugMode) HarkenScythe.LOGGER.debug("{} spawned at {}", this.getDisplayName(), this.getPosition());
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    private boolean isValidLightLevel()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering())
            {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    private boolean teleportTo(double x, double y, double z)
    {
        EnderTeleportEvent event = new EnderTeleportEvent(this, x, y, z, 0);
        if (MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

        if (flag)
        {
            this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        }

        return flag;
    }

    /**
     * Attempts to reap a player by setting the attack target to the nearest player within 20 blocks.
     *
     * @return true if the attack target was set to a valid player, false otherwise.
     */
    private boolean reapPlayer()
    {
        // TODO: Only attack players if they have certain Harken Scythe items like filled containers and essence items
        if (this.world.isDaytime()) return false;
        EntityPlayer nearestPlayer = this.world.getClosestPlayerToEntity(this, HSConfig.ENTITIES.harbingerSearchDistance);
        if (nearestPlayer == null || nearestPlayer.isCreative() || nearestPlayer.getIsInvulnerable()) nearestPlayer = null;
        this.setAttackTarget(nearestPlayer);
        return this.getAttackTarget() instanceof EntityPlayer;
    }

    /**
     * Attempts to reap a nearby whitelisted entity by setting it as an attack target.
     */
    private void reapForFun()
    {
        if (this.world.isDaytime()) return;
        List<Entity> nearbyEntities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(HSConfig.ENTITIES.harbingerSearchDistance));
        for (Entity entity : nearbyEntities)
        {
            if (entity instanceof EntityLivingBase && isWhitelistedEntity(entity))
            {
                this.setAttackTarget((EntityLivingBase) entity);
                break;
            }
        }
    }

    /**
     * Checks if the provided entity is a whitelisted entity.
     *
     * @param entity the entity to check
     * @return true if the entity is not blacklisted, false otherwise.
     */
    private boolean isWhitelistedEntity(Entity entity)
    {
        return !HSEntityBlacklists.isBlacklistedForHarbingerReaping(entity) && !entity.getEntityData().getBoolean("IsSpectral") && (entity instanceof EntityVillager || entity instanceof EntityAnimal);
    }

    /**
     * Phase 1: Rush towards player and attack logic.
     * It checks if the Harbinger has an attack target and if it is within a certain distance.
     * If the conditions are met, it makes the Harbinger move towards the target at a speed of 1.2.
     */
    private void onRushPhase()
    {
        if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) > 1.0)
        {
            this.getNavigator().tryMoveToEntityLiving(this.getAttackTarget(), 1.2);
        }
    }

    /**
     * Phase 2: Teleport frequently to dodge attacks.
     * The logic is run every 20 ticks (every second).
     * If the conditions are met, it teleports the entity to a random position within a certain range.
     */
    private void onDodgePhase()
    {
        if (!this.world.isRemote && this.ticksExisted % 20 == 0)
        {
            double x = this.posX + (this.rand.nextDouble() - 0.5) * 8.0;
            double y = this.posY + (this.rand.nextInt(8) - 4);
            double z = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0;
            this.attemptTeleport(x, y, z);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        }
    }

    /**
     * Phase 3: Spawn spectral minions.
     * The logic is run every 200 ticks (every 10 seconds).
     * If the conditions are met, it creates and spawns 4 spectral minions at random positions around the Harbinger.
     */
    private void onMinionPhase()
    {
        if (this.ticksExisted % 200 == 0)
        {
            this.swingArm(EnumHand.MAIN_HAND);
            this.swingArm(EnumHand.OFF_HAND);
            for (int i = 0; i < 4; i++)
            {
                double angle = Math.toRadians(i * 90D);
                double x = this.posX + 2.0 * Math.cos(angle);
                double z = this.posZ + 2.0 * Math.sin(angle);
                if (!this.world.isRemote) HSEventLivingDeath.spawnSpectralEntity(this.world, this.rand.nextBoolean() ? new EntitySkeleton(this.world) : new EntityZombie(this.world), new BlockPos(x, this.posY, z), false);
            }
            this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
        }
    }

    /**
     * Phase 4: Gain invisibility and continue Phase 2 attack pattern.
     * If the Harbinger does not have the invisibility potion effect, it adds it.
     * Then it proceeds to the dodge phase behavior.
     */
    private void onSneakPhase()
    {
        if (this.getActivePotionEffect(MobEffects.INVISIBILITY) == null)
        {
            this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 100, 1, false, false));
        }
        onDodgePhase();
    }

    /**
     * Summons a random number of Vexes around the Harbinger.
     * The amount of Vexes summoned is between 1 and 2.
     * They are given a random position within a 5x5x5 cube centered on the Harbinger, a limited life between 30-120 seconds and an Iron Scythe as their held item.
     * The summon cooldown is set to 2400 ticks (120 seconds) after summoning.
     */
    private void summonVexes()
    {
        if (!this.world.isRemote && this.summonCooldown <= 0)
        {
            this.swingArm(EnumHand.MAIN_HAND);
            this.swingArm(EnumHand.OFF_HAND);
            int amount = 1 + this.world.rand.nextInt(2);
            for (int i = 0; i < amount; i++)
            {
                BlockPos blockPos = (new BlockPos(this)).add(-2 + this.rand.nextInt(5), 1, -2 + this.rand.nextInt(5));
                EntityVex entityVex = new EntityVex(this.world);
                entityVex.moveToBlockPosAndAngles(blockPos, 0.0F, 0.0F);
                entityVex.onInitialSpawn(this.world.getDifficultyForLocation(blockPos), null);
                entityVex.setBoundOrigin(blockPos);
                entityVex.setLimitedLife(20 * (30 + this.rand.nextInt(90)));
                entityVex.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(HSItems.iron_scythe));
                this.world.spawnEntity(entityVex);
            }
        }
        this.summonCooldown = 2400;
    }
}
