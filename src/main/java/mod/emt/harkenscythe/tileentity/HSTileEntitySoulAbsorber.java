package mod.emt.harkenscythe.tileentity;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.item.HSItemEssenceKeeperSoul;

public class HSTileEntitySoulAbsorber extends HSTileEntityAbsorber implements ITickable
{
    @Override
    public Block getCrucibleType()
    {
        return HSBlocks.soul_crucible;
    }

    @Override
    public int scanContainerEssenceCounts(ItemStack container)
    {
        int totalCount = 0;
        if (container.getItem() instanceof HSItemEssenceKeeperSoul)
        {
            totalCount = container.getMaxDamage() - container.getItemDamage();
        }
        return totalCount;
    }
}
