package mod.emt.harkenscythe.event;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entity.HSEntityBlood;
import mod.emt.harkenscythe.init.HSEnchantments;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.tools.HSToolGlaive;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLivingHurt
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGlaiveReap(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();
        if (trueSource instanceof EntityPlayer && isSuccessfulReap((EntityPlayer) trueSource, damageSource))
        {
            spawnBlood(world, entity);
            if (isWearingFullBloodweaveSet((EntityPlayer) trueSource) && world.rand.nextDouble() < 0.25D)
            {
                spawnBlood(world, entity);
            }
        }
        if (entity instanceof EntityPlayer)
        {
            // Nourishment enchantment
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (player.getFoodStats().getFoodLevel() > 0 && isEnchantmentReap(HSEnchantments.NOURISHMENT, player))
            {
                int damage = Math.min(player.getFoodStats().getFoodLevel(), Math.round(event.getAmount()));
                player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - damage);
                event.setAmount(0);
            }
            // Exude enchantment
            if ((player.isPotionActive(MobEffects.POISON) || player.isPotionActive(MobEffects.WITHER) || player.isBurning()) && isEnchantmentReap(HSEnchantments.EXUDE, player))
            {
                player.removePotionEffect(MobEffects.POISON);
                player.removePotionEffect(MobEffects.WITHER);
                player.extinguish();
                player.heal(event.getAmount());
                event.setAmount(0);
            }
        }
    }

    private static void spawnBlood(World world, EntityLivingBase entity)
    {
        HSEntityBlood blood = new HSEntityBlood(world);
        blood.setPosition(entity.posX, entity.posY, entity.posZ);
        world.spawnEntity(blood);
        world.playSound(null, entity.getPosition(), HSSoundEvents.ESSENCE_BLOOD_SPAWN, SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
    }

    private static boolean isSuccessfulReap(EntityPlayer player, DamageSource damageSource)
    {
        return isRegularReap(player, damageSource, player.getHeldItemMainhand()) || isEnchantmentReap(HSEnchantments.BLOODLETTING, player);
    }

    private static boolean isRegularReap(EntityPlayer player, DamageSource damageSource, ItemStack stack)
    {
        if (player.getHeldItemMainhand().getItem() instanceof HSToolGlaive && damageSource.getDamageType().equals("hs_reap"))
        {
            int damage = stack.getMaxDamage() - stack.getItemDamage();
            double chance = Math.min(0.6D, Math.max(0.2D, (double) damage / 500));
            return player.getRNG().nextDouble() < chance;
        }
        return false;
    }

    private static boolean isEnchantmentReap(Enchantment enchantment, EntityPlayer player)
    {
        int level = EnchantmentHelper.getMaxEnchantmentLevel(enchantment, player);
        return (level > 0 && player.getRNG().nextFloat() < 0.05F * level);
    }

    private static boolean isWearingFullBloodweaveSet(EntityPlayer player)
    {
        Item boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
        Item leggings = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
        Item chestplate = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
        return boots == HSItems.bloodweave_shoes && leggings == HSItems.bloodweave_pants && chestplate == HSItems.bloodweave_robe && helmet == HSItems.bloodweave_hood;
    }
}
