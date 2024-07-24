package mod.emt.harkenscythe.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;

@SuppressWarnings("deprecation")
public class HSBlockSpectralGlass extends Block
{
    private final boolean inverted;
    private boolean daytime;

    public HSBlockSpectralGlass(boolean inverted)
    {
        super(Material.GLASS, Material.GLASS.getMaterialMapColor());
        this.setHardness(3.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setResistance(15.0F);
        this.setSoundType(SoundType.GLASS);
        this.setTickRandomly(true);
        this.inverted = inverted;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess blockAccess, BlockPos pos)
    {
        return !isSolid();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (blockState != iblockstate)
        {
            return true;
        }

        if (block == this)
        {
            return false;
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
    {
        if (isSolid())
        {
            super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, isActualState);
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess blockAccess, BlockPos pos)
    {
        return isSolid() ? super.getCollisionBoundingBox(state, blockAccess, pos) : Block.NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        this.daytime = world.isDaytime();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (player.getHeldItem(hand).getItem() == HSItems.harken_athame)
        {
            world.setBlockState(pos, this.inverted ? HSBlocks.spectral_glass.getDefaultState() : HSBlocks.spectral_glass_inverted.getDefaultState());
            world.playSound(player, pos, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 0.5F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            world.playSound(player, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.5F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            return true;
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    public boolean isSolid()
    {
        if (this.inverted) return this.daytime;
        else return !this.daytime;
    }
}
