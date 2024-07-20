package mod.emt.harkenscythe.block;

import javax.annotation.Nullable;
import mod.emt.harkenscythe.init.HSSoundTypes;
import mod.emt.harkenscythe.tileentity.HSTileEntityLivingmetalCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class HSBlockLivingmetalCore extends Block
{
    public static final PropertyInteger STATE = PropertyInteger.create("state", 0, 2);

    public HSBlockLivingmetalCore()
    {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState().withProperty(STATE, 0));
        setSoundType(HSSoundTypes.LIVINGMETAL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(STATE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(STATE);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        if (world.isBlockPowered(pos))
        {
            if (state.getValue(STATE) == 0)
            {
                world.setBlockState(pos, state.withProperty(STATE, 1));
            }
        }
        else
        {
            world.setBlockState(pos, state.withProperty(STATE, 0));
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (placer instanceof EntityPlayer)
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof HSTileEntityLivingmetalCore)
            {
                ((HSTileEntityLivingmetalCore) tileEntity).setOwnerUUID(placer.getUniqueID());
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos)
    {
        return blockState.getValue(STATE);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, STATE);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new HSTileEntityLivingmetalCore();
    }
}
