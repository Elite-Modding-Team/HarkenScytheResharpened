package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.HSModelEntityEssence;
import mod.emt.harkenscythe.client.model.HSModelEntityExospider;
import mod.emt.harkenscythe.entity.HSEntityExospider;

@SideOnly(Side.CLIENT)
public class HSRendererEntityExospider extends RenderLiving<HSEntityExospider>
{
    private static final ResourceLocation[] EXOSPIDER_TEXTURES = new ResourceLocation[] {
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/exospider.png"),
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/exospider_biomass.png")
    };
    private static final ResourceLocation SOUL_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/soul_common.png");
    private static final ResourceLocation BLOOD_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/blood_common.png");
    private final ModelBase modelEssence = new HSModelEntityEssence();

    public HSRendererEntityExospider(RenderManager renderManager)
    {
        super(renderManager, new HSModelEntityExospider(), 1.0F);
    }

    @Override
    public void doRender(HSEntityExospider entityExospider, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entityExospider, x, y, z, entityYaw, partialTicks);

        float f = entityExospider.ticksExisted + partialTicks;
        float f1 = 1.0F - (f / 20.0F);
        float f2 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f2 = f2 * f2 + f2;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(0.6F, 0.6F, 0.6F, 0.6F);
        GlStateManager.translate(x, y + 0.8D, z);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        this.bindTexture(entityExospider.getVariant() == 0 ? SOUL_TEXTURES : BLOOD_TEXTURES);
        this.modelEssence.render(entityExospider, 0.0F, f1 * entityExospider.limbSwingAmount, f2 * 0.05F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    protected float getDeathMaxRotation(HSEntityExospider entityExospider)
    {
        return 180.0F;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HSEntityExospider entityExospider)
    {
        return EXOSPIDER_TEXTURES[entityExospider.getVariant()];
    }

    public static class Factory implements IRenderFactory<HSEntityExospider>
    {
        @Override
        public Render<? super HSEntityExospider> createRenderFor(RenderManager manager)
        {
            return new HSRendererEntityExospider(manager);
        }
    }
}
