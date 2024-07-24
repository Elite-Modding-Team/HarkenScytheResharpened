package mod.emt.harkenscythe.tileentity;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;

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
        if (HSAltarRecipes.getOutputSoul(getInputStack().getItem()).isItemEqualIgnoreDurability(getInputStack()) && !getInputStack().isItemDamaged())
        {
            return false;
        }
        return HSAltarRecipes.isValidInputSoul(getInputStack()) && HSAltarRecipes.getRequiredSouls(getInputStack()) <= getEssenceCount();
    }

    @Override
    public Block getCrucibleType()
    {
        return HSBlocks.soul_crucible;
    }
}
