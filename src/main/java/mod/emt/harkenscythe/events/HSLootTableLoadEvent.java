package mod.emt.harkenscythe.events;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSLootTableLoadEvent
{
    @SubscribeEvent
    public static void onLootTablesLoaded(LootTableLoadEvent event)
    {
        if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON))
        {
            final LootPool main = event.getTable().getPool("main");
            if (main != null)
            {
                main.addEntry(new LootEntryItem(HSItems.lady_harken_scythe, 5, 0, new LootFunction[0], new LootCondition[0], "loottable:lady_harken_scythe"));
            }
        }
    }
}
