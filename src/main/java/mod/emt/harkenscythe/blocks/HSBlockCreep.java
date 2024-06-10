package mod.emt.harkenscythe.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import mod.emt.harkenscythe.init.HSBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: Creep blocks are tillable with glaives and can be filled with blood
// Using creepballs on soul sand will also create creep blocks
public class HSBlockCreep extends Block
{
    protected static final AxisAlignedBB CREEP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public HSBlockCreep()
    {
        super(Material.GRASS, MapColor.RED);
        this.setHardness(0.6F);
        this.setHarvestLevel("shovel", 0);
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
    {
        return CREEP_AABB;
    }

    // TODO: See whether this properly works in the Nether
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote && worldIn.provider.getDimension() == -1)
        {
            if (!worldIn.isAreaLoaded(pos, 3)) return;
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2)
            {
                if (this == HSBlocks.creep_block) worldIn.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
            }
            else
            {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == Blocks.SOUL_SAND && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
                        {
                            worldIn.setBlockState(blockpos, HSBlocks.creep_block.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    // TODO: Fancier particles? Maybe little red particles?
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        if (rand.nextInt(10) == 0)
        {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, (float) pos.getX() + rand.nextFloat(), (float) pos.getY() + 1.1F, (float) pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Blocks.SOUL_SAND.getItemDropped(Blocks.SOUL_SAND.getDefaultState(), rand, fortune);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
        entity.motionX *= 0.4D;
        entity.motionZ *= 0.4D;
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
