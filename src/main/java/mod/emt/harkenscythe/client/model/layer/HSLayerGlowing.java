package mod.emt.harkenscythe.client.model.layer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSLayerGlowing<T extends EntityLiving> implements LayerRenderer<T>
{
    private final RenderLiving<T> renderer;
    private final ResourceLocation glowLayer;

    public HSLayerGlowing(RenderLiving<T> renderer, ResourceLocation texture)
    {
        this.renderer = renderer;
        this.glowLayer = texture;
    }

    @Override
    public void doRenderLayer(T entityLiving, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.renderer.bindTexture(glowLayer);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderer.getMainModel().render(entityLiving, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        i = entityLiving.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        this.renderer.setLightmap(entityLiving);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return true;
    }
}
