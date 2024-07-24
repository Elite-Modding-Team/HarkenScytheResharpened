package mod.emt.harkenscythe.compat.jei.blood;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSBlocks;

public class HSJEIRecipeCategoryBloodAltar implements IRecipeCategory<HSJEIRecipeWrapperBloodAltar>
{
    public static final String UID = "mod.emt.harkenscythe.compat.jei.blood";
    private final IDrawable background;
    private final IDrawable icon;

    public HSJEIRecipeCategoryBloodAltar(IGuiHelper guiHelper)
    {
        this.background = guiHelper.createDrawable(new ResourceLocation(HarkenScythe.MOD_ID, "textures/gui/blood_altar.png"), 3, 4, 155, 65);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(HSBlocks.blood_altar));
    }

    @Override
    public String getUid()
    {
        return UID;
    }

    @Override
    public String getTitle()
    {
        return HSBlocks.blood_altar.getLocalizedName();
    }

    @Override
    public String getModName()
    {
        return HarkenScythe.NAME;
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Nullable
    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, HSJEIRecipeWrapperBloodAltar recipeWrapper, IIngredients ingredients)
    {
        recipeLayout.getItemStacks().init(0, true, 31, 0);
        recipeLayout.getItemStacks().init(1, false, 125, 30);
        recipeLayout.getItemStacks().set(ingredients);
    }
}
