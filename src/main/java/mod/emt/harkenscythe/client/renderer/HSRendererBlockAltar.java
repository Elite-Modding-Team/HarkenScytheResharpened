package mod.emt.harkenscythe.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.tileentity.HSTileEntityAltar;

@SideOnly(Side.CLIENT)
public abstract class HSRendererBlockAltar extends TileEntitySpecialRenderer<HSTileEntityAltar>
{
    private final ModelBook modelBook = new ModelBook();

    public ResourceLocation getBookTexture()
    {
        return null;
    }

    public int getTextColor(HSTileEntityAltar te)
    {
        return 0xFFFFFF;
    }

    @Override
    public void render(HSTileEntityAltar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        // RENDER BOOK
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
        float f = (float) te.tickCount + partialTicks;
        GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
        float f1;

        for (f1 = te.bookRotation - te.bookRotationPrev; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {}

        while (f1 < -(float) Math.PI)
        {
            f1 += ((float) Math.PI * 2F);
        }

        float f2 = te.bookRotationPrev + f1 * partialTicks;
        GlStateManager.rotate(-f2 * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
        this.bindTexture(getBookTexture());
        float f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25F;
        float f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75F;
        f3 = (f3 - (float) MathHelper.fastFloor(f3)) * 1.6F - 0.3F;
        f4 = (f4 - (float) MathHelper.fastFloor(f4)) * 1.6F - 0.3F;

        if (f3 < 0.0F)
        {
            f3 = 0.0F;
        }

        if (f4 < 0.0F)
        {
            f4 = 0.0F;
        }

        if (f3 > 1.0F)
        {
            f3 = 1.0F;
        }

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
        GlStateManager.enableCull();
        this.modelBook.render(null, f, f3, f4, f5, 0.0F, 0.0625F);
        GlStateManager.popMatrix();

        // RENDER ITEMS & COUNTS
        if (!te.getInputStack().isEmpty())
        {
            float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

            // Display input item
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 2.0, z + 0.5);
            GlStateManager.rotate(timeD, 0, 1, 0);
            GlStateManager.scale(0.8, 0.8, 0.8);
            renderItem.renderItem(te.getInputStack(), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();

            // Display input item count
            String stackText = String.valueOf(te.getInputStack().getCount());
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
            GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0, 1, 0);
            GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1, 0, 0);
            GlStateManager.scale(-0.025f, -0.025f, 0.025f);
            GlStateManager.disableLighting();
            Minecraft.getMinecraft().fontRenderer.drawString(stackText, -Minecraft.getMinecraft().fontRenderer.getStringWidth(stackText) / 2, 0, getTextColor(te));
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();

            RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;
            if (result == null || result.typeOfHit != RayTraceResult.Type.BLOCK) return;
            BlockPos resultPos = new BlockPos(result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ());
            if (!resultPos.equals(te.getPos())) return;

            EnumFacing facing = Minecraft.getMinecraft().objectMouseOver.sideHit;
            switch (facing)
            {
                case NORTH:
                    displayContent(te, x, y, z, 0.5, -0.02, 0, getTextColor(te), renderItem);
                    break;
                case SOUTH:
                    displayContent(te, x, y, z, 0.5, 1.02, 180, getTextColor(te), renderItem);
                    break;
                case WEST:
                    displayContent(te, x, y, z, -0.02, 0.5, 90, getTextColor(te), renderItem);
                    break;
                case EAST:
                    displayContent(te, x, y, z, 1.02, 0.5, 270, getTextColor(te), renderItem);
                    break;
                default:
                    displayContent(te, x, y, z, 0.5, -0.02, 0, getTextColor(te), renderItem);
                    displayContent(te, x, y, z, 0.5, 1.02, 180, getTextColor(te), renderItem);
                    displayContent(te, x, y, z, -0.02, 0.5, 90, getTextColor(te), renderItem);
                    displayContent(te, x, y, z, 1.02, 0.5, 270, getTextColor(te), renderItem);
                    break;
            }
        }
    }

    private void displayContent(HSTileEntityAltar te, double x, double y, double z, double offsetX, double offsetZ, int angle, int textColor, RenderItem renderItem)
    {
        // Display essence item
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + offsetX, y + 0.5, z + offsetZ);
        GlStateManager.rotate(angle, 0, 1, 0);
        GlStateManager.scale(0.5, 0.5, 0.5);
        renderItem.renderItem(te.getEssenceStack(), ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();

        // Display essence count
        String essenceCountText = "x " + te.getEssenceCount();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + offsetX, y + 0.25, z + offsetZ);
        GlStateManager.rotate(angle, 0, 1, 0);
        GlStateManager.scale(-0.025f, -0.025f, 0.025f);
        GlStateManager.disableLighting();
        Minecraft.getMinecraft().fontRenderer.drawString(essenceCountText, -Minecraft.getMinecraft().fontRenderer.getStringWidth(essenceCountText) / 2, 0, textColor);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
