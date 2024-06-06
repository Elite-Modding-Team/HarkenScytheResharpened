package mod.emt.harkenscythe;

import mod.emt.harkenscythe.init.HSCreativeTabs;
import mod.emt.harkenscythe.init.HSRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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
    public static final CreativeTabs TAB = new HSCreativeTabs(CreativeTabs.CREATIVE_TAB_ARRAY.length, MOD_ID + ".tab");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        HSRegistry.registerTileEntities();
        LOGGER.info(NAME + " initialized");
    }
}
