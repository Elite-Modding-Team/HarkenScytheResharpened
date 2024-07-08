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
        BLOOD_INPUT_BLOODCOUNT_MAP.put(input, requiredBlood);
    }

    public static void addBloodRecipe(String oreDictName, Item output, int requiredBlood)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) addBloodRecipe(ore.getItem(), output, requiredBlood);
    }

    public static boolean isValidInputBlood(ItemStack input)
    {
        return BLOOD_INPUT_OUTPUT_MAP.containsKey(input.getItem()) && BLOOD_INPUT_BLOODCOUNT_MAP.containsKey(input.getItem());
    }

    public static ItemStack getOutputBlood(Item input)
    {
        return new ItemStack(BLOOD_INPUT_OUTPUT_MAP.get(input));
    }

    public static int getRequiredBlood(ItemStack input)
    {
        return BLOOD_INPUT_BLOODCOUNT_MAP.get(input.getItem());
    }

    public static void addSoulRecipe(Item input, Item output, int requiredSouls)
    {
        SOUL_INPUT_OUTPUT_MAP.put(input, output);
        SOUL_INPUT_SOULCOUNT_MAP.put(input, requiredSouls);
    }

    public static void addSoulRecipe(String oreDictName, Item output, int requiredSouls)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) addSoulRecipe(ore.getItem(), output, requiredSouls);
    }

    public static boolean isValidInputSoul(ItemStack input)
    {
        return SOUL_INPUT_OUTPUT_MAP.containsKey(input.getItem()) && SOUL_INPUT_SOULCOUNT_MAP.containsKey(input.getItem());
    }

    public static ItemStack getOutputSoul(Item input)
    {
        return new ItemStack(SOUL_INPUT_OUTPUT_MAP.get(input));
    }

    public static int getRequiredSouls(ItemStack input)
    {
        return SOUL_INPUT_SOULCOUNT_MAP.get(input.getItem());
    }
}
