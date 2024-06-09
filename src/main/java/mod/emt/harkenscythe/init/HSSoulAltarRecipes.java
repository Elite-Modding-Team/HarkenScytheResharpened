package mod.emt.harkenscythe.init;

import java.util.Map;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;

public class HSSoulAltarRecipes
{
    private static final Map<Item, Item> SOUL_INPUT_OUTPUT_MAP = new Object2ObjectOpenHashMap<>();
    private static final Map<Item, Integer> SOUL_INPUT_SOULS_MAP = new Object2IntOpenHashMap<>();

    public static void addRecipe(Item input, Item output, int requiredSouls)
    {
        SOUL_INPUT_OUTPUT_MAP.put(input, output);
        SOUL_INPUT_SOULS_MAP.put(input, Math.max(requiredSouls, 10));
    }

    public static boolean isValidInput(Item input)
    {
        return SOUL_INPUT_OUTPUT_MAP.containsKey(input) && SOUL_INPUT_SOULS_MAP.containsKey(input);
    }

    public static Item getOutput(Item input)
    {
        return SOUL_INPUT_OUTPUT_MAP.get(input);
    }

    public static int getRequiredSouls(Item input)
    {
        return SOUL_INPUT_SOULS_MAP.get(input);
    }
}
