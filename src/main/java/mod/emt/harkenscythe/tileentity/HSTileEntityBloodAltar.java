package mod.emt.harkenscythe.tileentity;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;

public class HSTileEntityBloodAltar extends HSTileEntityAltar implements ITickable
{
    private static final ItemStack ESSENCE_STACK = new ItemStack(HSItems.blood_essence);

    @Override
    public ItemStack getEssenceStack()
    {
        return ESSENCE_STACK;
    }

    @Override
    public boolean getValidRecipe()
    {
        if (HSAltarRecipes.getOutputBlood(getInputStack().getItem()).isItemEqualIgnoreDurability(getInputStack()) && !getInputStack().isItemDamaged())
        {
            return false;
        }
        return HSAltarRecipes.isValidInputBlood(getInputStack()) && HSAltarRecipes.getRequiredBlood(getInputStack()) <= getEssenceCount();
    }

    @Override
    public Block getCrucibleType()
    {
        return HSBlocks.blood_crucible;
    }
}
