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
@ZenClass("mods.harkenscythe.Altar")
public class HSCraftTweakerPlugin
{
    @ZenMethod
    public static void addBloodAltarRecipe(IItemStack input, IItemStack output, int requiredBlood)
    {
        CraftTweakerAPI.apply(new AddBloodAltarRecipeAction(input, output, requiredBlood));
    }

    @ZenMethod
    public static void removeBloodAltarRecipe(IItemStack input, IItemStack output, int requiredBlood)
    {
        CraftTweakerAPI.apply(new RemoveBloodAltarRecipeAction(input, output, requiredBlood));
    }

    @ZenMethod
    public static void addSoulAltarRecipe(IItemStack input, IItemStack output, int requiredSouls)
    {
        CraftTweakerAPI.apply(new AddSoulAltarRecipeAction(input, output, requiredSouls));
    }

    @ZenMethod
    public static void removeSoulAltarRecipe(IItemStack input, IItemStack output, int requiredSouls)
    {
        CraftTweakerAPI.apply(new RemoveSoulAltarRecipeAction(input, output, requiredSouls));
    }

    private static class AddBloodAltarRecipeAction implements IAction
    {
        private final IItemStack input;
        private final IItemStack output;
        private final int requiredBlood;

        public AddBloodAltarRecipeAction(IItemStack input, IItemStack output, int requiredBlood)
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

    private static class RemoveBloodAltarRecipeAction implements IAction
    {
        private final IItemStack input;
        private final IItemStack output;
        private final int requiredBlood;

        public RemoveBloodAltarRecipeAction(IItemStack input, IItemStack output, int requiredBlood)
        {
            this.input = input;
            this.output = output;
            this.requiredBlood = requiredBlood;
        }

        @Override
        public void apply()
        {
            HSAltarRecipes.removeBloodRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output), requiredBlood);
        }

        @Override
        public String describe()
        {
            return String.format("Removing Blood Altar recipe: %s -> %s with %d blood", input.toString(), output.toString(), requiredBlood);
        }
    }

    private static class AddSoulAltarRecipeAction implements IAction
    {
        private final IItemStack input;
        private final IItemStack output;
        private final int requiredSouls;

        public AddSoulAltarRecipeAction(IItemStack input, IItemStack output, int requiredSouls)
        {
            this.input = input;
            this.output = output;
            this.requiredSouls = requiredSouls;
        }

        @Override
        public void apply()
        {
            HSAltarRecipes.addSoulRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output), requiredSouls);
        }

        @Override
        public String describe()
        {
            return String.format("Adding Soul Altar recipe: %s -> %s with %d souls", input.toString(), output.toString(), requiredSouls);
        }
    }

    private static class RemoveSoulAltarRecipeAction implements IAction
    {
        private final IItemStack input;
        private final IItemStack output;
        private final int requiredSouls;

        public RemoveSoulAltarRecipeAction(IItemStack input, IItemStack output, int requiredSouls)
        {
            this.input = input;
            this.output = output;
            this.requiredSouls = requiredSouls;
        }

        @Override
        public void apply()
        {
            HSAltarRecipes.removeSoulRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output), requiredSouls);
        }

        @Override
        public String describe()
        {
            return String.format("Removing Soul Altar recipe: %s -> %s with %d souls", input.toString(), output.toString(), requiredSouls);
        }
    }
}
