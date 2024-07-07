package mod.emt.harkenscythe.events;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.items.HSDimensionalMirror;
import mod.emt.harkenscythe.items.HSNecronomicon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

// Courtesy of Fuzs
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSRenderSpecificHandEvent
{
    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public static void onRenderHand(RenderSpecificHandEvent event)
    {
        EntityPlayerSP player = mc.player;
        if (player != null && player.isHandActive() && player.getActiveHand() == event.getHand())
        {
            Item heldItem = player.getActiveItemStack().getItem();
            if (heldItem instanceof HSDimensionalMirror || heldItem instanceof HSNecronomicon)
            {
                GlStateManager.pushMatrix();
                boolean rightHanded = (((event.getHand() == EnumHand.MAIN_HAND) ? player.getPrimaryHand() : player.getPrimaryHand().opposite()) == EnumHandSide.RIGHT);
                transformSideFirstPerson(rightHanded ? 1.0F : -1.0F, event.getEquipProgress());
                mc.getItemRenderer().renderItemSide(player, event.getItemStack(), rightHanded ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !rightHanded);
                GlStateManager.popMatrix();
                event.setCanceled(true);
            }
        }
    }

    private static void transformSideFirstPerson(float side, float equippedProg)
    {
        GlStateManager.translate(side * 0.56F, -0.52F + equippedProg * -0.6F, -0.72F);
        GlStateManager.translate(side * -0.14142136F, 0.08F, 0.14142136F);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(side * 78.05F, 0.0F, 0.0F, 1.0F);
    }
}
