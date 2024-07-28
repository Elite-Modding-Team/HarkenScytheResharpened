package mod.emt.harkenscythe.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;

public class HSEntityBlacklists
{
    private static final List<EntityEntry> HARBINGER_REAPING_BLACKLIST = new ArrayList<>();
    private static final List<EntityEntry> SPECTRAL_ENTITY_BLACKLIST = new ArrayList<>();

    public static void initBlacklistedEntityEntries()
    {
        HARBINGER_REAPING_BLACKLIST.clear();
        SPECTRAL_ENTITY_BLACKLIST.clear();
        try
        {
            for (String entry : HSConfig.ENTITIES.harbingerReapingBlacklist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.ENTITIES.containsKey(resLoc)) HARBINGER_REAPING_BLACKLIST.add(ForgeRegistries.ENTITIES.getValue(resLoc));
            }
            for (String entry : HSConfig.ENTITIES.spectralEntityBlacklist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.ENTITIES.containsKey(resLoc)) SPECTRAL_ENTITY_BLACKLIST.add(ForgeRegistries.ENTITIES.getValue(resLoc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        HarkenScythe.LOGGER.info("Entity blacklists initialized");
    }

    public static boolean isBlacklistedForReaping(Entity entity)
    {
        return HARBINGER_REAPING_BLACKLIST.contains(EntityRegistry.getEntry(entity.getClass()));
    }

    public static boolean isBlacklistedForSummoning(Entity entity)
    {
        return SPECTRAL_ENTITY_BLACKLIST.contains(EntityRegistry.getEntry(entity.getClass()));
    }
}
