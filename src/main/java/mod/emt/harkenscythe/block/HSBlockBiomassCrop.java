package mod.emt.harkenscythe.block;

import java.util.Random;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundTypes;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

@SuppressWarnings("deprecation")
public class HSBlockBiomassCrop extends BlockBush
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
    private static final AxisAlignedBB[] BIOMASS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.875D, 0.3125D, 0.875D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.875D, 0.5D, 0.875D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.875D, 0.6875D, 0.875D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.875D, 0.875D, 0.875D)};

    public HSBlockBiomassCrop()
    {
        super(Material.PLANTS, MapColor.RED);
        setDefaultState(blockState.getBaseState().withProperty(AGE, 0));
        setSoundType(HSSoundTypes.BIOMASS_PLANT);
        setTickRandomly(true);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AGE);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!world.isRemote)
        {
            spawnAsEntity(world, pos, new ItemStack(state.getValue(AGE) >= 3 ? HSItems.biomass : HSItems.biomass_seed));
        }
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        return new ItemStack(HSItems.biomass);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE);
    }

    @Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == HSBlocks.creep_block_tilled || state.getBlock() == HSBlocks.creep_block_tilled_bloodied;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        int i = state.getValue(AGE);
        if (i < 3)
        {
            boolean bloodied = world.getBlockState(pos.down()).getBlock() == HSBlocks.creep_block_tilled_bloodied;
            int random = bloodied ? 5 : 10;
            if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(random) == 0))
            {
                IBlockState newState = state.withProperty(AGE, i + 1);
                world.setBlockState(pos, newState, 2);
                ForgeHooks.onCropsGrowPost(world, pos, state, newState);
                if (bloodied)
                {
                    world.setBlockState(pos.down(), HSBlocks.creep_block_tilled.getDefaultState());
                }
            }
        }
        super.updateTick(world, pos, state, rand);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BIOMASS_AABB[state.getValue(AGE)];
    }
}
