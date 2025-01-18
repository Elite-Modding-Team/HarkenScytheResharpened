package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.HSModelEntityBearer;
import mod.emt.harkenscythe.entity.HSEntityTitanbearer;

@SideOnly(Side.CLIENT)
public class HSRendererEntityTitanbearer extends RenderLiving<HSEntityTitanbearer>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/titanbearer.png");

    public HSRendererEntityTitanbearer(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new HSModelEntityBearer(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HSEntityTitanbearer entity)
    {
        return TEXTURES;
    }

    protected void preRenderCallback(HSEntityTitanbearer entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(4.5F, 4.5F, 4.5F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
