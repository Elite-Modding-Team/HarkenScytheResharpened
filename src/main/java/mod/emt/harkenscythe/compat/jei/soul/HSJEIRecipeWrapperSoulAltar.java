package mod.emt.harkenscythe.compat.jei.soul;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.recipe.SoulAltarRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class HSJEIRecipeWrapperSoulAltar implements IRecipeWrapper
{
    private final SoulAltarRecipe recipe;

    public HSJEIRecipeWrapperSoulAltar(SoulAltarRecipe recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(ItemStack.class, recipe.getInput());
        ingredients.setOutput(ItemStack.class, recipe.getOutput());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        ItemStack essence = new ItemStack(HSItems.soul_essence);
        minecraft.getRenderItem().renderItemAndEffectIntoGUI(essence, 32, 28);

        ItemStack athame = new ItemStack(HSItems.harken_athame);
        minecraft.getRenderItem().renderItemAndEffectIntoGUI(athame, 64, 30);

        String soulCosts = "x" + recipe.getRequiredSouls();
        int stringWidth = minecraft.fontRenderer.getStringWidth(soulCosts);
        int x = 40 - stringWidth / 2;
        int y = 44;
        minecraft.fontRenderer.drawString(soulCosts, x, y, 0x55FFFF, true);
    }
}
