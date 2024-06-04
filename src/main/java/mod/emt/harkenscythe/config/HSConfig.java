package mod.emt.harkenscythe.config;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = HarkenScythe.MOD_ID, name = HarkenScythe.NAME)
public class HSConfig
{
    @Config.Comment("General Settings")
    public static GeneralSettings generalSettings = new GeneralSettings();

    public static class GeneralSettings
    {
        @Config.Name("Debug Mode")
        @Config.Comment("Prints debug values to console")
        public boolean debug = false;
    }

    @Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(HarkenScythe.MOD_ID))
            {
                ConfigManager.sync(HarkenScythe.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
