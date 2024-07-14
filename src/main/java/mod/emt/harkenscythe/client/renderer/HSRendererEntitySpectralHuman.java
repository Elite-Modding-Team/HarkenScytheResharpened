package mod.emt.harkenscythe.client.renderer;

import mod.emt.harkenscythe.entity.HSEntitySpectralHuman;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSRendererEntitySpectralHuman extends RenderBiped<HSEntitySpectralHuman>
{
    public HSRendererEntitySpectralHuman(RenderManager renderManager)
    {
        super(renderManager, new ModelPlayer(0.0F, false), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(HSEntitySpectralHuman entity)
    {
        NetworkPlayerInfo networkPlayerInfo = entity.getNetworkPlayerInfo();
        return networkPlayerInfo == null ? DefaultPlayerSkin.getDefaultSkin(entity.getUniqueID()) : networkPlayerInfo.getLocationSkin();
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
