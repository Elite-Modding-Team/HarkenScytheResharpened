package mod.emt.harkenscythe.compat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.compat.thaumcraft.HSThaumcraftPlugin;
import mod.emt.harkenscythe.compat.tinkers.TinkersConstruct;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSCompatHandler
{
    public static void preInit()
    {
        if (Loader.isModLoaded("tconstruct")) TinkersConstruct.registerToolMaterials();
    }

    public static void init()
    {
        if (Loader.isModLoaded("tconstruct")) TinkersConstruct.registerToolRecipes();
        if (Loader.isModLoaded("thaumcraft")) MinecraftForge.EVENT_BUS.register(HSThaumcraftPlugin.class);
    }

    public static void postInit()
    {
    }
}
