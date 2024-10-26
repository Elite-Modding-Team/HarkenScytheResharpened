package mod.emt.harkenscythe.entity;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSAdvancements;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSLootTables;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSEntitySpectralMiner extends EntityMob
{
    public HSEntitySpectralMiner(World world)
    {
        super(world);
        this.setSize(0.6F, 1.95F);
        this.enablePersistence();
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityPlayerMP)
        {
            HSAdvancements.ENCOUNTER_SPECTRAL_MINER.trigger((EntityPlayerMP) cause.getTrueSource());
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return HSSoundEvents.ENTITY_SPECTRAL_MINER_HURT.getSoundEvent();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return HSSoundEvents.ENTITY_SPECTRAL_MINER_DEATH.getSoundEvent();
    }

    @Override
    public boolean getCanSpawnHere()
    {
        BlockPos spawnPos = new BlockPos(this);
        return super.getCanSpawnHere() && spawnPos.getY() < 30 && this.world.canSeeSky(spawnPos);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(HSConfig.ENTITIES.spectralMinerAttackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(HSConfig.ENTITIES.spectralMinerMaxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(HSConfig.ENTITIES.spectralMinerMovementSpeed);
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block)
    {
        this.playSound(HSSoundEvents.ENTITY_SPECTRAL_MINER_STEP.getSoundEvent(), 0.4F, 1.0F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 2.0D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D, 30));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    public int getTalkInterval()
    {
        return 200;
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
        if (!this.world.isRemote && this.ticksExisted % 200 == 0 && this.rand.nextDouble() < 0.25D)
        {
            applyBlindnessEffect();
            this.world.playSound(null, this.getPosition(), HSSoundEvents.ENTITY_SPECTRAL_MINER_RUN.getSoundEvent(), SoundCategory.HOSTILE, 1.0F, 1.0F);
        }
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable()
    {
        return this.getHeldItemMainhand().getItem() == HSItems.spectral_pickaxe ? HSLootTables.SPECTRAL_MINER : super.getLootTable();
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(HSItems.spectral_pickaxe));
        this.setDropChance(EntityEquipmentSlot.MAINHAND, -100.0F);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        if (HSConfig.GENERAL.debugMode) HarkenScythe.LOGGER.debug(this.getDisplayName() + " spawned at " + this.getPosition());
        this.setEquipmentBasedOnDifficulty(difficulty);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    private void applyBlindnessEffect()
    {
        List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(24.0D));
        for (EntityPlayer player : players)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
            if (this.world.rand.nextInt(2) == 0)
            {
                this.world.playSound(null, player.getPosition(), SoundEvents.AMBIENT_CAVE, SoundCategory.HOSTILE, 0.8F, 1.0F);
            }
            else
            {
                this.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_WITHER_AMBIENT, SoundCategory.HOSTILE, 0.2F, 0.1F);
            }
        }
    }
}
