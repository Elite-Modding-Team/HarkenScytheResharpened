package mod.emt.harkenscythe.item;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSItemDeadtimeWatch extends HSItem
{
    private final ConcurrentLinkedQueue<Entity> entityQueue = new ConcurrentLinkedQueue<>();
    private int stopTime;

    public HSItemDeadtimeWatch()
    {
        super(EnumRarity.UNCOMMON);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(HSConfig.ITEMS.deadtimeWatchRadius));
        for (Entity entity : list)
        {
            if (entity.isNonBoss())
            {
                if (entity instanceof EntityLiving)
                {
                    ((EntityLiving) entity).setNoAI(true);
                }
                else
                {
                    entity.updateBlocked = true;
                }
                this.entityQueue.add(entity);
            }
        }
        this.stopTime = HSConfig.ITEMS.deadtimeWatchDuration;
        player.getCooldownTracker().setCooldown(this, this.stopTime * 4);
        player.swingArm(hand);
        world.playSound(null, player.getPosition(), HSSoundEvents.ITEM_DEADTIME_WATCH_ACTIVATE.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F);

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);

        if (this.stopTime > 0) this.stopTime--;
        if (!this.entityQueue.isEmpty())
        {
            int duration = HSConfig.ITEMS.deadtimeWatchDuration - this.stopTime;
            float progress = duration * 1.0F / HSConfig.ITEMS.deadtimeWatchDuration;
            for (Entity queuedEntity : this.entityQueue)
            {
                if (this.stopTime >= 15)
                {
                    spawnPauseParticles(world, queuedEntity, progress);
                }
                if (this.stopTime <= 0)
                {
                    if (queuedEntity instanceof EntityLiving)
                    {
                        ((EntityLiving) queuedEntity).setNoAI(false);
                    }
                    else
                    {
                        queuedEntity.updateBlocked = false;
                    }
                    spawnResumeParticles(world, queuedEntity);
                    entityQueue.remove(queuedEntity);
                }
            }
        }
    }

    private void spawnPauseParticles(World world, Entity queuedEntity, float progress)
    {
        if (world.isRemote)
        {
            double theta = Math.PI * 6 * progress;
            double r = queuedEntity.width * (1 - progress);
            for (int i = 0; i < 3; i++)
            {
                world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, queuedEntity.posX + Math.cos(theta) * r, queuedEntity.posY, queuedEntity.posZ + Math.sin(theta) * r, 0.0D, queuedEntity.height, 0.0D);
                theta += Math.PI * 2 / 3;
            }
        }
    }

    private void spawnResumeParticles(World world, Entity queuedEntity)
    {
        if (world.isRemote)
        {
            for (int i = 0; i < 40; i++)
            {
                double gaussX = itemRand.nextGaussian() * 0.02D;
                double gaussY = itemRand.nextGaussian() * 0.02D;
                double gaussZ = itemRand.nextGaussian() * 0.02D;
                double gaussFactor = 10.0D;
                world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, queuedEntity.posX + itemRand.nextFloat() * queuedEntity.width * 2.0F - queuedEntity.width - gaussX * gaussFactor, queuedEntity.posY + itemRand.nextFloat() * queuedEntity.height - gaussY * gaussFactor, queuedEntity.posZ + itemRand.nextFloat() * queuedEntity.width * 2.0F - queuedEntity.width - gaussZ * gaussFactor, gaussX, gaussY, gaussZ);
            }
        }
    }
}
