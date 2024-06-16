package mod.emt.harkenscythe.events;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSColorHandlerEvent
{
    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event)
    {
        event.getItemColors().registerItemColorHandler(HSItems.bloodweave_hood, HSItems.bloodweave_hood, HSItems.bloodweave_robe, HSItems.bloodweave_pants, HSItems.bloodweave_shoes);
        event.getItemColors().registerItemColorHandler(HSItems.soulweave_hood, HSItems.soulweave_hood, HSItems.soulweave_robe, HSItems.soulweave_pants, HSItems.soulweave_shoes);
    }
}
