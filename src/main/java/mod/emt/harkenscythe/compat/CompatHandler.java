package mod.emt.harkenscythe.compat;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.compat.thaumcraft.ThaumcraftIntegration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class CompatHandler
{
    public static void preInit()
    {
    }

    public static void init()
    {
        if (Loader.isModLoaded("thaumcraft")) MinecraftForge.EVENT_BUS.register(ThaumcraftIntegration.class);
    }

    public static void postInit()
    {
    }
}
