package mod.emt.harkenscythe.compat.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseListAddition;
import com.blamejared.mtlib.utils.BaseListRemoval;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import java.util.Collections;
import java.util.List;
import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.recipe.HSRecipeSoulAltar;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenRegister
@ZenClass("mods.harkenscythe.soulAltar")
public class HSCraftTweakerSoulAltarRecipes
{
    @ZenMethod
    public static void add(IItemStack input, IItemStack output, int requiredSouls)
    {
        HSCraftTweakerPlugin.LATE_ADDITIONS.add(new Add(Collections.singletonList(new HSRecipeSoulAltar(InputHelper.toStack(input), InputHelper.toStack(output), requiredSouls))));
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output)
    {
        HSCraftTweakerPlugin.LATE_REMOVALS.add(new RemoveByOutput(output));
    }

    @ZenMethod
    public static void removeByInput(IItemStack output)
    {
        HSCraftTweakerPlugin.LATE_REMOVALS.add(new RemoveByInput(output));
    }

    private static class Add extends BaseListAddition<HSRecipeSoulAltar>
    {
        protected Add(List<HSRecipeSoulAltar> recipes)
        {
            super("soulAltar", HSAltarRecipes.getSoulAltarRecipes(), recipes);
        }

        @Override
        public String getRecipeInfo(HSRecipeSoulAltar recipe)
        {
            return LogHelper.getStackDescription(recipe.getOutput());
        }
    }

    private static class RemoveByOutput extends BaseListRemoval<HSRecipeSoulAltar>
    {
        private final IItemStack output;

        protected RemoveByOutput(IItemStack output)
        {
            super("soulAltar", HSAltarRecipes.getSoulAltarRecipes());
            this.output = output;
        }

        @Override
        public void apply()
        {
            for (HSRecipeSoulAltar recipe : HSAltarRecipes.getSoulAltarRecipes())
            {
                if (output.matches(InputHelper.toIItemStack(recipe.getOutput())))
                {
                    recipes.add(recipe);
                }
            }
            super.apply();
        }

        @Override
        public String getRecipeInfo(HSRecipeSoulAltar recipe)
        {
            return LogHelper.getStackDescription(recipe.getOutput());
        }
    }

    private static class RemoveByInput extends BaseListRemoval<HSRecipeSoulAltar>
    {
        private final IItemStack input;

        protected RemoveByInput(IItemStack input)
        {
            super("soulAltar", HSAltarRecipes.getSoulAltarRecipes());
            this.input = input;
        }

        @Override
        public void apply()
        {
            for (HSRecipeSoulAltar recipe : HSAltarRecipes.getSoulAltarRecipes())
            {
                if (input.matches(InputHelper.toIItemStack(recipe.getInput())))
                {
                    recipes.add(recipe);
                }
            }
            super.apply();
        }

        @Override
        public String getRecipeInfo(HSRecipeSoulAltar recipe)
        {
            return LogHelper.getStackDescription(recipe.getInput());
        }
    }
}
