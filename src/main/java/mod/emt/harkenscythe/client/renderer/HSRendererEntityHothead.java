package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nullable;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.HSModelEntityHothead;
import mod.emt.harkenscythe.entity.HSEntityHothead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSRendererEntityHothead extends RenderLiving<HSEntityHothead>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/hothead.png");
	
    public HSRendererEntityHothead(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new HSModelEntityHothead(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HSEntityHothead entity)
    {
        return TEXTURES;
    }

    protected void preRenderCallback(HSEntityHothead entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(4.5F, 4.5F, 4.5F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}