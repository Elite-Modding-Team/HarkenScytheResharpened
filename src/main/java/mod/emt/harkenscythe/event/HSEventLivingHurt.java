package mod.emt.harkenscythe.event;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
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
import mod.emt.harkenscythe.entity.*;
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
        if (trueSource instanceof HSEntityHarbinger)
        {
            if (entity instanceof EntityCreature)
            {
                // +100% Harbinger damage against animals
                event.setAmount(event.getAmount() * 2.0F);
            }
            else if (!(entity instanceof EntityPlayer))
            {
                // +50% Harbinger damage against non-players
                event.setAmount(event.getAmount() * 1.5F);
            }
        }
        if (entity instanceof HSEntitySpectralMiner && world.rand.nextDouble() < 0.25D)
        {
            // 25% chance to spawn medium Ectoglobins on hit
            spawnEctoglobin(world, entity);
        }
        if (trueSource instanceof EntityPlayer && isSuccessfulReap(damageSource, entity, event.getAmount()))
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
        if (entity.isChild() || entity.getEntityData().getBoolean("IsSpectral") || entity.getMaxHealth() <= HSConfig.ENTITIES.essenceMaxHealthLimit || entity instanceof HSEntityGlobin || HSEntityBlacklists.isBlacklistedForBloodReaping(entity)) return;
        HSEntityBlood blood = new HSEntityBlood(world, entity);
        blood.setPosition(entity.posX, entity.posY, entity.posZ);
        if (!world.isRemote) world.spawnEntity(blood);
        world.playSound(null, entity.getPosition(), HSSoundEvents.ESSENCE_BLOOD_SPAWN.getSoundEvent(), SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
    }

    public static void spawnEctoglobin(World world, EntityLivingBase entity)
    {
        HSEntityEctoglobin globin = new HSEntityEctoglobin(world);
        globin.setSize(2, true);
        globin.setPosition(entity.posX, entity.posY, entity.posZ);
        if (!world.isRemote) world.spawnEntity(globin);
    }

    private static boolean isSuccessfulReap(DamageSource damageSource, EntityLivingBase target, float damage)
    {
        EntityPlayer player = (EntityPlayer) damageSource.getTrueSource();
        return isRegularReap(player, target, damage, damageSource, player.getHeldItemMainhand()) || isEnchantmentReap(HSEnchantments.BLOODLETTING, player);
    }

    private static boolean isRegularReap(EntityPlayer player, EntityLivingBase target, float damage, DamageSource damageSource, ItemStack stack)
    {
        if (player.getHeldItemMainhand().getItem() instanceof HSToolGlaive && damageSource.getDamageType().equals("hs_reap"))
        {
            if (damage >= target.getHealth()) return true;
            int toolDamage = stack.getMaxDamage() - stack.getItemDamage();
            double chance = Math.min(0.6D, Math.max(0.2D, (double) toolDamage / 500));
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
