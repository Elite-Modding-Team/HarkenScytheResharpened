package mod.emt.harkenscythe.event.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.compat.patchouli.item.HSItemGuidebook;
import mod.emt.harkenscythe.init.HSSoundEvents;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSEventGuiOpen
{
    private static boolean wasGuidebookOpen = false;

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event)
    {
        if (Loader.isModLoaded("patchouli") && event.getGui() != null && event.getGui().getClass().getName().contains("vazkii.patchouli.client.book.gui"))
        {
            wasGuidebookOpen = true;
        }
        else if (wasGuidebookOpen && Minecraft.getMinecraft().player != null)
        {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player.getHeldItemMainhand().getItem() instanceof HSItemGuidebook || player.getHeldItemOffhand().getItem() instanceof HSItemGuidebook)
            {
                player.playSound(HSSoundEvents.BLOCK_ALTAR_BOOK_CLOSE.getSoundEvent(), 0.6F, 0.7F + player.getRNG().nextFloat() * 0.4F);
            }
            wasGuidebookOpen = false;
        }
    }
}
