package mod.emt.harkenscythe.event;

import javax.annotation.Nullable;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entity.HSEntityEctoglobin;
import mod.emt.harkenscythe.entity.HSEntityGlobin;
import mod.emt.harkenscythe.entity.HSEntityHarbinger;
import mod.emt.harkenscythe.entity.HSEntitySoul;
import mod.emt.harkenscythe.init.HSEnchantments;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.tools.HSToolScythe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLivingDeath
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onScytheReap(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        if (!world.isRemote)
        {
            DamageSource damageSource = event.getSource();
            Entity trueSource = damageSource.getTrueSource();
            if (trueSource instanceof EntityPlayer && isSuccessfulReap((EntityPlayer) trueSource, damageSource))
            {
                spawnSoul(world, entity);
                if (isWearingFullSoulweaveSet((EntityPlayer) trueSource) && world.rand.nextDouble() < 0.25D)
                {
                    spawnSoul(world, entity);
                }
            }
            else if (trueSource instanceof HSEntityHarbinger)
            {
                spawnSpectralEntity(world, entity, entity.getPosition());
            }
            else if (entity.getEntityData().getBoolean("IsSpectral"))
            {
                entity.dropItem(HSItems.soul_essence, 1);
            }
        }
    }

    public static void spawnSpectralEntity(World world, @Nullable EntityLivingBase entity, BlockPos pos)
    {
        if (!world.isRemote)
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
            // Spawn ectoglobin
            else
            {
                entity = new HSEntityEctoglobin(world);
            }
            entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(entity);
            world.playSound(null, pos, HSSoundEvents.ESSENCE_SOUL_SPAWN, SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            //if (false && entity instanceof EntityCreature && !(entity instanceof EntityMob))
            //{
            //    EntityCreature creature = (EntityCreature) entity;
            //    creature.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(creature, EntityPlayer.class, true));
            //    creature.setAttackTarget(creature.world.getNearestAttackablePlayer(creature, 32.0D, 32.0D));
            //}
        }
    }

    private static void spawnSoul(World world, EntityLivingBase entity)
    {
        if (entity.getEntityData().getBoolean("IsSpectral") || entity instanceof HSEntityGlobin) return;
        HSEntitySoul soul = new HSEntitySoul(world, entity);
        soul.setPosition(entity.posX, entity.posY, entity.posZ);
        world.spawnEntity(soul);
        world.playSound(null, entity.getPosition(), HSSoundEvents.ESSENCE_SOUL_SPAWN, SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
    }

    private static boolean isSuccessfulReap(EntityPlayer player, DamageSource damageSource)
    {
        return isRegularReap(player, damageSource, player.getHeldItemMainhand()) || isEnchantmentReap(HSEnchantments.SOULSTEAL, player);
    }

    private static boolean isRegularReap(EntityPlayer player, DamageSource damageSource, ItemStack stack)
    {
        if (player.getHeldItemMainhand().getItem() instanceof HSToolScythe && damageSource.getDamageType().equals("hs_reap"))
        {
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
        // TODO: Replace with config-defined whitelist
        return !(entity instanceof EntityPlayer) && !(entity instanceof EntityGhast) && !(entity instanceof EntitySlime);
    }

    private static boolean isWearingFullSoulweaveSet(EntityPlayer player)
    {
        Item boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
        Item leggings = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
        Item chestplate = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
        return boots == HSItems.soulweave_shoes && leggings == HSItems.soulweave_pants && chestplate == HSItems.soulweave_robe && helmet == HSItems.soulweave_hood;
    }
}
