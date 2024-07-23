package mod.emt.harkenscythe.config;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.util.HSHarbingerReapingBlacklist;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = HarkenScythe.MOD_ID, name = "HarkenScythe")
public class HSConfig
{
    @Config.LangKey("cfg.harkenscythe.general")
    @Config.Name("General Settings")
    public static final GeneralSettings GENERAL_SETTINGS = new GeneralSettings();

    @Config.LangKey("cfg.harkenscythe.entity")
    @Config.Name("Entity Settings")
    public static final EntitySettings ENTITY_SETTINGS = new EntitySettings();

    public static class GeneralSettings
    {
        @Config.Name("Debug Mode")
        @Config.Comment("Prints debug values to console")
        public boolean debugMode = false;
    }

    public static class EntitySettings
    {
        @Config.Name("Harbinger Reaping Blacklist")
        @Config.Comment
            ({
                "Syntax:  modid:entity",
                "Example: minecraft:cow"
            })
        public String[] harbingerReapingBlacklist = new String[] {};
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
                HSHarbingerReapingBlacklist.initBlacklistedEntityEntries();
            }
        }
    }
}
