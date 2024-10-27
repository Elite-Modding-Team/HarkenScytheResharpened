package mod.emt.harkenscythe.compat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.compat.thaumcraft.HSThaumcraftPlugin;
import mod.emt.harkenscythe.compat.tinkers.ConstructsArmory;
import mod.emt.harkenscythe.compat.tinkers.TinkersConstruct;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSCompatHandler
{
    public static void preInit()
    {
        if (Loader.isModLoaded("tconstruct"))
        {
            TinkersConstruct.preInit();
        }
    }

    public static void init() {
        if (Loader.isModLoaded("tconstruct"))
        {
            TinkersConstruct.init();

            // Only load Construct's Armory if Tinkers' Construct is also loaded
            if (Loader.isModLoaded("conarm"))
            {
                ConstructsArmory.preInit();
            }
        }

        if (Loader.isModLoaded("thaumcraft")) MinecraftForge.EVENT_BUS.register(HSThaumcraftPlugin.class);
    }

    public static void postInit()
    {
    }
}
