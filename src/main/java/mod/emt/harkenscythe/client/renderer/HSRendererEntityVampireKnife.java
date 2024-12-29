package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nonnull;

import mod.emt.harkenscythe.entity.HSEntityVampireKnife;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

// TODO: Projectiles need to use the unique texture
public class HSRendererEntityVampireKnife extends Render<HSEntityVampireKnife>
{
    public HSRendererEntityVampireKnife(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull HSEntityVampireKnife entity, double d, double d1, double d2, float f, float f1)
    {
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        GlStateManager.pushMatrix();
        bindEntityTexture(entity);
        GlStateManager.translate(d, d1, d2);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.85f, 0.85f, 0.85f);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        float f15 = entity.arrowShake - f1;

        if (f15 > 0.0f)
        {
            float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
            GlStateManager.rotate(f16, 0.0f, 0.0f, 1.0f);
        }

        GlStateManager.translate(-0.15f, -0.15f, 0.0f);

        if (renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(8214271);
        }

        itemRender.renderItem(getStackToRender(entity), TransformType.NONE);

        if (renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, d, d1, d2, f, f1);
    }

    public ItemStack getStackToRender(HSEntityVampireKnife entity)
    {
        return new ItemStack(HSItems.vampire_knife);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull HSEntityVampireKnife entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public static class Factory implements IRenderFactory<HSEntityVampireKnife>
    {
        @Override
        public Render<? super HSEntityVampireKnife> createRenderFor(RenderManager manager)
        {
            return new HSRendererEntityVampireKnife(manager);
        }
    }
}
