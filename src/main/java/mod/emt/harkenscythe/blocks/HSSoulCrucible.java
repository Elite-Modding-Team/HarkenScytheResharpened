package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.items.HSEssenceKeeper;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSSoulCrucible extends HSCrucible
{
    public HSSoulCrucible()
    {
        super(MapColor.BLACK);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote) return false;

        ItemStack heldStack = player.getHeldItem(hand);
        if (heldStack.isEmpty()) return false;

        Item heldItem = heldStack.getItem();
        if (!(heldItem instanceof HSEssenceKeeper)) return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);

        int crucibleLevel = state.getValue(LEVEL);
        if (crucibleLevel < 10 && !player.isSneaking() && (heldItem == HSItems.essence_keeper_soul || heldItem == HSItems.essence_vessel_soul))
        {
            fillCrucible(world, pos, state, player, hand, heldStack, heldItem, crucibleLevel, HSItems.essence_keeper_soul, HSItems.essence_vessel_soul);
        }
        else if (crucibleLevel > 0 && player.isSneaking())
        {
            emptyCrucible(world, pos, state, player, hand, heldStack, heldItem, crucibleLevel, HSItems.essence_keeper_soul, HSItems.essence_vessel_soul);
        }

        return true;
    }
}
