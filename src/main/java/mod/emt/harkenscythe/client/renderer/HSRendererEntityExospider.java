package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.HSModelEntityExospider;
import mod.emt.harkenscythe.entity.HSEntityExospider;

@SideOnly(Side.CLIENT)
public class HSRendererEntityExospider extends RenderLiving<HSEntityExospider>
{
    private static final ResourceLocation[] EXOSPIDER_TEXTURES = new ResourceLocation[] {
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/exospider.png"),
        new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/exospider_biomass.png")
    };

    public HSRendererEntityExospider(RenderManager renderManager)
    {
        super(renderManager, new HSModelEntityExospider(), 1.0F);
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
