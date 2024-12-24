package mod.emt.harkenscythe.compat.jei;

import net.minecraft.item.ItemStack;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mod.emt.harkenscythe.compat.jei.blood.HSJEIRecipeCategoryBloodAltar;
import mod.emt.harkenscythe.compat.jei.blood.HSJEIRecipeWrapperBloodAltar;
import mod.emt.harkenscythe.compat.jei.soul.HSJEIRecipeCategorySoulAltar;
import mod.emt.harkenscythe.compat.jei.soul.HSJEIRecipeWrapperSoulAltar;
import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.recipe.HSRecipeBloodAltar;
import mod.emt.harkenscythe.recipe.HSRecipeSoulAltar;

@JEIPlugin
public class HSJEIPlugin implements IModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        // Blood Altar
        registry.addRecipeCategories(new HSJEIRecipeCategoryBloodAltar(registry.getJeiHelpers().getGuiHelper()));
        registry.handleRecipes(HSRecipeBloodAltar.class, HSJEIRecipeWrapperBloodAltar::new, HSJEIRecipeCategoryBloodAltar.UID);
        registry.addRecipes(HSAltarRecipes.getBloodAltarRecipes(), HSJEIRecipeCategoryBloodAltar.UID);
        registry.addRecipeCatalyst(new ItemStack(HSBlocks.blood_altar), HSJEIRecipeCategoryBloodAltar.UID);

        // Soul Altar
        registry.addRecipeCategories(new HSJEIRecipeCategorySoulAltar(registry.getJeiHelpers().getGuiHelper()));
        registry.handleRecipes(HSRecipeSoulAltar.class, HSJEIRecipeWrapperSoulAltar::new, HSJEIRecipeCategorySoulAltar.UID);
        registry.addRecipes(HSAltarRecipes.getSoulAltarRecipes(), HSJEIRecipeCategorySoulAltar.UID);
        registry.addRecipeCatalyst(new ItemStack(HSBlocks.soul_altar), HSJEIRecipeCategorySoulAltar.UID);
    }
}
