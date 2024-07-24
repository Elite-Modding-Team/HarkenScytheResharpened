package mod.emt.harkenscythe.event.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.item.HSItemDimensionalMirror;
import mod.emt.harkenscythe.item.HSItemNecronomicon;

// Courtesy of Fuzs
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSEventRenderSpecificHand
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final ResourceLocation NECRONOMICON_ACTIVE = new ResourceLocation(HarkenScythe.MOD_ID, "textures/items/ancient_necronomicon_active.png");

    @SubscribeEvent
    public static void onRenderHand(RenderSpecificHandEvent event)
    {
        EntityPlayerSP player = mc.player;
        if (player != null && player.isHandActive() && player.getActiveHand() == event.getHand())
        {
            Item heldItem = player.getActiveItemStack().getItem();
            if (heldItem instanceof HSItemDimensionalMirror)
            {
                GlStateManager.pushMatrix();
                boolean rightHanded = (((event.getHand() == EnumHand.MAIN_HAND) ? player.getPrimaryHand() : player.getPrimaryHand().opposite()) == EnumHandSide.RIGHT);
                transformSideFirstPerson(rightHanded ? 1.0F : -1.0F, event.getEquipProgress());
                mc.getItemRenderer().renderItemSide(player, event.getItemStack(), rightHanded ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !rightHanded);
                GlStateManager.popMatrix();
                event.setCanceled(true);
            }
            else if (heldItem instanceof HSItemNecronomicon)
            {
                renderItemFirstPersonBimanual(event.getInterpolatedPitch(), event.getEquipProgress(), event.getSwingProgress());
                event.setCanceled(true);
            }
        }
    }

    private static void transformSideFirstPerson(float side, float equipProgress)
    {
        GlStateManager.translate(side * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
        GlStateManager.translate(side * -0.14142136F, 0.08F, 0.14142136F);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(side * 78.05F, 0.0F, 0.0F, 1.0F);
    }

    private static void renderItemFirstPersonBimanual(float partialTicks, float equipProgress, float swingProgress)
    {
        float f = MathHelper.sqrt(swingProgress);
        float f1 = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
        float f2 = -0.4F * MathHelper.sin(f * (float) Math.PI);
        GlStateManager.translate(0.0F, -f1 / 2.0F, f2);
        float f3 = getItemAngleFromPitch(partialTicks);
        GlStateManager.translate(0.0F, 0.04F + equipProgress * -1.2F + f3 * -0.5F, -0.72F);
        GlStateManager.rotate(f3 * -85.0F, 1.0F, 0.0F, 0.0F);
        renderArms();
        float f4 = MathHelper.sin(f * (float) Math.PI);
        GlStateManager.rotate(f4 * 20.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.scale(0.38F, 0.38F, 0.38F);
        GlStateManager.disableLighting();
        mc.getTextureManager().bindTexture(NECRONOMICON_ACTIVE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.translate(-0.5F, -0.5F, 0.0F);
        GlStateManager.scale(0.0078125F, 0.0078125F, 0.0078125F);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableLighting();
    }

    private static float getItemAngleFromPitch(float pitch)
    {
        float f = 1.0F - pitch / 45.0F + 0.1F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = -MathHelper.cos(f * (float) Math.PI) * 0.5F + 0.5F;
        return f;
    }

    private static void renderArms()
    {
        if (!mc.player.isInvisible())
        {
            GlStateManager.disableCull();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            renderArm(EnumHandSide.RIGHT);
            renderArm(EnumHandSide.LEFT);
            GlStateManager.popMatrix();
            GlStateManager.enableCull();
        }
    }

    private static void renderArm(EnumHandSide handSide)
    {
        mc.getTextureManager().bindTexture(mc.player.getLocationSkin());
        Render<AbstractClientPlayer> render = mc.getRenderManager().getEntityRenderObject(mc.player);
        if (render != null)
        {
            RenderPlayer renderplayer = (RenderPlayer) render;
            GlStateManager.pushMatrix();
            float f = handSide == EnumHandSide.RIGHT ? 1.0F : -1.0F;
            GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f * -41.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(f * 0.3F, -1.1F, 0.45F);
            if (handSide == EnumHandSide.RIGHT)
            {
                renderplayer.renderRightArm(mc.player);
            }
            else
            {
                renderplayer.renderLeftArm(mc.player);
            }
            GlStateManager.popMatrix();
        }
    }
}
