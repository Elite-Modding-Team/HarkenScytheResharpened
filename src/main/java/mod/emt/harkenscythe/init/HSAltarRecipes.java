package mod.emt.harkenscythe.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.recipe.HSRecipeBloodAltar;
import mod.emt.harkenscythe.recipe.HSRecipeSoulAltar;

public class HSAltarRecipes
{
    private static final List<HSRecipeBloodAltar> BLOOD_ALTAR_RECIPES = new ArrayList<>();
    private static final List<HSRecipeBloodAltar> BLOOD_ALTAR_RECIPE_REMOVALS = new ArrayList<>();
    private static final List<HSRecipeSoulAltar> SOUL_ALTAR_RECIPES = new ArrayList<>();
    private static final List<HSRecipeSoulAltar> SOUL_ALTAR_RECIPE_REMOVALS = new ArrayList<>();

    public static void addBloodRecipe(Item input, Item output, int requiredBlood)
    {
        BLOOD_ALTAR_RECIPES.add(new HSRecipeBloodAltar(new ItemStack(input), new ItemStack(output), requiredBlood));
    }

    public static void addBloodRecipe(ItemStack input, ItemStack output, int requiredBlood)
    {
        BLOOD_ALTAR_RECIPES.add(new HSRecipeBloodAltar(input, output, requiredBlood));
    }

