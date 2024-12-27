package mod.emt.harkenscythe.client.renderer;

import javax.annotation.Nullable;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.model.HSModelEntityExospider;
import mod.emt.harkenscythe.entity.HSEntityExospider;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSRendererEntityExospider<T extends HSEntityExospider> extends RenderLiving<T>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/exospider.png");

    public HSRendererEntityExospider(RenderManager renderManager)
    {
        super(renderManager, new HSModelEntityExospider(), 1.0F);
    }

    protected float getDeathMaxRotation(T entityLivingBase)
    {
        return 180.0F;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HSEntityExospider entity)
    {
        return TEXTURES;
    }
}
