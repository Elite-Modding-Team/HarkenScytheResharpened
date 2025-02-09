package mod.emt.harkenscythe.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.block.HSBlockBloodCrucible;
import mod.emt.harkenscythe.block.HSBlockCrucible;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.network.HSNetworkHandler;
import mod.emt.harkenscythe.network.packet.HSEssenceSyncPacket;

public class HSTileEntityCrucible extends HSTileEntity
{
    public static final List<BlockPos> CRUCIBLE_POSITIONS = new ArrayList<>();
    private int essenceCount = 0;

    public int getEssenceCount()
    {
        return this.essenceCount;
    }

    public void setEssenceCount(World world, BlockPos pos, IBlockState state, int essenceCount)
    {
        if (!world.isRemote)
        {
            this.essenceCount = Math.min(essenceCount, HSConfig.BLOCKS.crucibleMaxAmount);
            int level = (int) Math.ceil((double) getEssenceCount() / HSConfig.BLOCKS.crucibleMaxAmount * HSBlockCrucible.MAX_LEVEL);
            ((HSBlockCrucible) state.getBlock()).setLevel(world, pos, state, level);
            HSNetworkHandler.instance.sendToAll(new HSEssenceSyncPacket(this.essenceCount, pos));
        }
        for (int i = 0; i < 3; i++)
        {
            if (state.getBlock() instanceof HSBlockBloodCrucible)
            {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, pos.getX() + 0.5D, pos.getY() + 0.01D * getEssenceCount(), pos.getZ() + 0.5D, 0.9D, 0.2D, 0.2D);
            }
            else
            {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, pos.getX() + 0.5D, pos.getY() + 0.01D * getEssenceCount(), pos.getZ() + 0.5D, 0.4D, 0.8D, 0.9D);
            }
        }
        markDirty();
    }

    @SideOnly(Side.CLIENT)
    public void setEssenceCountClient(int essenceCount)
    {
        this.essenceCount = essenceCount;
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
    public void invalidate()
    {
        super.invalidate();
        CRUCIBLE_POSITIONS.remove(getPos());
    }

    @Override
    public void setPos(BlockPos pos)
    {
        super.setPos(pos);
        if (!CRUCIBLE_POSITIONS.contains(getPos()))
        {
            CRUCIBLE_POSITIONS.add(getPos());
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
}
