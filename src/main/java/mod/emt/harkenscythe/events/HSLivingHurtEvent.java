package mod.emt.harkenscythe.events;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entities.HSEntityBlood;
import mod.emt.harkenscythe.init.HSEnchantments;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.items.tools.HSGlaive;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSLivingHurtEvent
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        if (!world.isRemote)
        {
            // Glaive reap
            DamageSource damageSource = event.getSource();
            if (damageSource.getTrueSource() instanceof EntityPlayer && damageSource.getDamageType().equals("hs_reap"))
            {
                EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
                if (player.getHeldItemMainhand().getItem() instanceof HSGlaive)
                {
                    HSEntityBlood blood = new HSEntityBlood(world);
                    blood.setPosition(entity.posX, entity.posY, entity.posZ);
                    world.spawnEntity(blood);
                    world.playSound(null, entity.getPosition(), HSSoundEvents.ESSENCE_BLOOD_SPAWN, SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
                }
            }
            if (entity instanceof EntityPlayer)
            {
                // Nourishment enchantment
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                if (player.getFoodStats().getFoodLevel() > 0 && triggerEnchantment(HSEnchantments.NOURISHMENT, player))
                {
                    int damage = Math.min(player.getFoodStats().getFoodLevel(), Math.round(event.getAmount()));
                    player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - damage);
                    event.setAmount(0);
                }
                // Exude enchantment
                if ((player.isPotionActive(MobEffects.POISON) || player.isPotionActive(MobEffects.WITHER) || player.isBurning()) && triggerEnchantment(HSEnchantments.EXUDE, player))
                {
                    player.removePotionEffect(MobEffects.POISON);
                    player.removePotionEffect(MobEffects.WITHER);
                    player.extinguish();
                    player.heal(event.getAmount());
                    event.setAmount(0);
                }
            }
        }
    }

    private static boolean triggerEnchantment(Enchantment enchantment, EntityPlayer player)
    {
        int level = EnchantmentHelper.getMaxEnchantmentLevel(enchantment, player);
        return (level > 0 && player.getRNG().nextFloat() < 0.05F * level);
    }
}
