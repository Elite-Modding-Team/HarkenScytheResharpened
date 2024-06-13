package mod.emt.harkenscythe.client.renderers;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.tileentities.HSTileEntityBloodAltar;
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

@SideOnly(Side.CLIENT)
public class RenderHSBloodAltar extends TileEntitySpecialRenderer<HSTileEntityBloodAltar>
{
    private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/blood_altar_book.png");
    private final ModelBook modelBook = new ModelBook();

    @Override
    public void render(HSTileEntityBloodAltar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
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
        this.bindTexture(TEXTURE_BOOK);
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
            int textColor = te.isValidRecipe() ? 0xFF5555 : 0xFFFFFF;

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
            Minecraft.getMinecraft().fontRenderer.drawString(stackText, -Minecraft.getMinecraft().fontRenderer.getStringWidth(stackText) / 2, 0, textColor);
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();

            RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;
            if (result.typeOfHit != RayTraceResult.Type.BLOCK) return;
            BlockPos resultPos = new BlockPos(result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ());
            if (!resultPos.equals(te.getPos())) return;

            EnumFacing facing = Minecraft.getMinecraft().objectMouseOver.sideHit;
            if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH || facing == EnumFacing.WEST || facing == EnumFacing.EAST)
            {
                double offsetX = 0;
                double offsetZ = 0;
                int angle = 0;
                switch (facing)
                {
                    case NORTH:
                        offsetX = 0.5;
                        offsetZ = -0.02;
                        break;
                    case SOUTH:
                        offsetX = 0.5;
                        offsetZ = 1.02;
                        angle = 180;
                        break;
                    case WEST:
                        offsetX = -0.02;
                        offsetZ = 0.5;
                        angle = 90;
                        break;
                    case EAST:
                        offsetX = 1.02;
                        offsetZ = 0.5;
                        angle = 270;
                        break;
                }

                // Display blood essence item
                GlStateManager.pushMatrix();
                GlStateManager.translate(x + offsetX, y + 0.5, z + offsetZ);
                GlStateManager.rotate(angle, 0, 1, 0);
                GlStateManager.scale(0.5, 0.5, 0.5);
                renderItem.renderItem(te.getEssenceStack(), ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.popMatrix();

                // Display blood essence count
                String bloodText = "x " + te.getBloodCount();
                GlStateManager.pushMatrix();
                GlStateManager.translate(x + offsetX, y + 0.25, z + offsetZ);
                GlStateManager.rotate(angle, 0, 1, 0);
                GlStateManager.scale(-0.025f, -0.025f, 0.025f);
                GlStateManager.disableLighting();
                Minecraft.getMinecraft().fontRenderer.drawString(bloodText, -Minecraft.getMinecraft().fontRenderer.getStringWidth(bloodText) / 2, 0, textColor);
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }
}
