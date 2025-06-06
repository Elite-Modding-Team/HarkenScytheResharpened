package mod.emt.harkenscythe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import mod.emt.harkenscythe.compat.HSCompatHandler;
import mod.emt.harkenscythe.compat.crafttweaker.HSCraftTweakerPlugin;
import mod.emt.harkenscythe.init.HSRegistry;
import mod.emt.harkenscythe.network.HSNetworkHandler;
import mod.emt.harkenscythe.util.HSCreativeTab;
import mod.emt.harkenscythe.util.HSDimensionBlacklist;
import mod.emt.harkenscythe.util.HSEntityBlacklists;

import static mod.emt.harkenscythe.HarkenScythe.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, acceptedMinecraftVersions = ACCEPTED_VERSIONS, dependencies = DEPENDENCIES)
public class HarkenScythe
{
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_PREFIX = MOD_ID + ":";
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final String DEPENDENCIES = "required-after:baubles;after:thaumcraft;after:tconstruct;after:conarm;";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final CreativeTabs TAB = new HSCreativeTab(CreativeTabs.CREATIVE_TAB_ARRAY.length, MOD_ID + ".tab");

    @Mod.Instance
    public static HarkenScythe instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        HSNetworkHandler.registerPackets();
        HSCompatHandler.preInit();
        FMLInterModComms.sendMessage("waila", "register", "mod.emt.harkenscythe.compat.hwyla.HSHWYLAPlugin.register");
        LOGGER.info(NAME + " pre-initialized");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        HSRegistry.registerAdvancements();
        HSRegistry.registerTileEntities();
        HSCompatHandler.init();
        LOGGER.info(NAME + " initialized");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        HSDimensionBlacklist.initBlacklistedDimensions();
        HSEntityBlacklists.initBlacklistedEntityEntries();
        if (Loader.isModLoaded("crafttweaker"))
        {
            HSCraftTweakerPlugin.applyActions();
        }
        LOGGER.info(NAME + " post-initialized");
    }
}
