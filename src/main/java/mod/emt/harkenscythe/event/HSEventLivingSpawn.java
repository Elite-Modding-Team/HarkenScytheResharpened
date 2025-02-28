package mod.emt.harkenscythe.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLivingSpawn
{
    @SubscribeEvent
    public static void onSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if (!(entity instanceof IMob || entity instanceof EntityMob)) return;

        // Mobs that spawn during the New Moon will gain the Strength effect
        if (HSConfig.GENERAL.newMoonStrengthEffect && !entity.world.isDaytime() && entity.world.getCurrentMoonPhaseFactor() == 0.0F)
        {
            Potion effect = null;
            effect = MobEffects.STRENGTH;

            // No particles
            if (effect != null) entity.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, 0, true, false));
        }
    }
}
