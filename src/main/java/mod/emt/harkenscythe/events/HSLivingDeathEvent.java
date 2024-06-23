package mod.emt.harkenscythe.events;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entities.HSEntityEctoglobin;
import mod.emt.harkenscythe.entities.HSEntityHarbinger;
import mod.emt.harkenscythe.entities.HSEntitySoul;
import mod.emt.harkenscythe.init.HSEnchantments;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.items.tools.HSScythe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSLivingDeathEvent
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onScytheReap(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();
        if (trueSource instanceof EntityPlayer && isPlayerReap((EntityPlayer) trueSource, damageSource) && !entity.getCustomNameTag().contains("Spectral"))
        {
            spawnSoul(world, entity);
        }
        else if (trueSource instanceof HSEntityHarbinger)
        {
            spawnSpectralEntity(world, entity, entity.getPosition());
        }
        // TODO: Set entity data to determine spectral variant
        else if (!world.isRemote && entity.getCustomNameTag().contains("Spectral"))
        {
            entity.dropItem(HSItems.soul_essence, 1);
        }
    }

    public static void spawnSoul(World world, EntityLivingBase entity)
    {
        HSEntitySoul soul = new HSEntitySoul(world, entity);
        soul.setPosition(entity.posX, entity.posY, entity.posZ);
        if (!world.isRemote) world.spawnEntity(soul);
        world.playSound(null, entity.getPosition(), HSSoundEvents.ESSENCE_SOUL_SPAWN, SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
    }

    public static void spawnSpectralEntity(World world, EntityLivingBase entity, BlockPos pos)
    {
        if (entity != null)
        {
            // Reanimate original entity
            if (isWhitelistedMob(entity))
            {
                // TODO: Set entity data to determine spectral variant
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
            if (!world.isRemote) world.spawnEntity(entity);
            world.playSound(null, pos, HSSoundEvents.ESSENCE_SOUL_SPAWN, SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            //if (false && entity instanceof EntityCreature && !(entity instanceof EntityMob))
            //{
            //    EntityCreature creature = (EntityCreature) entity;
            //    creature.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(creature, EntityPlayer.class, true));
            //    creature.setAttackTarget(creature.world.getNearestAttackablePlayer(creature, 32.0D, 32.0D));
            //}
        }
    }

    private static boolean isPlayerReap(EntityPlayer player, DamageSource damageSource)
    {
        return (player.getHeldItemMainhand().getItem() instanceof HSScythe && damageSource.getDamageType().equals("hs_reap")) || triggerEnchantment(HSEnchantments.SOULSTEAL, player);
    }

    private static boolean triggerEnchantment(Enchantment enchantment, EntityPlayer player)
    {
        int level = EnchantmentHelper.getMaxEnchantmentLevel(enchantment, player);
        return (level > 0 && player.getRNG().nextFloat() < 0.15F * level);
    }

    private static boolean isWhitelistedMob(Entity entity)
    {
        // TODO: Replace with config-defined whitelist
        return !(entity instanceof EntityPlayer) && !(entity instanceof EntityGhast) && !(entity instanceof EntitySlime);
    }
}
