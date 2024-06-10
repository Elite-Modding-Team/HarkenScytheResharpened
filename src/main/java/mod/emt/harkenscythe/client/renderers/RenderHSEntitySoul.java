package mod.emt.harkenscythe.client.renderers;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.models.ModelHSEntityEssence;
import mod.emt.harkenscythe.entities.HSEntitySoul;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHSEntitySoul extends Render<HSEntitySoul>
{
    private static final ResourceLocation SOUL_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/soul_common.png");
    private final ModelBase modelEssence = new ModelHSEntityEssence();

    public RenderHSEntitySoul(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.0F;
    }

    @Override
    public void doRender(HSEntitySoul entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        float f = (float) entity.getInnerRotation() + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        this.bindTexture(SOUL_TEXTURES);
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = f1 * f1 + f1;
        this.modelEssence.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(HSEntitySoul entity)
    {
        return SOUL_TEXTURES;
    }

    public static class Factory implements IRenderFactory<HSEntitySoul>
    {
        @Override
        public Render<? super HSEntitySoul> createRenderFor(RenderManager manager)
        {
            return new RenderHSEntitySoul(manager);
        }
    }
}
