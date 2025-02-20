package mod.emt.harkenscythe.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.tileentity.HSTileEntityAbsorber;
import mod.emt.harkenscythe.tileentity.HSTileEntityBloodAbsorber;

// Courtesy of BordListian
@SideOnly(Side.CLIENT)
public class HSParticleGlow extends Particle
{
    public static final ResourceLocation TEXTURE_BLOOD = new ResourceLocation(HarkenScythe.MOD_ID, "misc/glow_blood");
    public static final ResourceLocation TEXTURE_SOUL = new ResourceLocation(HarkenScythe.MOD_ID, "misc/glow_soul");
    private final float initScale;
    private final float initAlpha;

    public HSParticleGlow(HSTileEntityAbsorber absorber, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float a, float scale, int lifetime)
    {
        super(Minecraft.getMinecraft().world, x, y, z, 0, 0, 0);
        float colorR = r;
        float colorG = g;
        float colorB = b;
        if (colorR > 1.0)
        {
            colorR = colorR / 255.0f;
        }
        if (colorG > 1.0)
        {
            colorG = colorG / 255.0f;
        }
        if (colorB > 1.0)
        {
            colorB = colorB / 255.0f;
        }
        this.setRBGColorF(colorR, colorG, colorB);
        this.particleMaxAge = lifetime;
        this.particleScale = scale;
        this.initScale = scale;
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;
        this.initAlpha = a;
        this.canCollide = false;
        ResourceLocation texture = absorber instanceof HSTileEntityBloodAbsorber ? TEXTURE_BLOOD : TEXTURE_SOUL;
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
        this.setParticleTexture(sprite);
    }

    @Override
    public boolean shouldDisableDepth()
    {
        return true;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (Minecraft.getMinecraft().world.getTotalWorldTime() % 10 == 0)
        {
            this.particleAge++;
        }
        float lifeCoeff = (float) this.particleAge / (float) this.particleMaxAge;
        this.particleScale = initScale - initScale * lifeCoeff;
        this.particleAlpha = initAlpha * (1.0f - lifeCoeff);
    }

    @Override
    public int getFXLayer()
    {
        return 1;
    }

    @Override
    public int getBrightnessForRender(float pTicks)
    {
        return 255;
    }
}
