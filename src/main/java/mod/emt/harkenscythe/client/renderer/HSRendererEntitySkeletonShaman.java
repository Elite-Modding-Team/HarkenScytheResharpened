package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nullable;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.HSModelEntitySkeletonShaman;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: Clean up and add glow layer
@SideOnly(Side.CLIENT)
public class HSRendererEntitySkeletonShaman extends RenderBiped<AbstractSkeleton>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/skeleton_shaman.png");

    public HSRendererEntitySkeletonShaman(RenderManager renderManager)
    {
        super(renderManager, new HSModelEntitySkeletonShaman(), 0.5F);
        //this.addLayer(new LayerShamanSkeletonFlames(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new HSModelEntitySkeletonShaman(0.5F, true);
                this.modelArmor = new HSModelEntitySkeletonShaman(1.0F, true);
            }
        });
    }

    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }

    protected void preRenderCallback(AbstractSkeleton entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(0.9F, 0.9F, 0.9F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(AbstractSkeleton entity)
    {
        return TEXTURES;
    }
}
