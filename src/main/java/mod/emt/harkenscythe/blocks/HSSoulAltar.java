package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.tileentities.HSSoulAltarTE;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSSoulAltar extends BlockEnchantmentTable
{
    public HSSoulAltar()
    {
        super();
        setCreativeTab(HarkenScythe.TAB);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HSSoulAltarTE)
        {
            HSSoulAltarTE altar = (HSSoulAltarTE) te;
            altar.dropItem();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new HSSoulAltarTE();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HSSoulAltarTE)
        {
            HSSoulAltarTE altar = (HSSoulAltarTE) te;
            ItemStack heldItem = player.getHeldItem(hand);

            if (!heldItem.isEmpty())
            {
                ItemStack altarItem = altar.getItem();
                if (altarItem.isEmpty())
                {
                    altar.setItem(heldItem.splitStack(1));
                    return true;
                }
                else if (altarItem.getMaxStackSize() > altarItem.getCount() && ItemStack.areItemsEqual(altarItem, heldItem) && ItemStack.areItemStackTagsEqual(altarItem, heldItem))
                {
                    heldItem.shrink(1);
                    altarItem.grow(1);
                    return true;
                }
            }
            else
            {
                ItemStack itemStack = altar.getItem();
                if (!itemStack.isEmpty())
                {
                    player.addItemStackToInventory(itemStack);
                    altar.setItem(ItemStack.EMPTY);
                    return true;
                }
            }
        }
        return true;
    }
}
