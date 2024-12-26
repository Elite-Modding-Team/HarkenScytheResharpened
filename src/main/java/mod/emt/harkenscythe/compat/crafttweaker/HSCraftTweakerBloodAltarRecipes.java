package mod.emt.harkenscythe.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mod.emt.harkenscythe.init.HSAltarRecipes;
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
        CraftTweakerAPI.apply(new AddAction(input, output, requiredBlood));
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output)
    {
        CraftTweakerAPI.apply(new RemoveByOutputAction(output));
    }

    @ZenMethod
    public static void removeByInput(IItemStack output)
    {
        CraftTweakerAPI.apply(new RemoveByInputAction(output));
    }

    private static class AddAction implements IAction
    {
        private final IItemStack input;
        private final IItemStack output;
        private final int requiredBlood;

        public AddAction(IItemStack input, IItemStack output, int requiredBlood)
        {
            this.input = input;
            this.output = output;
            this.requiredBlood = requiredBlood;
        }

        @Override
        public void apply()
        {
            HSAltarRecipes.addBloodRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output), requiredBlood);
        }

        @Override
        public String describe()
        {
            return String.format("Adding Blood Altar recipe: %s -> %s with %d blood", input.toString(), output.toString(), requiredBlood);
        }
    }

    private static class RemoveByOutputAction implements IAction
    {
        private final IItemStack output;

        public RemoveByOutputAction(IItemStack output)
        {
            this.output = output;
        }

        @Override
        public void apply()
        {
            HSAltarRecipes.removeBloodRecipeByOutput(CraftTweakerMC.getItemStack(output));
        }

        @Override
        public String describe()
        {
            return String.format("Removing all Blood Altar recipes with output %s", output.toString());
        }
    }

    private static class RemoveByInputAction implements IAction
    {
        private final IItemStack input;

        public RemoveByInputAction(IItemStack input)
        {
            this.input = input;
        }

        @Override
        public void apply()
        {
            HSAltarRecipes.removeBloodRecipeByInput(CraftTweakerMC.getItemStack(input));
        }

        @Override
        public String describe()
        {
            return String.format("Removing all Blood Altar recipes with input %s", input.toString());
        }
    }
}
