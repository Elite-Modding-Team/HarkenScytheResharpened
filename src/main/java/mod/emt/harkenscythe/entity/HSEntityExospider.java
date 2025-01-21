package mod.emt.harkenscythe.entity;

import java.awt.*;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.entity.ai.HSAIFollowHerd;
import mod.emt.harkenscythe.init.*;

public class HSEntityExospider extends EntitySpider
{
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(HSEntityExospider.class, DataSerializers.VARINT);
    private int timeUntilNextBiomass;

    public HSEntityExospider(World world)
    {
        super(world);
        this.isImmuneToFire = true;
        this.timeUntilNextBiomass = this.rand.nextInt(6000) + 6000;
    }

    public int getVariant()
    {
        return this.dataManager.get(VARIANT);
    }

    public void setVariant(int skinType)
    {
        this.dataManager.set(VARIANT, skinType);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.world.isRemote && this.getVariant() == 1 && --this.timeUntilNextBiomass <= 0)
        {
            this.playSound(HSSoundEvents.BLOCK_BIOMASS_HARVEST.getSoundEvent(), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(HSItems.biomass, this.world.rand.nextInt(2) + 1);
            this.timeUntilNextBiomass = this.rand.nextInt(6000) + 6000;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("Variant", this.getVariant());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        this.setVariant(nbt.getInteger("Variant"));
        if (this.getVariant() == 1) this.setPassive();
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (this.getVariant() == 0 && player.getHeldItem(hand).getItem() == HSItems.biomass)
        {
            if (!player.isCreative()) player.getHeldItem(hand).shrink(1);
            this.setVariant(1);
            this.setPassive();
            this.spawnExplosionParticle();
            this.playSound(HSSoundEvents.ITEM_CREEP_BALL_USE.getSoundEvent(), 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 1.2F));
        }
        return super.processInteract(player, hand);
    }

    // Remove the default smoke puff death animation. Add special death animation
    @Override
    protected void onDeathUpdate()
    {
        if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))
        {
            int i = this.getExperiencePoints(this.attackingPlayer);
            i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);

            while (i > 0)
            {
                int j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
            }
        }

        this.setDead();

        for (int k = 0; k < 40; ++k)
        {
            double d2 = this.rand.nextGaussian() * 0.02D;
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width,
                d2, d0, d1, this.getVariant() == 1 ? Block.getIdFromBlock(HSBlocks.biomass_block) : Block.getIdFromBlock(Blocks.SOUL_SAND));
        }

        for (int k = 0; k < 20; ++k)
        {
            double d2 = this.rand.nextGaussian() * 0.02D;
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            HSParticleHandler.spawnColoredParticle(EnumParticleTypes.REDSTONE, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width,
                this.getVariant() == 1 ? Color.getColor("Blood Red", 12124160) : Color.getColor("Soul Blue", 4560335), d2, d0, d1);
        }
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityPlayerMP)
        {
            HSAdvancements.ENCOUNTER_EXOSPIDER.trigger((EntityPlayerMP) cause.getTrueSource());
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        // No fall damage
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(3, new HSAIFollowHerd(this, 0.8D, 4.0F, 16.0F));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(VARIANT, 0);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return HSSoundEvents.ENTITY_EXOSPIDER_HURT.getSoundEvent();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return HSSoundEvents.ENTITY_EXOSPIDER_DEATH.getSoundEvent();
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block)
    {
        this.playSound(HSSoundEvents.ENTITY_EXOSPIDER_STEP.getSoundEvent(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return this.getVariant() == 1 ? HSLootTables.EXOSPIDER_BIOMASS : HSLootTables.EXOSPIDER;
    }

    private void setPassive()
    {
        Iterator<EntityAITasks.EntityAITaskEntry> tasksIterator = this.tasks.taskEntries.iterator();
        while (tasksIterator.hasNext())
        {
            EntityAITasks.EntityAITaskEntry entry = tasksIterator.next();
            if (entry != null && (entry.action.getClass().getName().contains("AISpiderAttack") || entry.action instanceof EntityAILeapAtTarget)) tasksIterator.remove();
        }
        Iterator<EntityAITasks.EntityAITaskEntry> targetTasksIterator = this.targetTasks.taskEntries.iterator();
        while (targetTasksIterator.hasNext())
        {
            EntityAITasks.EntityAITaskEntry entry = targetTasksIterator.next();
            if (entry != null && (entry.action.getClass().getName().contains("AISpiderTarget") || entry.action instanceof EntityAIHurtByTarget)) targetTasksIterator.remove();
        }
    }
}
