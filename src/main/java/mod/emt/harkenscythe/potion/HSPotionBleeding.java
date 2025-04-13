package mod.emt.harkenscythe.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.awt.*;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.particle.HSParticleHandler;

public class HSPotionBleeding extends Potion
{
    public HSPotionBleeding(String name)
    {
        super(true, 0x8B0000);
        setPotionName("effect." + HarkenScythe.MOD_ID + "." + name);
        setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
        entity.attackEntityFrom(DamageSource.MAGIC, 1.0F + (amplifier * 0.5F));

        if (FMLLaunchHandler.side().isClient())
        {
            for (int i = 0; i < (amplifier + 1) * 10; i++)
            {
                double posX = entity.posX + (entity.world.rand.nextGaussian() * 0.5D);
                double posY = entity.posY + (entity.world.rand.nextGaussian() * 0.5D) + (entity.height * 0.5F);
                double posZ = entity.posZ + (entity.world.rand.nextGaussian() * 0.5D);
                HSParticleHandler.spawnColoredParticle(EnumParticleTypes.WATER_SPLASH, posX, posY, posZ, Color.getColor("Blood Red", 12124160), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration % 20 == 0;
    }

    @Override
    public boolean shouldRender(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(PotionEffect effect)
    {
        return false;
    }
}
