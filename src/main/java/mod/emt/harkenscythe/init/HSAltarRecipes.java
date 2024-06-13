package mod.emt.harkenscythe.init;

import java.util.List;
import java.util.Map;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class HSAltarRecipes
{
    private static final Map<Item, Item> BLOOD_INPUT_OUTPUT_MAP = new Object2ObjectOpenHashMap<>();
    private static final Map<Item, Integer> BLOOD_INPUT_BLOODCOUNT_MAP = new Object2IntOpenHashMap<>();
    private static final Map<Item, Item> SOUL_INPUT_OUTPUT_MAP = new Object2ObjectOpenHashMap<>();
    private static final Map<Item, Integer> SOUL_INPUT_SOULCOUNT_MAP = new Object2IntOpenHashMap<>();

    public static void addBloodRecipe(Item input, Item output, int requiredBlood)
    {
        BLOOD_INPUT_OUTPUT_MAP.put(input, output);
        BLOOD_INPUT_BLOODCOUNT_MAP.put(input, Math.max(requiredBlood, 10));
    }

    public static void addBloodRecipe(String oreDictName, Item output, int requiredBlood)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) addBloodRecipe(ore.getItem(), output, requiredBlood);
    }

    public static boolean isValidInputBlood(Item input)
    {
        return BLOOD_INPUT_OUTPUT_MAP.containsKey(input) && BLOOD_INPUT_BLOODCOUNT_MAP.containsKey(input);
    }

    public static Item getOutputBlood(Item input)
    {
        return BLOOD_INPUT_OUTPUT_MAP.get(input);
    }

    public static int getRequiredBlood(Item input)
    {
        return BLOOD_INPUT_BLOODCOUNT_MAP.get(input);
    }

    public static void addSoulRecipe(Item input, Item output, int requiredSouls)
    {
        SOUL_INPUT_OUTPUT_MAP.put(input, output);
        SOUL_INPUT_SOULCOUNT_MAP.put(input, Math.max(requiredSouls, 10));
    }

    public static void addSoulRecipe(String oreDictName, Item output, int requiredSouls)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) addSoulRecipe(ore.getItem(), output, requiredSouls);
    }

    public static boolean isValidInputSoul(Item input)
    {
        return SOUL_INPUT_OUTPUT_MAP.containsKey(input) && SOUL_INPUT_SOULCOUNT_MAP.containsKey(input);
    }

    public static Item getOutputSoul(Item input)
    {
        return SOUL_INPUT_OUTPUT_MAP.get(input);
    }

    public static int getRequiredSouls(Item input)
    {
        return SOUL_INPUT_SOULCOUNT_MAP.get(input);
    }
}
