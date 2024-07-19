package mod.emt.harkenscythe.client.renderer;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entity.HSEntitySpectralHuman;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSRendererEntitySpectralHuman extends RenderBiped<HSEntitySpectralHuman>
{
    private static final ResourceLocation SPECTRAL_STEVE_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/spectral_steve.png");
    private static final ResourceLocation SPECTRAL_ALEX_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/spectral_alex.png");

    public HSRendererEntitySpectralHuman(RenderManager renderManager)
    {
        super(renderManager, new ModelPlayer(0.0F, false), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(HSEntitySpectralHuman entity)
    {
        return SPECTRAL_STEVE_TEXTURES;
    }

    public static class Factory implements IRenderFactory<HSEntitySpectralHuman>
    {
        @Override
        public Render<? super HSEntitySpectralHuman> createRenderFor(RenderManager manager)
        {
            return new HSRendererEntitySpectralHuman(manager);
        }
    }
}
