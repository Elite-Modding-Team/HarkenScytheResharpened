package mod.emt.harkenscythe.tileentity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class HSTileEntityAltar extends HSTileEntity implements ITickable
{
    private static final int RADIUS = 4;
    public int tickCount;
    public float pageFlip;
    public float pageFlipPrev;
    public float flipT;
    public float flipA;
    public float bookSpread;
    public float bookSpreadPrev;
    public float bookRotation;
    public float bookRotationPrev;
    public float tRot;
    protected ItemStack inputStack = ItemStack.EMPTY;
    protected int essenceCount;

    public ItemStack getInputStack()
    {
        return inputStack;
    }

    public void setInputStack(ItemStack inputStack)
    {
        this.inputStack = inputStack;
        markDirty();
    }

    public abstract ItemStack getEssenceStack();

    public int getEssenceCount()
    {
        return essenceCount;
    }

    public void setEssenceCount(int essenceCount)
    {
        this.essenceCount = essenceCount;
    }

    public abstract boolean getValidRecipe();

    public abstract Block getCrucibleType();

    @Override
    public void update()
    {
        this.bookSpreadPrev = this.bookSpread;
        this.bookRotationPrev = this.bookRotation;
        EntityPlayer entityplayer = this.world.getClosestPlayer((float) this.pos.getX() + 0.5F, (float) this.pos.getY() + 0.5F, (float) this.pos.getZ() + 0.5F, 3.0D, false);

        if (entityplayer != null)
        {
            double d0 = entityplayer.posX - (double) ((float) this.pos.getX() + 0.5F);
            double d1 = entityplayer.posZ - (double) ((float) this.pos.getZ() + 0.5F);
            this.tRot = (float) MathHelper.atan2(d1, d0);
            this.bookSpread += 0.1F;

            if (this.bookSpread < 0.5F || world.rand.nextInt(40) == 0)
            {
                float f1 = this.flipT;

                do
                {
                    this.flipT += (world.rand.nextInt(4) - world.rand.nextInt(4));

                } while (f1 == this.flipT);
            }
        }
        else
        {
            this.tRot += 0.02F;
            this.bookSpread -= 0.1F;
        }

        while (this.bookRotation >= (float) Math.PI)
        {
            this.bookRotation -= ((float) Math.PI * 2F);
        }

        while (this.bookRotation < -(float) Math.PI)
        {
            this.bookRotation += ((float) Math.PI * 2F);
        }

        while (this.tRot >= (float) Math.PI)
        {
            this.tRot -= ((float) Math.PI * 2F);
        }

        while (this.tRot < -(float) Math.PI)
        {
            this.tRot += ((float) Math.PI * 2F);
        }

        float f2;

        for (f2 = this.tRot - this.bookRotation; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F))
        {
        }

        while (f2 < -(float) Math.PI)
        {
            f2 += ((float) Math.PI * 2F);
        }

        this.bookRotation += f2 * 0.4F;
        this.bookSpread = MathHelper.clamp(this.bookSpread, 0.0F, 1.0F);
        ++this.tickCount;
        this.pageFlipPrev = this.pageFlip;
        float f = (this.flipT - this.pageFlip) * 0.4F;
        f = MathHelper.clamp(f, -0.2F, 0.2F);
        this.flipA += (f - this.flipA) * 0.9F;
        this.pageFlip += this.flipA;

        if (!getInputStack().isEmpty())
        {
            setEssenceCount(scanCrucibleEssenceCounts());
            getValidRecipe();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("Item"))
        {
            setInputStack(new ItemStack(compound.getCompoundTag("Item")));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if (!getInputStack().isEmpty())
        {
            NBTTagCompound itemTag = new NBTTagCompound();
            getInputStack().writeToNBT(itemTag);
            compound.setTag("Item", itemTag);
        }
        return compound;
    }

    public void dropItem()
    {
        if (!world.isRemote && !getInputStack().isEmpty())
        {
            BlockPos pos = getPos();
            EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), getInputStack());
            world.spawnEntity(entityItem);
            setInputStack(ItemStack.EMPTY);
        }
    }

    public int scanCrucibleEssenceCounts()
    {
        int totalCount = 0;
        BlockPos pos = this.getPos();
        World world = this.getWorld();

        for (int dx = -RADIUS; dx <= RADIUS; dx++)
        {
            for (int dy = -RADIUS; dy <= RADIUS; dy++)
            {
                for (int dz = -RADIUS; dz <= RADIUS; dz++)
                {
                    BlockPos checkPos = pos.add(dx, dy, dz);
                    if (world.getBlockState(checkPos).getBlock() == getCrucibleType())
                    {
                        TileEntity te = world.getTileEntity(checkPos);
                        if (te instanceof HSTileEntityCrucible)
                        {
                            int count = ((HSTileEntityCrucible) te).getEssenceCount();
                            totalCount += count;
                        }
                    }
                }
            }
        }
        return totalCount;
    }

    public void decreaseCrucibleEssenceCount(int countToDecrease)
    {
        World world = this.getWorld();
        BlockPos pos = this.getPos();
        List<BlockPos> cruciblePositions = new ArrayList<>();

        for (int dx = -RADIUS; dx <= RADIUS; dx++)
        {
            for (int dy = -RADIUS; dy <= RADIUS; dy++)
            {
                for (int dz = -RADIUS; dz <= RADIUS; dz++)
                {
                    BlockPos checkPos = pos.add(dx, dy, dz);
                    if (world.getBlockState(checkPos).getBlock() == getCrucibleType())
                    {
                        TileEntity te = world.getTileEntity(checkPos);
                        if (te instanceof HSTileEntityCrucible)
                        {
                            cruciblePositions.add(checkPos);
                        }
                    }
                }
            }
        }

        int remainingCountToDecrease = countToDecrease;

        while (remainingCountToDecrease > 0 && !cruciblePositions.isEmpty())
        {
            BlockPos selectedPos = cruciblePositions.get(world.rand.nextInt(cruciblePositions.size()));
            IBlockState state = world.getBlockState(selectedPos);
            if (world.getBlockState(selectedPos).getBlock() == getCrucibleType())
            {
                TileEntity te = world.getTileEntity(selectedPos);
                if (te instanceof HSTileEntityCrucible)
                {
                    int currentCount = ((HSTileEntityCrucible) te).getEssenceCount();
                    if (currentCount >= remainingCountToDecrease)
                    {
                        ((HSTileEntityCrucible) te).setEssenceCount(world, selectedPos, state, currentCount - remainingCountToDecrease);
                        remainingCountToDecrease = 0;
                    }
                    else
                    {
                        ((HSTileEntityCrucible) te).setEssenceCount(world, selectedPos, state, 0);
                        remainingCountToDecrease -= currentCount;
                    }
                }
            }
            cruciblePositions.remove(selectedPos);
        }
    }
}
