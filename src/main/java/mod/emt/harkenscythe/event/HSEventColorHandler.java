package mod.emt.harkenscythe.event;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.color.HSArmorDyeableColorHandler;
import mod.emt.harkenscythe.client.color.HSItemSpectralPotionColorHandler;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSEventColorHandler
{
    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event)
    {
        event.getItemColors().registerItemColorHandler(new HSArmorDyeableColorHandler(), HSItems.bloodweave_hood, HSItems.bloodweave_hood, HSItems.bloodweave_robe, HSItems.bloodweave_pants, HSItems.bloodweave_shoes);
        event.getItemColors().registerItemColorHandler(new HSArmorDyeableColorHandler(), HSItems.soulweave_hood, HSItems.soulweave_robe, HSItems.soulweave_pants, HSItems.soulweave_shoes);
        event.getItemColors().registerItemColorHandler(new HSItemSpectralPotionColorHandler(), HSItems.spectral_potion_affliction, HSItems.spectral_potion_affliction);
        event.getItemColors().registerItemColorHandler(new HSItemSpectralPotionColorHandler(), HSItems.spectral_potion_flame, HSItems.spectral_potion_flame);
        event.getItemColors().registerItemColorHandler(new HSItemSpectralPotionColorHandler(), HSItems.spectral_potion_purifying, HSItems.spectral_potion_purifying);
        event.getItemColors().registerItemColorHandler(new HSItemSpectralPotionColorHandler(), HSItems.spectral_potion_water, HSItems.spectral_potion_water);
    }
}
