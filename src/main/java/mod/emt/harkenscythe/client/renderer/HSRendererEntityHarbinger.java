package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.HSModelEntityHarbinger;
import mod.emt.harkenscythe.client.model.layer.HSLayerGlowing;
import mod.emt.harkenscythe.entity.HSEntityHarbinger;

@SideOnly(Side.CLIENT)
public class HSRendererEntityHarbinger extends RenderBiped<HSEntityHarbinger>
{
    public static final ResourceLocation HARBINGER_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/harbinger.png");
    public static final ResourceLocation HARBINGER_RARE_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/harbinger_rare.png");
    public static final ResourceLocation HARBINGER_GLOW_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/harbinger_glow.png");

    public HSRendererEntityHarbinger(RenderManager renderManager)
    {
        super(renderManager, new HSModelEntityHarbinger(), 0.0F);
        this.addLayer(new HSLayerGlowing<>(this, HARBINGER_GLOW_TEXTURES));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HSEntityHarbinger entity)
    {
        return entity.getDataManager().get(HSEntityHarbinger.RARE) ? HARBINGER_RARE_TEXTURES : HARBINGER_TEXTURES;
    }

    @Override
    protected void renderModel(HSEntityHarbinger entityHarbinger, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        super.renderModel(entityHarbinger, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GlStateManager.disableBlend();
    }

    @Override
    protected void preRenderCallback(HSEntityHarbinger entityHarbinger, float partialTickTime)
    {
        super.preRenderCallback(entityHarbinger, partialTickTime);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
    }

    public static class Factory implements IRenderFactory<HSEntityHarbinger>
    {
        @Override
        public Render<? super HSEntityHarbinger> createRenderFor(RenderManager manager)
        {
            return new HSRendererEntityHarbinger(manager);
        }
    }
}
