package mod.emt.harkenscythe.compat.hwyla;

import mcp.mobius.waila.api.IWailaRegistrar;
import mod.emt.harkenscythe.block.HSBlockBiomassCrop;

@SuppressWarnings("unused")
public class HSHWYLAPlugin
{
    public static void register(IWailaRegistrar iWailaRegistrar)
    {
        iWailaRegistrar.registerBodyProvider(new HSHWYLABiomassCrop(), HSBlockBiomassCrop.class);
    }
}
