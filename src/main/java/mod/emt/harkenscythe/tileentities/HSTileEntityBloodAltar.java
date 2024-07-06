package mod.emt.harkenscythe.tileentities;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

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
        return HSAltarRecipes.isValidInputBlood(getInputStack()) && HSAltarRecipes.getRequiredBlood(getInputStack()) <= getEssenceCount();
    }

    @Override
    public Block getCrucibleType()
    {
        return HSBlocks.blood_crucible;
    }
}
