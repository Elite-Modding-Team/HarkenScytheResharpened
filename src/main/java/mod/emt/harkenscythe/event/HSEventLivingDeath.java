package mod.emt.harkenscythe.event;

import java.util.Iterator;
import javax.annotation.Nullable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entity.*;
import mod.emt.harkenscythe.entity.ai.HSAIPassiveMobAttack;
import mod.emt.harkenscythe.init.HSEnchantments;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.armor.HSArmor;
import mod.emt.harkenscythe.item.tools.HSToolScythe;
import mod.emt.harkenscythe.util.HSEntityBlacklists;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLivingDeath
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();
        if (trueSource instanceof EntityPlayer && isSuccessfulReap((EntityPlayer) trueSource, damageSource))
        {
            if (entity instanceof HSEntitySpectralMiner) return;
            spawnSoul(world, entity);
            if (HSArmor.isWearingFullSoulweaveSet((EntityPlayer) trueSource) && world.rand.nextDouble() < 0.25D)
            {
                spawnSoul(world, entity);
            }
        }
        else if (entity instanceof HSEntitySpectralMiner)
        {
            spawnSoul(world, entity);
        }
        else if (trueSource instanceof HSEntityHarbinger)
        {
            spawnSpectralEntity(world, entity, entity.getPosition(), false);
        }
        else if (entity.getEntityData().getBoolean("IsSpectral"))
        {
            entity.dropItem(HSItems.soul_essence, 1);
        }
    }

    public static void spawnSpectralEntity(World world, @Nullable EntityLivingBase entity, BlockPos pos, boolean modifyAI)
    {
        // Reanimate original entity
        if (entity != null && isWhitelistedMob(entity))
        {
            entity.getEntityData().setBoolean("IsSpectral", true);
            entity.setCustomNameTag("Spectral " + entity.getName());
            entity.setHealth(entity.getMaxHealth());
            entity.deathTime = 0;
            entity.isDead = false;
        }
        // Spawn spectral human
        else if (entity instanceof EntityPlayer)
        {
            entity = new HSEntitySpectralHuman(world);
        }
        // Spawn ectoglobin
        else
        {
            entity = new HSEntityEctoglobin(world);
        }
        entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
        if (modifyAI && entity instanceof EntityCreature && !(entity instanceof EntityMob))
        {
            modifyAI((EntityCreature) entity);
        }
        if (!world.isRemote) world.spawnEntity(entity);
        world.playSound(null, pos, HSSoundEvents.ESSENCE_SOUL_SUMMON.getSoundEvent(), SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
    }

    public static void spawnSoul(World world, EntityLivingBase entity)
    {
        if (entity.getEntityData().getBoolean("IsSpectral") || entity instanceof HSEntityGlobin) return;
        HSEntitySoul soul = new HSEntitySoul(world, entity);
        soul.setPosition(entity.posX, entity.posY, entity.posZ);
        if (!world.isRemote) world.spawnEntity(soul);
        world.playSound(null, entity.getPosition(), HSSoundEvents.ESSENCE_SOUL_SPAWN.getSoundEvent(), SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
    }

    private static boolean isSuccessfulReap(EntityPlayer player, DamageSource damageSource)
    {
        return isRegularReap(player, damageSource, player.getHeldItemMainhand()) || isEnchantmentReap(HSEnchantments.SOULSTEAL, player);
    }

    private static boolean isRegularReap(EntityPlayer player, DamageSource damageSource, ItemStack stack)
    {
        if (stack.getItem() instanceof HSToolScythe && damageSource.getDamageType().equals("hs_reap"))
        {
            if (stack.getItem() == HSItems.reaper_scythe || stack.getItem() == HSItems.lady_harken_scythe) return true;
            int damage = stack.getMaxDamage() - stack.getItemDamage();
            double chance = Math.min(0.8D, Math.max(0.4D, (double) damage / 500));
            return player.getRNG().nextDouble() < chance;
        }
        return false;
    }

    private static boolean isEnchantmentReap(Enchantment enchantment, EntityPlayer player)
    {
        int level = EnchantmentHelper.getMaxEnchantmentLevel(enchantment, player);
        return (level > 0 && player.getRNG().nextFloat() < 0.15F * level);
    }

    private static boolean isWhitelistedMob(Entity entity)
    {
        return !HSEntityBlacklists.isBlacklistedForSummoning(entity) && !(entity instanceof EntityPlayer) && !(entity instanceof EntityGhast) && !(entity instanceof EntitySlime);
    }

    private static void modifyAI(EntityCreature entity)
    {
        Iterator<EntityAITasks.EntityAITaskEntry> iterator = entity.tasks.taskEntries.iterator();
        while (iterator.hasNext())
        {
            EntityAITasks.EntityAITaskEntry taskEntry = iterator.next();
            EntityAIBase ai = taskEntry.action;
            if (ai instanceof EntityAIPanic)
            {
                iterator.remove();
            }
        }
        entity.setAttackTarget(entity.world.getNearestAttackablePlayer(entity, 32.0D, 32.0D));
        entity.tasks.addTask(1, new HSAIPassiveMobAttack(entity, 1.25D, true));
        entity.targetTasks.addTask(1, new EntityAIHurtByTarget(entity, false));
    }
}
