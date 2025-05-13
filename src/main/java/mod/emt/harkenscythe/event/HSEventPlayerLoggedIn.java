package mod.emt.harkenscythe.event;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.compat.patchouli.HSPatchouliPlugin;
import mod.emt.harkenscythe.config.HSConfig;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventPlayerLoggedIn
{
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (Loader.isModLoaded("patchouli") && !HSConfig.GENERAL.disableGuidebook && HSConfig.ITEMS.reaperGuidebookProvision && !event.player.getTags().contains("hasReaperGuidebook"))
        {
            HSPatchouliPlugin.giveBookToPlayer(event.player);
            event.player.addTag("hasReaperGuidebook");
        }
    }
}
