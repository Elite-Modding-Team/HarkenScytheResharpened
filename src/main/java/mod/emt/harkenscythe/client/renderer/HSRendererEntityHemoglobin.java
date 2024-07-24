package mod.emt.harkenscythe.client.renderer;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.layer.HSLayerEntityHemoglobinGel;
import mod.emt.harkenscythe.entity.HSEntityHemoglobin;

@SideOnly(Side.CLIENT)
public class HSRendererEntityHemoglobin extends RenderLiving<HSEntityHemoglobin>
{
    private static final ResourceLocation[] HEMOGLOBIN_TEXTURES = new ResourceLocation[] {
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/hemoglobin.png"),
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/hemoglobin2.png"),
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/hemoglobin3.png")
    };

    public HSRendererEntityHemoglobin(RenderManager renderManager)
    {
        super(renderManager, new ModelSlime(16), 0.25F);
        this.addLayer(new HSLayerEntityHemoglobinGel(this));
    }

    @Override
    public void doRender(HSEntityHemoglobin entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.shadowSize = 0.25F * (float) entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(HSEntityHemoglobin entityHemoglobin, float partialTickTime)
    {
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float) entityHemoglobin.getSlimeSize();
        float f2 = (entityHemoglobin.prevSquishFactor + (entityHemoglobin.squishFactor - entityHemoglobin.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(HSEntityHemoglobin entity)
    {
        return HEMOGLOBIN_TEXTURES[entity.getSkin()];
    }

    public static class Factory implements IRenderFactory<HSEntityHemoglobin>
    {
        @Override
        public Render<? super HSEntityHemoglobin> createRenderFor(RenderManager manager)
        {
            return new HSRendererEntityHemoglobin(manager);
        }
    }
}
