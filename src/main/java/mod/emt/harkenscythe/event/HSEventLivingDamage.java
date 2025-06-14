package mod.emt.harkenscythe.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.awt.*;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.entity.HSEntityEctoglobin;
import mod.emt.harkenscythe.init.HSAttributes;
import mod.emt.harkenscythe.init.HSSoundEvents;
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

        if (entity instanceof EntityLivingBase && damageSource.getTrueSource() instanceof EntityLivingBase)
        {
            EntityLivingBase trueSource = (EntityLivingBase) damageSource.getTrueSource();
            IAttributeInstance lifesteal = trueSource.getEntityAttribute(HSAttributes.LIFESTEAL);

            if (lifesteal != null && !lifesteal.getModifiers().isEmpty())
            {
                // Only activate lifesteal effect when there is more than 0 lifesteal on the user, an additional check is done to see if the user's health is not full or at zero
                if (trueSource.getHealth() > 0.0F && trueSource.getHealth() < trueSource.getMaxHealth())
                {
                    float lifestealValue = 0.0F;
                    for (AttributeModifier attributemodifier : lifesteal.getModifiers())
                    {
                        lifestealValue += (float) attributemodifier.getAmount();
                    }
                    if (lifestealValue <= 0) return;
                    float lifestealDamage = event.getAmount() * lifestealValue;

                    // Apply lifesteal damage with calculation based on how much lifesteal the user has
                    entity.attackEntityFrom(new HSDamageSource("hs_lifesteal", trueSource), lifestealDamage);
                    entity.playSound(HSSoundEvents.BLOCK_BLOOD_ABSORBER_STOP.getSoundEvent(), 0.4F, 2.0F / (trueSource.world.rand.nextFloat() * 0.4F + 0.8F));

                    // Heal user based on lifesteal amount
                    trueSource.heal(lifestealDamage);
                    trueSource.playSound(HSSoundEvents.ENTITY_HEMOGLOBIN_IDLE.getSoundEvent(), 0.2F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 0.8F));
                    trueSource.playSound(HSSoundEvents.BLOCK_BLOOD_ABSORBER_START.getSoundEvent(), 0.2F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 0.8F));

                    // Beam particles while lifesteal is activated
                    if (FMLLaunchHandler.side().isClient())
                    {
                        // Beam particles are blue under special circumstances
                        if (trueSource instanceof HSEntityEctoglobin)
                        {
                            HSParticleHandler.spawnBeamParticles(EnumParticleTypes.REDSTONE, 20, world, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, Color.getColor("Soul Blue", 4560335), trueSource.posX, trueSource.posY + trueSource.getEyeHeight(), trueSource.posZ);
                        }
                        else
                        {
                            HSParticleHandler.spawnBeamParticles(EnumParticleTypes.REDSTONE, 20, world, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, Color.getColor("Blood Red", 12124160), trueSource.posX, trueSource.posY + trueSource.getEyeHeight(), trueSource.posZ);
                        }
                    }
                }
            }
        }
    }
}
