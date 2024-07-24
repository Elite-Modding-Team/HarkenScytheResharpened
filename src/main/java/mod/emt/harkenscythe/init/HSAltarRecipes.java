package mod.emt.harkenscythe.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import mod.emt.harkenscythe.recipe.BloodAltarRecipe;
import mod.emt.harkenscythe.recipe.SoulAltarRecipe;

public class HSAltarRecipes
{
    private static final List<BloodAltarRecipe> BLOOD_ALTAR_RECIPES = new ArrayList<>();
    private static final List<SoulAltarRecipe> SOUL_ALTAR_RECIPES = new ArrayList<>();

    public static void addBloodRecipe(Item input, Item output, int requiredBlood)
    {
        BLOOD_ALTAR_RECIPES.add(new BloodAltarRecipe(new ItemStack(input), new ItemStack(output), requiredBlood));
    }

    public static void addBloodRecipe(String oreDictName, Item output, int requiredBlood)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) addBloodRecipe(ore.getItem(), output, requiredBlood);
    }

    public static boolean isValidInputBlood(ItemStack input)
    {
        for (BloodAltarRecipe recipe : BLOOD_ALTAR_RECIPES)
        {
            if (recipe.getInput().isItemEqual(input))
            {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getOutputBlood(Item input)
    {
        for (BloodAltarRecipe recipe : BLOOD_ALTAR_RECIPES)
        {
            if (recipe.getInput().getItem() == input)
            {
                return recipe.getOutput();
            }
        }
        return ItemStack.EMPTY;
    }

    public static int getRequiredBlood(ItemStack input)
    {
        for (BloodAltarRecipe recipe : BLOOD_ALTAR_RECIPES)
        {
            if (recipe.getInput().isItemEqual(input))
            {
                return recipe.getRequiredBlood();
            }
        }
        return 0;
    }

    public static void addSoulRecipe(Item input, Item output, int requiredSouls)
    {
        SOUL_ALTAR_RECIPES.add(new SoulAltarRecipe(new ItemStack(input), new ItemStack(output), requiredSouls));
    }

    public static void addSoulRecipe(String oreDictName, Item output, int requiredSouls)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) addSoulRecipe(ore.getItem(), output, requiredSouls);
    }

    public static boolean isValidInputSoul(ItemStack input)
    {
        for (SoulAltarRecipe recipe : SOUL_ALTAR_RECIPES)
        {
            if (recipe.getInput().isItemEqual(input))
            {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getOutputSoul(Item input)
    {
        for (SoulAltarRecipe recipe : SOUL_ALTAR_RECIPES)
        {
            if (recipe.getInput().getItem() == input)
            {
                return recipe.getOutput();
            }
        }
        return ItemStack.EMPTY;
    }

    public static int getRequiredSouls(ItemStack input)
    {
        for (SoulAltarRecipe recipe : SOUL_ALTAR_RECIPES)
        {
            if (recipe.getInput().isItemEqual(input))
            {
                return recipe.getRequiredSouls();
            }
        }
        return 0;
    }

    public static List<BloodAltarRecipe> getBloodAltarRecipes()
    {
        return BLOOD_ALTAR_RECIPES;
    }

    public static List<SoulAltarRecipe> getSoulAltarRecipes()
    {
        return SOUL_ALTAR_RECIPES;
    }
}
