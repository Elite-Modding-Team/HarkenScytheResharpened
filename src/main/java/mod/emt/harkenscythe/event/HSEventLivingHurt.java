package mod.emt.harkenscythe.event;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.HSEntityBlood;
import mod.emt.harkenscythe.entity.HSEntityGlobin;
import mod.emt.harkenscythe.init.HSEnchantments;
import mod.emt.harkenscythe.init.HSPotions;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.armor.HSArmor;
import mod.emt.harkenscythe.item.tool.HSToolGlaive;
import mod.emt.harkenscythe.util.HSEntityBlacklists;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLivingHurt
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();
        if (trueSource instanceof EntityPlayer && isSuccessfulReap((EntityPlayer) trueSource, damageSource))
        {
            spawnBlood(world, entity);
            if (HSArmor.isWearingFullBloodweaveSet((EntityPlayer) trueSource) && world.rand.nextDouble() < 0.25D)
            {
                spawnBlood(world, entity);
            }
        }
        if (!HSConfig.GENERAL.disableEnchantments)
        {
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
            // Blight enchantment
            if (event.getSource().getImmediateSource() instanceof EntityArrow)
            {
                EntityArrow arrow = (EntityArrow) event.getSource().getImmediateSource();
                if (arrow.shootingEntity instanceof EntityLivingBase)
                {
                    EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
                    ItemStack bow = shooter.getHeldItemMainhand();
                    int level = EnchantmentHelper.getEnchantmentLevel(HSEnchantments.BLIGHT, bow);
                    if (!bow.isEmpty() && level > 0)
                    {
                        int duration = level * 100;
                        entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, duration));
                        entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, duration));
                        entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, duration));
                    }
                }
            }
            // Hemorrhage enchantment
            if (trueSource instanceof EntityLivingBase)
            {
                EntityLivingBase attacker = (EntityLivingBase) trueSource;
                ItemStack weapon = attacker.getHeldItemMainhand();

                if (!weapon.isEmpty() && EnchantmentHelper.getEnchantmentLevel(HSEnchantments.HEMORRHAGE, weapon) > 0)
                {
                    int level = EnchantmentHelper.getEnchantmentLevel(HSEnchantments.HEMORRHAGE, weapon);
                    int duration = level * 100;
                    entity.addPotionEffect(new PotionEffect(HSPotions.BLEEDING, duration));
                }
            }
        }
    }

    public static void spawnBlood(World world, EntityLivingBase entity)
    {
        if (entity.getEntityData().getBoolean("IsSpectral") || entity instanceof HSEntityGlobin || HSEntityBlacklists.isBlacklistedForBloodReaping(entity)) return;
        HSEntityBlood blood = new HSEntityBlood(world);
        blood.setPosition(entity.posX, entity.posY, entity.posZ);
        world.spawnEntity(blood);
        world.playSound(null, entity.getPosition(), HSSoundEvents.ESSENCE_BLOOD_SPAWN.getSoundEvent(), SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
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
}
