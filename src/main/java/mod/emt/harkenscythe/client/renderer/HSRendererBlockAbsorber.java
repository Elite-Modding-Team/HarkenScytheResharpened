package mod.emt.harkenscythe.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.tileentity.HSTileEntityAbsorber;

@SideOnly(Side.CLIENT)
public class HSRendererBlockAbsorber extends TileEntitySpecialRenderer<HSTileEntityAbsorber>
{
    @Override
    public void render(HSTileEntityAbsorber te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if (!te.getInputStack().isEmpty())
        {
            float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

            // Display input item
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 1.25, z + 0.5);
            GlStateManager.rotate(timeD, 0, 1, 0);
            GlStateManager.scale(0.8, 0.8, 0.8);
            renderItem.renderItem(te.getInputStack(), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
    }
}
