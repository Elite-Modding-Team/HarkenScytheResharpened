package mod.emt.harkenscythe.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.HSModelEntityEssence;
import mod.emt.harkenscythe.entity.HSEntityBlood;

@SideOnly(Side.CLIENT)
public class HSRendererEntityBlood extends Render<HSEntityBlood>
{
    private static final ResourceLocation BLOOD_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/blood_common.png");
    private final ModelBase modelEssence = new HSModelEntityEssence();

    public HSRendererEntityBlood(RenderManager renderManager)
    {
        super(renderManager);
        this.shadowSize = 0.0F;
    }

    @Override
    public void doRender(HSEntityBlood entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        float f = (float) entity.getInnerRotation() + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        this.bindTexture(BLOOD_TEXTURES);
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = f1 * f1 + f1;
        this.modelEssence.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(HSEntityBlood entity)
    {
        return BLOOD_TEXTURES;
    }

    public static class Factory implements IRenderFactory<HSEntityBlood>
    {
        @Override
        public Render<? super HSEntityBlood> createRenderFor(RenderManager manager)
        {
            return new HSRendererEntityBlood(manager);
        }
    }
}
