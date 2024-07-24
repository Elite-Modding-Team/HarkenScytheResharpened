package mod.emt.harkenscythe.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.emt.harkenscythe.block.HSBlockCrucible;

public class HSTileEntityCrucible extends HSTileEntity
{
    public static final int MAX_ESSENCE_COUNT = 100;
    private int essenceCount = 0;

    public int getEssenceCount()
    {
        return essenceCount;
    }

    public void setEssenceCount(World world, BlockPos pos, IBlockState state, int essenceCount)
    {
        this.essenceCount = Math.min(essenceCount, MAX_ESSENCE_COUNT);
        int level = (int) Math.ceil((double) getEssenceCount() / MAX_ESSENCE_COUNT * HSBlockCrucible.MAX_LEVEL);
        ((HSBlockCrucible) state.getBlock()).setLevel(world, pos, state, level);
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.essenceCount = compound.getInteger("EssenceCount");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("EssenceCount", this.essenceCount);
        return compound;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
}
