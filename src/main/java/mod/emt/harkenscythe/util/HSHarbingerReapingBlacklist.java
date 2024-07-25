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

public class HSHarbingerReapingBlacklist
{
    public static final List<EntityEntry> BLACKLISTED_ENTITY_ENTRIES = new ArrayList<>();

    public static void initBlacklistedEntityEntries()
    {
        BLACKLISTED_ENTITY_ENTRIES.clear();
        try
        {
            for (String entry : HSConfig.ENTITIES.harbingerReapingBlacklist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.ENTITIES.containsKey(resLoc)) BLACKLISTED_ENTITY_ENTRIES.add(ForgeRegistries.ENTITIES.getValue(resLoc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        HarkenScythe.LOGGER.info("Harbinger reaping blacklist initialized");
    }

    public static boolean isBlacklisted(Entity entity)
    {
        return BLACKLISTED_ENTITY_ENTRIES.contains(EntityRegistry.getEntry(entity.getClass()));
    }
}
