package mod.emt.harkenscythe.client.particle;

import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

public class HSParticleHandler
{
    public static void spawnColoredParticle(EnumParticleTypes type, double x, double y, double z, Color color, double velX, double velY, double velZ)
    {
        if (FMLLaunchHandler.side().isServer()) return;

        Particle particle = Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(type.getParticleID(), x, y, z, velX, velY, velZ);
        particle.setRBGColorF((color.getRed() / 255.0F), (color.getGreen() / 255.0F), (color.getBlue() / 255.0F));
    }

    public static void spawnDarkColoredParticle(EnumParticleTypes type, double x, double y, double z, Color color, double velX, double velY, double velZ)
    {
        if (FMLLaunchHandler.side().isServer()) return;

        Particle particle = Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(type.getParticleID(), x, y, z, velX, velY, velZ);
        float randBrightness = 0.5F + (float) Math.random();
        particle.setRBGColorF((color.getRed() / 255.0F) * randBrightness, (color.getGreen() / 255.0F) * randBrightness, (color.getBlue() / 255.0F) * randBrightness);
    }

    public static void spawnBeamParticles(EnumParticleTypes type, int particleAmount, World world, double sourceX, double sourceY, double sourceZ, Color color, double targetX, double targetY, double targetZ)
    {
        if (FMLLaunchHandler.side().isServer()) return;

        for (int i = 0; i < particleAmount; i++)
        {
            double beam = i / (particleAmount - 1.0D);
            float velX = 1.0F;
            float velY = 0.5F;
            float velZ = 0.5F;
            double x = sourceX + (targetX - sourceX) * beam + world.rand.nextGaussian() * 0.005;
            double y = sourceY + (targetY - sourceY) * beam + world.rand.nextGaussian() * 0.005;
            double z = sourceZ + (targetZ - sourceZ) * beam + world.rand.nextGaussian() * 0.005;

            spawnColoredParticle(type, x, y, z, color, velX, velY, velZ);
        }
    }
}
