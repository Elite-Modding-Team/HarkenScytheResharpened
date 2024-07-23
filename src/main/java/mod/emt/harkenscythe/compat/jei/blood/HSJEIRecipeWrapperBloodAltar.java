package mod.emt.harkenscythe.compat.jei.blood;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.recipe.BloodAltarRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class HSJEIRecipeWrapperBloodAltar implements IRecipeWrapper
{
    private final BloodAltarRecipe recipe;

    public HSJEIRecipeWrapperBloodAltar(BloodAltarRecipe recipe)
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
        ItemStack essence = new ItemStack(HSItems.blood_essence);
        minecraft.getRenderItem().renderItemAndEffectIntoGUI(essence, 32, 28);

        ItemStack athame = new ItemStack(HSItems.harken_athame);
        minecraft.getRenderItem().renderItemAndEffectIntoGUI(athame, 64, 30);

        String bloodCosts = "x" + recipe.getRequiredBlood();
        int stringWidth = minecraft.fontRenderer.getStringWidth(bloodCosts);
        int x = 40 - stringWidth / 2;
        int y = 44;
        minecraft.fontRenderer.drawString(bloodCosts, x, y, 0xFF5555, true);
    }
}
