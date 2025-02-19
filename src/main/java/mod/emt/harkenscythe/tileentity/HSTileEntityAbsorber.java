package mod.emt.harkenscythe.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.block.HSBlockAbsorber;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.item.HSItemEssenceTrinketBlood;
import mod.emt.harkenscythe.item.HSItemEssenceTrinketSoul;
import mod.emt.harkenscythe.item.HSItemEssenceVesselBlood;
import mod.emt.harkenscythe.item.HSItemEssenceVesselSoul;

public abstract class HSTileEntityAbsorber extends HSTileEntity implements ITickable
{
    private final List<BlockPos> cruciblePositions = new ArrayList<>();
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

    public boolean isActive()
    {
        return active;
    }

    public abstract Block getCrucibleType();

    public abstract int scanContainerEssenceCounts(ItemStack container);

    @Override
    public void update()
    {
        setEssenceCount(scanContainerEssenceCounts(getInputStack()));
        if (getEssenceCount() > 0)
        {
            scanCruciblePositions();
            if (!cruciblePositions.isEmpty())
            {
                if (getWorld().getTotalWorldTime() % 5 == 0)
                {
                    increaseCrucibleEssenceCount();
                }
                if (!active)
                {
                    active = true;
                    IBlockState state = world.getBlockState(pos);
                    world.setBlockState(pos, state.withProperty(HSBlockAbsorber.STATE, 1), 3);

                    playStartSound();
                    if (FMLLaunchHandler.side().isClient() && this.world.isRemote)
                    {
                        playActiveSound();
                    }
                }
            }
        }
        else if (active)
        {
            active = false;
            IBlockState state = world.getBlockState(pos);
            world.setBlockState(pos, state.withProperty(HSBlockAbsorber.STATE, 0), 3);
            playStopSound();
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

    public void scanCruciblePositions()
    {
        World world = this.getWorld();
        BlockPos pos = this.getPos();

        cruciblePositions.clear();

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
    }

    public void increaseCrucibleEssenceCount()
    {
        World world = this.getWorld();
        BlockPos pos = this.getPos();

        if (!cruciblePositions.isEmpty())
        {
            BlockPos selectedPos = cruciblePositions.get(0);
            IBlockState state = world.getBlockState(selectedPos);
            TileEntity te = world.getTileEntity(selectedPos);
            if (te instanceof HSTileEntityCrucible)
            {
                int currentCount = ((HSTileEntityCrucible) te).getEssenceCount();
                ((HSTileEntityCrucible) te).setEssenceCount(world, selectedPos, state, currentCount + 1);
                decreaseContainerEssenceCount();
                createWorkingParticles();
                createTrailParticles(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, te.getPos().getX() + 0.5D, te.getPos().getY() + 1.0D, te.getPos().getZ() + 0.5D);
            }
        }
    }

    public void decreaseContainerEssenceCount()
    {
        getInputStack().setItemDamage(getInputStack().getItemDamage() + 1);
        Item item = getInputStack().getItem();
        if (item instanceof HSItemEssenceTrinketBlood || item instanceof HSItemEssenceTrinketSoul)
        {
            return;
        }
        if (getInputStack().getItemDamage() >= getInputStack().getMaxDamage())
        {
            setInputStack(new ItemStack(item instanceof HSItemEssenceVesselBlood || item instanceof HSItemEssenceVesselSoul ? HSItems.essence_vessel : HSItems.essence_keeper));
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

    public abstract void playStartSound();

    @SideOnly(Side.CLIENT)
    public abstract void playActiveSound();

    public abstract void playStopSound();
}
