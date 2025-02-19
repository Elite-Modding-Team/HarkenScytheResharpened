package mod.emt.harkenscythe.block;

import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSAdvancements;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItemEssenceKeeper;
import mod.emt.harkenscythe.tileentity.HSTileEntityAbsorber;

@SuppressWarnings("deprecation")
public abstract class HSBlockAbsorber extends BlockEnchantmentTable
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger STATE = PropertyInteger.create("state", 0, 1);

    protected HSBlockAbsorber()
    {
        super();
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(STATE, 0));
        setHardness(5.0F);
        setResistance(2000.0F);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3)).withProperty(STATE, meta >> 2);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getHorizontalIndex();
        i = i | state.getValue(STATE) << 2;
        return i;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing horizontalFacing = placer.getHorizontalFacing();
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, horizontalFacing).withProperty(STATE, meta >> 2);
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
        return new BlockStateContainer(this, FACING, STATE);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HSTileEntityAbsorber && hand == EnumHand.MAIN_HAND)
        {
            HSTileEntityAbsorber absorber = (HSTileEntityAbsorber) te;
            int absorberX = absorber.getPos().getX();
            int absorberY = absorber.getPos().getY();
            int absorberZ = absorber.getPos().getZ();
            ItemStack absorberStack = absorber.getInputStack();
            ItemStack heldStack = player.getHeldItem(hand);

            if (heldStack.getItem() instanceof HSItemEssenceKeeper && absorberStack.isEmpty())
            {
                absorber.setInputStack(heldStack.splitStack(1));
                world.playSound(absorberX, absorberY, absorberZ, HSSoundEvents.BLOCK_BOTTLE_PLACE.getSoundEvent(), SoundCategory.BLOCKS, 0.8F, 1.0F / (absorber.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                if (player instanceof EntityPlayerMP)
                {
                    HSAdvancements.USE_ABSORBER.trigger((EntityPlayerMP) player);
                    if (this instanceof HSBlockBloodAbsorber) HSAdvancements.USE_BLOOD_ABSORBER.trigger((EntityPlayerMP) player);
                    else HSAdvancements.USE_SOUL_ABSORBER.trigger((EntityPlayerMP) player);
                }
                return true;
            }
            else
            {
                ItemStack inputStack = absorber.getInputStack();
                if (!inputStack.isEmpty())
                {
                    absorber.setInputStack(ItemStack.EMPTY);
                    if (!world.isRemote) player.addItemStackToInventory(inputStack);
                    world.playSound(absorberX, absorberY, absorberZ, HSSoundEvents.BLOCK_BOTTLE_REMOVE.getSoundEvent(), SoundCategory.BLOCKS, 0.8F, 1.0F / (absorber.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    return true;
                }
            }
        }
        return false;
    }
}
