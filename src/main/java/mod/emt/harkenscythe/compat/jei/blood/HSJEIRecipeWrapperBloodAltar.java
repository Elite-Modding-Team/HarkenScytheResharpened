package mod.emt.harkenscythe.compat.jei.blood;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.recipe.HSRecipeBloodAltar;

public class HSJEIRecipeWrapperBloodAltar implements IRecipeWrapper
{
    private static final ResourceLocation REPAIR_ICON = new ResourceLocation(HarkenScythe.MOD_ID, "textures/gui/repair.png");
    private final HSRecipeBloodAltar recipe;

    public HSJEIRecipeWrapperBloodAltar(HSRecipeBloodAltar recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(ItemStack.class, getInputStack());
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

        if (isRepairRecipe())
        {
            GlStateManager.enableBlend();
            minecraft.getTextureManager().bindTexture(REPAIR_ICON);
            Gui.drawModalRectWithCustomSizedTexture(62, 14, 0, 0, 16, 16, 16, 16);
            GlStateManager.disableBlend();
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY)
    {
        List<String> tooltips = new ArrayList<>();
        if (mouseX >= 32 && mouseX < 48 && mouseY >= 28 && mouseY < 44)
        {
            tooltips.add(I18n.format("item.harkenscythe.blood_essence.name"));
        }
        if (mouseX >= 64 && mouseX < 80 && mouseY >= 30 && mouseY < 46)
        {
            tooltips.add(I18n.format("item.harkenscythe.harken_athame.name"));
        }
        if (isRepairRecipe() && mouseX >= 62 && mouseX < 78 && mouseY >= 14 && mouseY < 30)
        {
            tooltips.add(I18n.format("tooltip.harkenscythe.repair"));
        }
        return tooltips;
    }

    private boolean isRepairRecipe()
    {
        return recipe.getInput().getItem() == recipe.getOutput().getItem();
    }

    private ItemStack getInputStack()
    {
        ItemStack inputStack = recipe.getInput().copy();
        if (isRepairRecipe())
        {
            inputStack.setItemDamage(recipe.getRequiredBlood());
        }
        return inputStack;
    }
}
