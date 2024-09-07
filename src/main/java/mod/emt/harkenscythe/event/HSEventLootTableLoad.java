package mod.emt.harkenscythe.event;

import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLootTableLoad
{
    @SubscribeEvent
    public static void onLootTablesLoaded(LootTableLoadEvent event)
    {
        if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON) || event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID) || event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE))
        {
            final LootPool main = event.getTable().getPool("main");
            if (main != null)
            {
                main.addEntry(new LootEntryItem(HSItems.ancient_necronomicon_page, 10, 0, new LootFunction[0], new LootCondition[0], "loottable:ancient_necronomicon_page"));
                main.addEntry(new LootEntryItem(HSItems.lady_harken_scythe, 1, 0, new LootFunction[0], new LootCondition[0], "loottable:lady_harken_scythe"));
                main.addEntry(new LootEntryItem(HSItems.unpowered_totem_of_undying, 1, 0, new LootFunction[0], new LootCondition[0], "loottable:unpowered_totem_of_undying"));
            }
        }

        if (event.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE) || event.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE))
        {
            final LootPool main = event.getTable().getPool("main");
            if (main != null)
            {
                main.addEntry(new LootEntryItem(HSItems.unpowered_totem_of_undying, 2, 0, new LootFunction[0], new LootCondition[0], "loottable:unpowered_totem_of_undying"));
            }
        }
    }
}
