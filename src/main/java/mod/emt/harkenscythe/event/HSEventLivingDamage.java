package mod.emt.harkenscythe.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.awt.Color;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.util.HSAttributeModifier;
import mod.emt.harkenscythe.util.HSDamageSource;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLivingDamage
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDamage(LivingDamageEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        if (trueSource instanceof EntityLivingBase && ((EntityLivingBase) trueSource).getEntityAttribute(HSAttributeModifier.LIFESTEAL) != null && ((EntityLivingBase) trueSource).getEntityAttribute(HSAttributeModifier.LIFESTEAL).getAttributeValue() > 0)
        {
            if (entity instanceof EntityLivingBase)
            {
                // Only activate lifesteal effect when there is more than 0 lifesteal on the user, an additional check is done to see if the user's health is not full or at zero
                if (((EntityLivingBase) trueSource).getHealth() > 0.0F && ((EntityLivingBase) trueSource).getHealth() < ((EntityLivingBase) trueSource).getMaxHealth())
                {
                    float lifestealDamage = (float) (event.getAmount() * (float) ((EntityLivingBase) trueSource).getEntityAttribute(HSAttributeModifier.LIFESTEAL).getAttributeValue());

                    // Apply lifesteal damage with calculation based on how much lifesteal the user has
                    entity.attackEntityFrom(new HSDamageSource("hs_lifesteal", trueSource), lifestealDamage);
                    entity.playSound(HSSoundEvents.BLOCK_BLOOD_ABSORBER_STOP.getSoundEvent(), 0.4F, 2.0F / (trueSource.world.rand.nextFloat() * 0.4F + 0.8F));

                    // Heal user based on lifesteal amount
                    ((EntityLivingBase) trueSource).heal(lifestealDamage);
                    trueSource.playSound(HSSoundEvents.ENTITY_HEMOGLOBIN_IDLE.getSoundEvent(), 0.2F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 0.8F));
                    trueSource.playSound(HSSoundEvents.BLOCK_BLOOD_ABSORBER_START.getSoundEvent(), 0.2F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 0.8F));

                    // Beam particles while lifesteal is activated
                    if (FMLLaunchHandler.side().isClient())
                    {
                        HSParticleHandler.spawnBeamParticles(EnumParticleTypes.REDSTONE, 20, world, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, Color.getColor("Blood Red", 12124160), trueSource.posX, trueSource.posY + trueSource.getEyeHeight(), trueSource.posZ);
                    }
                }
            }
        }
    }
}
