package mod.emt.harkenscythe.entities.render;

import mod.emt.harkenscythe.entities.HSSoul;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSSoulRender extends Render<HSSoul>
{
    private static final ResourceLocation SOUL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    private final ModelBase modelSoul = new ModelEnderCrystal(0.0F, false);

    public HSSoulRender(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.0F;
    }

    @Override
    public void doRender(HSSoul entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(0.1F, 0.9F, 1.0F, 0.5F);
        GlStateManager.translate((float) x, (float) y, (float) z);
        this.bindTexture(SOUL_TEXTURES);
        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        float ticks = entity.ticksExisted + partialTicks;
        float rotation = (ticks) * 2.0F;
        GlStateManager.translate(x, y + Math.sin(ticks / 5.0F) * 0.1F + 0.2F, z);
        GlStateManager.rotate(rotation, 0, 1, 0);
        this.modelSoul.render(entity, 0.0F, 3.0F, 0.2F, 0.0F, 0.0F, 0.0625F);
        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(HSSoul entity)
    {
        return SOUL_TEXTURES;
    }

    public static class Factory implements IRenderFactory<HSSoul>
    {
        @Override
        public Render<? super HSSoul> createRenderFor(RenderManager manager)
        {
            return new HSSoulRender(manager);
        }
    }
}
