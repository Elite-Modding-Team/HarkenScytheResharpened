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
import mod.emt.harkenscythe.recipe.HSRecipeBloodAltar;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenRegister
@ZenClass("mods.harkenscythe.bloodAltar")
public class HSCraftTweakerBloodAltarRecipes
{
    @ZenMethod
    public static void add(IItemStack input, IItemStack output, int requiredBlood)
    {
        HSCraftTweakerPlugin.LATE_ADDITIONS.add(new Add(Collections.singletonList(new HSRecipeBloodAltar(InputHelper.toStack(input), InputHelper.toStack(output), requiredBlood))));
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

    private static class Add extends BaseListAddition<HSRecipeBloodAltar>
    {
        protected Add(List<HSRecipeBloodAltar> recipes)
        {
            super("bloodAltar", HSAltarRecipes.getBloodAltarRecipes(), recipes);
        }

        @Override
        public String getRecipeInfo(HSRecipeBloodAltar recipe)
        {
            return LogHelper.getStackDescription(recipe.getOutput());
        }
    }

    private static class RemoveByOutput extends BaseListRemoval<HSRecipeBloodAltar>
    {
        private final IItemStack output;

        protected RemoveByOutput(IItemStack output)
        {
            super("bloodAltar", HSAltarRecipes.getBloodAltarRecipes());
            this.output = output;
        }

        @Override
        public void apply()
        {
            for (HSRecipeBloodAltar recipe : HSAltarRecipes.getBloodAltarRecipes())
            {
                if (output.matches(InputHelper.toIItemStack(recipe.getOutput())))
                {
                    recipes.add(recipe);
                }
            }
            super.apply();
        }

        @Override
        public String getRecipeInfo(HSRecipeBloodAltar recipe)
        {
            return LogHelper.getStackDescription(recipe.getOutput());
        }
    }

    private static class RemoveByInput extends BaseListRemoval<HSRecipeBloodAltar>
    {
        private final IItemStack input;

        protected RemoveByInput(IItemStack input)
        {
            super("bloodAltar", HSAltarRecipes.getBloodAltarRecipes());
            this.input = input;
        }

        @Override
        public void apply()
        {
            for (HSRecipeBloodAltar recipe : HSAltarRecipes.getBloodAltarRecipes())
            {
                if (input.matches(InputHelper.toIItemStack(recipe.getInput())))
                {
                    recipes.add(recipe);
                }
            }
            super.apply();
        }

        @Override
        public String getRecipeInfo(HSRecipeBloodAltar recipe)
        {
            return LogHelper.getStackDescription(recipe.getInput());
        }
    }
}
