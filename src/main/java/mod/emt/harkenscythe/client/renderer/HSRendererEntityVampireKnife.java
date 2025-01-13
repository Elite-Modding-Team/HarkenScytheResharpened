package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nonnull;

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

import mod.emt.harkenscythe.entity.HSEntityVampireKnife;
import mod.emt.harkenscythe.init.HSItems;

public class HSRendererEntityVampireKnife extends Render<HSEntityVampireKnife>
{
    private static final ItemStack PROJECTILE = new ItemStack(HSItems.vampire_knife_projectile);

    public HSRendererEntityVampireKnife(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull HSEntityVampireKnife entity, double x, double y, double z, float yaw, float partialTicks)
    {
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        GlStateManager.pushMatrix();
        bindEntityTexture(entity);
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.85f, 0.85f, 0.85f);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks - 45.0f, 0.0f, 0.0f, 1.0f);
        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        float f1 = entity.arrowShake - partialTicks;

        if (f1 > 0.0f)
        {
            float f2 = -MathHelper.sin(f1 * 3.0f) * f1;
            GlStateManager.rotate(f2, 0.0f, 0.0f, 1.0f);
        }

        GlStateManager.translate(-0.15f, -0.15f, 0.0f);

        if (renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(15539236);
        }

        itemRender.renderItem(PROJECTILE, TransformType.NONE);

        if (renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, yaw, partialTicks);
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
