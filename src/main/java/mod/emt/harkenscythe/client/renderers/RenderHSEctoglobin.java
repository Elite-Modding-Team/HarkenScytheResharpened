package mod.emt.harkenscythe.client.renderers;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.models.LayerHSEctoglobinGel;
import mod.emt.harkenscythe.entities.HSEntityEctoglobin;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHSEctoglobin extends RenderLiving<HSEntityEctoglobin>
{
    private static final ResourceLocation[] ECTOGLOBIN_TEXTURES = new ResourceLocation[] {
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/ectoglobin.png"),
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/ectoglobin2.png"),
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/ectoglobin3.png")
    };

    public RenderHSEctoglobin(RenderManager renderManager)
    {
        super(renderManager, new ModelSlime(16), 0.25F);
        this.addLayer(new LayerHSEctoglobinGel(this));
    }

    @Override
    public void doRender(HSEntityEctoglobin entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.shadowSize = 0.25F * (float) entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(HSEntityEctoglobin entityEctoglobin, float partialTickTime)
    {
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float) entityEctoglobin.getSlimeSize();
        float f2 = (entityEctoglobin.prevSquishFactor + (entityEctoglobin.squishFactor - entityEctoglobin.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(HSEntityEctoglobin entity)
    {
        return ECTOGLOBIN_TEXTURES[entity.getSkin()];
    }

    public static class Factory implements IRenderFactory<HSEntityEctoglobin>
    {
        @Override
        public Render<? super HSEntityEctoglobin> createRenderFor(RenderManager manager)
        {
            return new RenderHSEctoglobin(manager);
        }
    }
}
