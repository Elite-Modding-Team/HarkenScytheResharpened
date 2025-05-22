package mod.emt.harkenscythe.recipe;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import net.minecraftforge.registries.IForgeRegistryEntry;

public class HSRecipeRefreshTomeUse extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public HSRecipeRefreshTomeUse()
    {
        super();
        this.setRegistryName(HarkenScythe.MOD_ID, "disenchant");
    }

    public static boolean matches(IInventory inventory)
    {
        boolean foundCloth = false;
        boolean foundTarget = false;

        for (int index = 0; index < inventory.getSizeInventory(); index++)
        {
            ItemStack stack = inventory.getStackInSlot(index);

            if (!stack.isEmpty())
            {
                if (stack.getItem() == HSItems.refresh_tome && !foundCloth)
                {
                    foundCloth = true;
                } else if (stack.getItem() != HSItems.refresh_tome && !foundTarget && stack.isItemEnchanted())
                {
                    foundTarget = true;
                } else
                {
                    // Invalid item, abort
                    return false;
                }
            }
        }

        return foundCloth && foundTarget;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world)
    {
        return HSRecipeRefreshTomeUse.matches(inventory);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory)
    {
        ItemStack stackToDisenchant = ItemStack.EMPTY;

        for (int index = 0; index < inventory.getSizeInventory(); index++)
        {
            ItemStack stack = inventory.getStackInSlot(index);

            if (!stack.isEmpty() && stack.getItem() != HSItems.refresh_tome && stack.isItemEnchanted())
            {
                stackToDisenchant = stack.copy();
                break;
            }
        }

        if (!stackToDisenchant.isEmpty() && stackToDisenchant.hasTagCompound())
        {
            stackToDisenchant.getTagCompound().removeTag("ench");
        }

        return stackToDisenchant;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return (width >= 2) || (height >= 2);
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        // Recipe is dynamic, so return empty stack
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic()
    {
        return true;
    }
}
