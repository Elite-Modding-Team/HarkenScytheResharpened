package mod.emt.harkenscythe;

import mod.emt.harkenscythe.init.HSRegistry;
import mod.emt.harkenscythe.network.HSNetworkHandler;
import mod.emt.harkenscythe.util.HSCreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static mod.emt.harkenscythe.HarkenScythe.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, acceptedMinecraftVersions = ACCEPTED_VERSIONS)
public class HarkenScythe
{
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String NAME = "Harken Scythe";
    public static final String VERSION = Tags.VERSION;
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final CreativeTabs TAB = new HSCreativeTab(CreativeTabs.CREATIVE_TAB_ARRAY.length, MOD_ID + ".tab");

    @Mod.Instance
    public static HarkenScythe instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        HSNetworkHandler.registerPackets();
        LOGGER.info(NAME + " pre-initialized");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        HSRegistry.registerTileEntities();
        HSRegistry.registerRecipes();
        LOGGER.info(NAME + " initialized");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LOGGER.info(NAME + " post-initialized");
    }
}
