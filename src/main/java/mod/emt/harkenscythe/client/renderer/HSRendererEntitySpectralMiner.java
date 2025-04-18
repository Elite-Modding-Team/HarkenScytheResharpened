package mod.emt.harkenscythe.client.renderer;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entity.HSEntitySpectralMiner;

@SideOnly(Side.CLIENT)
public class HSRendererEntitySpectralMiner extends RenderBiped<HSEntitySpectralMiner>
{
    private static final ResourceLocation SPECTRAL_MINER_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/spectral_miner.png");
    private static final ResourceLocation SPECTRAL_MINER_HURT_TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/spectral_miner_hurt.png");

    public HSRendererEntitySpectralMiner(RenderManager renderManager)
    {
        super(renderManager, new ModelPlayer(0.0F, false), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(HSEntitySpectralMiner entity)
    {
        if (entity.getHealth() < entity.getMaxHealth() / 2)
        {
            return SPECTRAL_MINER_HURT_TEXTURES;
        }
        return SPECTRAL_MINER_TEXTURES;
    }

    public static class Factory implements IRenderFactory<HSEntitySpectralMiner>
    {
        @Override
        public Render<? super HSEntitySpectralMiner> createRenderFor(RenderManager manager)
        {
            return new HSRendererEntitySpectralMiner(manager);
        }
    }
}
