package mod.emt.harkenscythe.util;

import java.util.ArrayList;
import java.util.List;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;

public class HSDimensionBlacklist
{
    private static final List<Integer> DIMENSION_BLACKLIST = new ArrayList<>();

    public static void initBlacklistedDimensions()
    {
        DIMENSION_BLACKLIST.clear();
        try
        {
            for (Integer dimensionID : HSConfig.ITEMS.dimensionalMirrorDimensionBlacklist)
            {
                DIMENSION_BLACKLIST.add(dimensionID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        HarkenScythe.LOGGER.info("Dimension blacklist initialized");
    }

    public static boolean isBlacklisted(int dimensionID)
    {
        return DIMENSION_BLACKLIST.contains(dimensionID);
    }
}
