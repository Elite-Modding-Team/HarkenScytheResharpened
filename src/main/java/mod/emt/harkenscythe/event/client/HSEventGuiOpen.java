package mod.emt.harkenscythe.event.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.emt.harkenscythe.HarkenScythe;
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
        else if (wasGuidebookOpen)
        {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            player.playSound(HSSoundEvents.GUIDE_CLOSE.getSoundEvent(), 1.0F, 0.7F + player.getRNG().nextFloat() * 0.4F);
            wasGuidebookOpen = false;
        }
    }
}
