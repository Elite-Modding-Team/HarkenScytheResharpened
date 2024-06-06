package mod.emt.harkenscythe.blocks;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.items.HSEssenceKeeper;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class HSSoulCrucible extends Block
{
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 10);
    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public HSSoulCrucible()
    {
        super(Material.IRON, MapColor.BLACK);
        setCreativeTab(HarkenScythe.TAB);
        setDefaultState(blockState.getBaseState().withProperty(LEVEL, 0));
    }

    public void setLevel(World world, BlockPos pos, IBlockState state, int level)
    {
        world.setBlockState(pos, state.withProperty(LEVEL, MathHelper.clamp(level, 0, 10)), 2);
        world.updateComparatorOutputLevel(pos, this);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(LEVEL, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(LEVEL);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FULL_BLOCK_AABB;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
    {
        if (face == EnumFacing.UP)
        {
            return BlockFaceShape.BOWL;
        }
        else
        {
            return face == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote) return false;

        ItemStack stack = player.getHeldItem(hand);
        if (stack.isEmpty()) return false;

        Item item = stack.getItem();
        if (!(item instanceof HSEssenceKeeper)) return false;

        int level = state.getValue(LEVEL);
        if (level < 10 && !player.isSneaking() && (item == HSItems.essence_keeper_soul || item == HSItems.essence_vessel_soul))
        {
            fillCrucible(world, pos, state, player, hand, stack, item, level);
        }
        else if (level > 0 && player.isSneaking())
        {
            emptyCrucible(world, pos, state, player, hand, stack, item, level);
        }

        return true;
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this));
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos)
    {
        return blockState.getValue(LEVEL);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LEVEL);
    }

    private void fillCrucible(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, Item item, int level)
    {
        if (!player.capabilities.isCreativeMode)
        {
            if (stack.getItemDamage() + 10 < stack.getMaxDamage())
            {
                stack.setItemDamage(stack.getItemDamage() + 10);
            }
            else
            {
                stack.shrink(1);
                if (item == HSItems.essence_keeper_soul)
                {
                    player.setHeldItem(hand, new ItemStack(HSItems.essence_keeper));
                }
                else if (item == HSItems.essence_vessel_soul)
                {
                    player.setHeldItem(hand, new ItemStack(HSItems.essence_vessel));
                }
            }
        }
        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        setLevel(world, pos, state, level + 1);
        player.addStat(StatList.getObjectUseStats(item));
    }

    private void emptyCrucible(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, Item item, int level)
    {
        if (!player.capabilities.isCreativeMode)
        {
            if (item == HSItems.essence_keeper || item == HSItems.essence_vessel)
            {
                stack.shrink(1);
                ItemStack newStack = item == HSItems.essence_keeper ? new ItemStack(HSItems.essence_keeper_soul) : new ItemStack(HSItems.essence_vessel_soul);
                newStack.setItemDamage(newStack.getMaxDamage() - 10);
                player.setHeldItem(hand, newStack);
            }
            else if (item == HSItems.essence_keeper_soul || item == HSItems.essence_vessel_soul)
            {
                if (stack.getItemDamage() == 0) return;
                if (stack.getItemDamage() > 0)
                {
                    stack.setItemDamage(stack.getItemDamage() - 10);
                }
                if (stack.getItemDamage() <= 0)
                {
                    stack.shrink(1);
                    ItemStack newStack = item == HSItems.essence_keeper_soul ? new ItemStack(HSItems.essence_keeper_soul) : new ItemStack(HSItems.essence_vessel_soul);
                    player.setHeldItem(hand, newStack);
                }
            }
        }
        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        setLevel(world, pos, state, level - 1);
        player.addStat(StatList.getObjectUseStats(item));
    }
}
