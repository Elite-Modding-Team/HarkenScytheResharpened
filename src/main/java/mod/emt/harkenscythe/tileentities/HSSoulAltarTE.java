package mod.emt.harkenscythe.tileentities;

import java.util.Random;
import mod.emt.harkenscythe.blocks.HSSoulCrucible;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class HSSoulAltarTE extends TileEntity implements ITickable
{
    private static final Random rand = new Random();
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
    public int soulCount;
    private ItemStack item = ItemStack.EMPTY;

    public ItemStack getItem()
    {
        return item;
    }

    public void setItem(ItemStack item)
    {
        this.item = item;
        markDirty();
    }

    public void dropItem()
    {
        if (!world.isRemote && !item.isEmpty())
        {
            BlockPos pos = getPos();
            EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), item);
            world.spawnEntity(entityItem);
            setItem(ItemStack.EMPTY);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("Item"))
        {
            item = new ItemStack(compound.getCompoundTag("Item"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if (!item.isEmpty())
        {
            NBTTagCompound itemTag = new NBTTagCompound();
            item.writeToNBT(itemTag);
            compound.setTag("Item", itemTag);
        }
        return compound;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        readFromNBT(tag);
    }

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

            if (this.bookSpread < 0.5F || rand.nextInt(40) == 0)
            {
                float f1 = this.flipT;

                do
                {
                    this.flipT += (rand.nextInt(4) - rand.nextInt(4));

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

        if (this.world.getWorldTime() % 40 == 39)
        {
            soulCount = scanCrucibleLevels() * 10;
        }
    }

    public int scanCrucibleLevels()
    {
        int totalLevel = 0;
        BlockPos pos = this.getPos();
        World world = this.getWorld();

        for (int dx = -RADIUS; dx <= RADIUS; dx++)
        {
            for (int dy = -RADIUS; dy <= RADIUS; dy++)
            {
                for (int dz = -RADIUS; dz <= RADIUS; dz++)
                {
                    BlockPos checkPos = pos.add(dx, dy, dz);
                    IBlockState state = world.getBlockState(checkPos);

                    if (state.getBlock() instanceof HSSoulCrucible)
                    {
                        int level = state.getValue(HSSoulCrucible.LEVEL);
                        totalLevel += level;
                    }
                }
            }
        }

        return totalLevel;
    }
}
