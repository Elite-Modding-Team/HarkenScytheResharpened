package mod.emt.harkenscythe.client.renderer;

import mod.emt.harkenscythe.entity.HSEntitySpectralPotion;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSRendererEntitySpectralPotion extends RenderSnowball<HSEntitySpectralPotion>
{
    public HSRendererEntitySpectralPotion(RenderManager renderManager, RenderItem itemRenderer)
    {
        super(renderManager, HSItems.spectral_glass_bottle, itemRenderer);
    }

    @Override
    public ItemStack getStackToRender(HSEntitySpectralPotion entity)
    {
        return entity.getPotionStack();
    }

    public static class Factory implements IRenderFactory<HSEntitySpectralPotion>
    {
        @Override
        public Render<? super HSEntitySpectralPotion> createRenderFor(RenderManager manager)
        {
            return new HSRendererEntitySpectralPotion(manager, Minecraft.getMinecraft().getRenderItem());
        }
    }
}
