package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.items.HSEssenceKeeper;
import mod.emt.harkenscythe.tileentities.HSTileEntityCrucible;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSBloodCrucible extends HSCrucible
{
    public HSBloodCrucible()
    {
        super(MapColor.RED);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldStack = player.getHeldItem(hand);
        if (heldStack.isEmpty()) return false;

        Item heldItem = heldStack.getItem();
        if (!(heldItem instanceof HSEssenceKeeper)) return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);

        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof HSTileEntityCrucible)) return false;
        int crucibleBloodCount = ((HSTileEntityCrucible) te).getEssenceCount();
        if (crucibleBloodCount < HSTileEntityCrucible.MAX_ESSENCE_COUNT && !player.isSneaking() && (heldItem == HSItems.essence_keeper_blood || heldItem == HSItems.essence_vessel_blood))
        {
            fillCrucible(world, pos, state, player, hand, heldStack, heldItem, crucibleBloodCount, HSItems.essence_keeper_blood, HSItems.essence_vessel_blood);
        }
        else if (crucibleBloodCount > 0 && player.isSneaking())
        {
            emptyCrucible(world, pos, state, player, hand, heldStack, heldItem, crucibleBloodCount, HSItems.essence_keeper_blood, HSItems.essence_vessel_blood);
        }

        return true;
    }
}
