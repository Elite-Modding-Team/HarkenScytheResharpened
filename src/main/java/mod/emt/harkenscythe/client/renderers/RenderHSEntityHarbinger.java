package mod.emt.harkenscythe.client.renderers;

import javax.annotation.Nullable;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.models.ModelHSHarbinger;
import mod.emt.harkenscythe.entities.HSEntityHarbinger;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHSEntityHarbinger extends RenderBiped<HSEntityHarbinger>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/harbinger.png");

    public RenderHSEntityHarbinger(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelHSHarbinger(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HSEntityHarbinger entity)
    {
        return TEXTURES;
    }
    
    public static class Factory implements IRenderFactory<HSEntityHarbinger>
    {
        @Override
        public Render<? super HSEntityHarbinger> createRenderFor(RenderManager manager)
        {
            return new RenderHSEntityHarbinger(manager);
        }
    }
}
