package mod.emt.harkenscythe.client.model.layer;

import mod.emt.harkenscythe.client.renderer.HSRendererEntityHemoglobin;
import mod.emt.harkenscythe.entity.HSEntityHemoglobin;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSLayerEntityHemoglobinGel implements LayerRenderer<HSEntityHemoglobin>
{
    private final HSRendererEntityHemoglobin hemoglobinRenderer;
    private final ModelBase slimeModel = new ModelSlime(0);

    public HSLayerEntityHemoglobinGel(HSRendererEntityHemoglobin hemoglobinRenderer)
    {
        this.hemoglobinRenderer = hemoglobinRenderer;
    }

    @Override
    public void doRenderLayer(HSEntityHemoglobin entityHemoglobin, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entityHemoglobin.isInvisible())
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.hemoglobinRenderer.getMainModel());
            this.slimeModel.render(entityHemoglobin, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return true;
    }
}