    public static void addBloodRecipe(String oreDictName, Item output, int requiredBlood)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) addBloodRecipe(ore.getItem(), output, requiredBlood);
    }

    public static void addBloodRecipesConfig(String[] strings)
    {
        for (String string : strings)
        {
            String[] splits = string.split(";");
            Item input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(splits[0]));
            if (input == null)
            {
                HarkenScythe.LOGGER.error("Custom Blood Altar recipe input is invalid: " + splits[0]);
                HarkenScythe.LOGGER.error("Recipe will be skipped!");
                continue;
            }
            Item output = ForgeRegistries.ITEMS.getValue(new ResourceLocation(splits[1]));
            if (output == null)
            {
                HarkenScythe.LOGGER.error("Custom Blood Altar recipe output is invalid: " + splits[1]);
                HarkenScythe.LOGGER.error("Recipe will be skipped!");
                continue;
            }
            int cost = Integer.parseInt(splits[2]);
            if (cost <= 0)
            {
                HarkenScythe.LOGGER.error("Custom Blood Altar recipe essence cost is invalid: " + splits[2]);
                HarkenScythe.LOGGER.error("Recipe will be skipped!");
                continue;
            }
            HSAltarRecipes.addBloodRecipe(input, output, cost);
        }
    }

    public static void removeBloodRecipe(Item input, Item output, int requiredBlood)
    {
        BLOOD_ALTAR_RECIPE_REMOVALS.add(new HSRecipeBloodAltar(new ItemStack(input), new ItemStack(output), requiredBlood));
    }

    public static void removeBloodRecipe(ItemStack input, ItemStack output, int requiredBlood)
    {
        BLOOD_ALTAR_RECIPE_REMOVALS.add(new HSRecipeBloodAltar(input, output, requiredBlood));
    }

    public static void removeBloodRecipe(String oreDictName, Item output, int requiredBlood)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) removeBloodRecipe(ore.getItem(), output, requiredBlood);
    }

    public static void removeBloodRecipes()
    {
        for (HSRecipeBloodAltar recipeRemoval : BLOOD_ALTAR_RECIPE_REMOVALS)
        {
            for (HSRecipeBloodAltar recipe : BLOOD_ALTAR_RECIPES)
            {
                if (recipe.getInput().getItem() == recipeRemoval.getInput().getItem() && recipe.getOutput().getItem() == recipeRemoval.getOutput().getItem() && recipe.getRequiredBlood() == recipeRemoval.getRequiredBlood())
                {
                    BLOOD_ALTAR_RECIPES.remove(recipe);
                    return;
                }
            }
        }
    }

    public static boolean isValidInputBlood(ItemStack input)
    {
        for (HSRecipeBloodAltar recipe : BLOOD_ALTAR_RECIPES)
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
        for (HSRecipeBloodAltar recipe : BLOOD_ALTAR_RECIPES)
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
        for (HSRecipeBloodAltar recipe : BLOOD_ALTAR_RECIPES)
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
        SOUL_ALTAR_RECIPES.add(new HSRecipeSoulAltar(new ItemStack(input), new ItemStack(output), requiredSouls));
    }

    public static void addSoulRecipe(ItemStack input, ItemStack output, int requiredSouls)
    {
        SOUL_ALTAR_RECIPES.add(new HSRecipeSoulAltar(input, output, requiredSouls));
    }

    public static void addSoulRecipe(String oreDictName, Item output, int requiredSouls)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) addSoulRecipe(ore.getItem(), output, requiredSouls);
    }

    public static void addSoulRecipesConfig(String[] strings)
    {
        for (String string : strings)
        {
            String[] splits = string.split(";");
            Item input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(splits[0]));
            if (input == null)
            {
                HarkenScythe.LOGGER.error("Custom Soul Altar recipe input is invalid: " + splits[0]);
                HarkenScythe.LOGGER.error("Recipe will be skipped!");
                continue;
            }
            Item output = ForgeRegistries.ITEMS.getValue(new ResourceLocation(splits[1]));
            if (output == null)
            {
                HarkenScythe.LOGGER.error("Custom Soul Altar recipe output is invalid: " + splits[1]);
                HarkenScythe.LOGGER.error("Recipe will be skipped!");
                continue;
            }
            int cost = Integer.parseInt(splits[2]);
            if (cost <= 0)
            {
                HarkenScythe.LOGGER.error("Custom Soul Altar recipe essence cost is invalid: " + splits[2]);
                HarkenScythe.LOGGER.error("Recipe will be skipped!");
                continue;
            }
            HSAltarRecipes.addSoulRecipe(input, output, cost);
        }
    }

    public static void removeSoulRecipe(Item input, Item output, int requiredSouls)
    {
        SOUL_ALTAR_RECIPE_REMOVALS.add(new HSRecipeSoulAltar(new ItemStack(input), new ItemStack(output), requiredSouls));
    }

    public static void removeSoulRecipe(ItemStack input, ItemStack output, int requiredSouls)
    {
        SOUL_ALTAR_RECIPE_REMOVALS.add(new HSRecipeSoulAltar(input, output, requiredSouls));
    }

    public static void removeSoulRecipe(String oreDictName, Item output, int requiredSouls)
    {
        List<ItemStack> ores = OreDictionary.getOres(oreDictName);
        for (ItemStack ore : ores) removeSoulRecipe(ore.getItem(), output, requiredSouls);
    }

    public static void removeSoulRecipes()
    {
        for (HSRecipeSoulAltar recipeRemoval : SOUL_ALTAR_RECIPE_REMOVALS)
        {
            for (HSRecipeSoulAltar recipe : SOUL_ALTAR_RECIPES)
            {
                if (recipe.getInput().getItem() == recipeRemoval.getInput().getItem() && recipe.getOutput().getItem() == recipeRemoval.getOutput().getItem() && recipe.getRequiredSouls() == recipeRemoval.getRequiredSouls())
                {
                    SOUL_ALTAR_RECIPES.remove(recipe);
                    return;
                }
            }
        }
    }

    public static boolean isValidInputSoul(ItemStack input)
    {
        for (HSRecipeSoulAltar recipe : SOUL_ALTAR_RECIPES)
        {
            if (recipe.getInput().getItem() == input.getItem())
            {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getOutputSoul(Item input)
    {
        for (HSRecipeSoulAltar recipe : SOUL_ALTAR_RECIPES)
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
        for (HSRecipeSoulAltar recipe : SOUL_ALTAR_RECIPES)
        {
            if (recipe.getInput().getItem() == input.getItem())
            {
                return recipe.getRequiredSouls();
            }
        }
        return 0;
    }

    public static List<HSRecipeBloodAltar> getBloodAltarRecipes()
    {
        return BLOOD_ALTAR_RECIPES;
    }

    public static List<HSRecipeSoulAltar> getSoulAltarRecipes()
    {
        return SOUL_ALTAR_RECIPES;
    }
}
