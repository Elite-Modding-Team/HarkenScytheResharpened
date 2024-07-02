package mod.emt.harkenscythe.tileentities;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class HSTileEntitySoulAltar extends HSTileEntityAltar implements ITickable
{
    private static final ItemStack ESSENCE_STACK = new ItemStack(HSItems.soul_essence);

    @Override
    public ItemStack getEssenceStack()
    {
        return ESSENCE_STACK;
    }

    @Override
    public boolean getValidRecipe()
    {
        return HSAltarRecipes.isValidInputSoul(getInputStack().getItem()) && HSAltarRecipes.getRequiredSouls(getInputStack().getItem()) <= getEssenceCount();
    }

    @Override
    public Block getCrucibleType()
    {
        return HSBlocks.soul_crucible;
    }
}
