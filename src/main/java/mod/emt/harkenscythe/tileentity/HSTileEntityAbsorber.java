package mod.emt.harkenscythe.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.emt.harkenscythe.block.HSBlockAbsorber;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItemEssenceVesselBlood;
import mod.emt.harkenscythe.item.HSItemEssenceVesselSoul;

public abstract class HSTileEntityAbsorber extends HSTileEntity implements ITickable
{
    protected ItemStack inputStack = ItemStack.EMPTY;
    protected int essenceCount;
    protected boolean active;

    public ItemStack getInputStack()
    {
        return inputStack;
    }

    public void setInputStack(ItemStack inputStack)
    {
        this.inputStack = inputStack;
        markDirty();
    }

    public int getEssenceCount()
    {
        return essenceCount;
    }

    public void setEssenceCount(int essenceCount)
    {
        this.essenceCount = essenceCount;
    }

    public abstract Block getCrucibleType();

    public abstract int scanContainerEssenceCounts(ItemStack container);

    @Override
    public void update()
    {
        setEssenceCount(scanContainerEssenceCounts(getInputStack()));
        if (getEssenceCount() > 0)
        {
            if (getWorld().getTotalWorldTime() % 5 == 0)
            {
                increaseCrucibleEssenceCount();
            }
            if (!active)
            {
                IBlockState state = world.getBlockState(pos);
                world.setBlockState(pos, state.withProperty(HSBlockAbsorber.STATE, 1), 3);
                active = true;
            }
        }
        else if (active)
        {
            IBlockState state = world.getBlockState(pos);
            world.setBlockState(pos, state.withProperty(HSBlockAbsorber.STATE, 0), 3);
            active = false;
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

    @Override
    public void invalidate()
    {
        super.invalidate();
        dropItem();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
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

    public void increaseCrucibleEssenceCount()
    {
        World world = this.getWorld();
        BlockPos pos = this.getPos();
        List<BlockPos> cruciblePositions = new ArrayList<>();

        for (BlockPos checkPos : HSTileEntityCrucible.CRUCIBLE_POSITIONS)
        {
            if (Math.sqrt(pos.distanceSq(checkPos)) <= HSConfig.BLOCKS.crucibleDetectionRange && world.getBlockState(checkPos).getBlock() == getCrucibleType())
            {
                TileEntity te = world.getTileEntity(checkPos);
                if (te instanceof HSTileEntityCrucible && ((HSTileEntityCrucible) te).getEssenceCount() < HSConfig.BLOCKS.crucibleMaxAmount)
                {
                    cruciblePositions.add(checkPos);
                }
            }
        }

        if (!cruciblePositions.isEmpty())
        {
            BlockPos selectedPos = cruciblePositions.get(0);
            IBlockState state = world.getBlockState(selectedPos);
            if (world.getBlockState(selectedPos).getBlock() == getCrucibleType())
            {
                TileEntity te = world.getTileEntity(selectedPos);
                if (te instanceof HSTileEntityCrucible)
                {
                    int currentCount = ((HSTileEntityCrucible) te).getEssenceCount();
                    ((HSTileEntityCrucible) te).setEssenceCount(world, selectedPos, state, currentCount + 1);
                    decreaseContainerEssenceCount();
                    createWorkingParticles();
                    createTrailParticles(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, te.getPos().getX() + 0.5D, te.getPos().getY() + 1.0D, te.getPos().getZ() + 0.5D);
                    world.playSound(null, pos, HSSoundEvents.ESSENCE_SOUL_SUMMON.getSoundEvent(), SoundCategory.BLOCKS, 0.2F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
                }
            }
            cruciblePositions.remove(selectedPos);
        }
    }

    public void decreaseContainerEssenceCount()
    {
        getInputStack().setItemDamage(getInputStack().getItemDamage() + 1);
        if (getInputStack().getItemDamage() >= getInputStack().getMaxDamage())
        {
            setInputStack(new ItemStack(getInputStack().getItem() instanceof HSItemEssenceVesselBlood || getInputStack().getItem() instanceof HSItemEssenceVesselSoul ? HSItems.essence_vessel : HSItems.essence_keeper));
        }
    }

    public void createWorkingParticles()
    {
        for (int i = 0; i < 3; i++)
        {
            world.spawnParticle(EnumParticleTypes.SPELL_WITCH, pos.getX() + world.rand.nextFloat(), pos.getY() + 0.5D + world.rand.nextFloat(), pos.getZ() + world.rand.nextFloat(), 0.0D, 0.5D, 0.0D);
        }
    }

    public void createTrailParticles(double srcX, double srcY, double srcZ, double destX, double destY, double destZ)
    {
        int particles = 60;
        for (int i = 0; i < particles; i++)
        {
            double trailFactor = i / (particles - 1.0D);
            double d = this instanceof HSTileEntityBloodAbsorber ? 0.9F : 0.4F;
            double d1 = this instanceof HSTileEntityBloodAbsorber ? 0.2F : 0.8F;
            double d2 = this instanceof HSTileEntityBloodAbsorber ? 0.2F : 0.9F;
            double tx = srcX + (destX - srcX) * trailFactor;
            double ty = srcY + (destY - srcY) * trailFactor;
            double tz = srcZ + (destZ - srcZ) * trailFactor;
            world.spawnParticle(EnumParticleTypes.REDSTONE, tx, ty, tz, d, d1, d2);
        }
    }
}
