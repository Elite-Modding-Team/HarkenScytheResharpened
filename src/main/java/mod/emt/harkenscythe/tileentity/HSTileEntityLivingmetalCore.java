package mod.emt.harkenscythe.tileentity;

import java.util.List;
import java.util.UUID;
import mod.emt.harkenscythe.block.HSBlockLivingmetalCore;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSTileEntityLivingmetalCore extends HSTileEntity implements ITickable
{
    private UUID ownerUUID;
    private int healTimer;
    private boolean hasAllies;

    public UUID getOwnerUUID()
    {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID)
    {
        this.ownerUUID = ownerUUID;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("ownerUUID"))
        {
            ownerUUID = UUID.fromString(compound.getString("ownerUUID"));
        }
        if (compound.hasKey("healTimer"))
        {
            healTimer = compound.getInteger("healTimer");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if (ownerUUID != null)
        {
            compound.setString("ownerUUID", ownerUUID.toString());
        }
        compound.setInteger("healTimer", healTimer);
        return compound;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void update()
    {
        if (world.isRemote || !world.isBlockPowered(pos)) return;
        IBlockState state = world.getBlockState(pos);
        if (state.getValue(HSBlockLivingmetalCore.STATE) == 1)
        {
            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(15));
            if (!entities.isEmpty())
            {
                for (EntityLivingBase entity : entities)
                {
                    if (isAlly(entity))
                    {
                        entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0));
                        this.hasAllies = true;
                    }
                }
                if (this.hasAllies)
                {
                    world.setBlockState(pos, state.withProperty(HSBlockLivingmetalCore.STATE, 2), 3);
                    this.healTimer = 90;
                }
            }
        }
        if (this.healTimer > 0)
        {
            this.healTimer--;
            if (this.healTimer == 0)
            {
                world.setBlockState(pos, state.withProperty(HSBlockLivingmetalCore.STATE, 1), 3);
                this.hasAllies = false;
            }
        }
    }

    private boolean isAlly(EntityLivingBase entity)
    {
        if (getOwnerUUID() != null)
        {
            EntityPlayer owner = entity.world.getPlayerEntityByUUID(getOwnerUUID());
            if (owner != null)
            {
                return (entity.equals(owner) || entity.isOnSameTeam(owner) || (entity instanceof EntityTameable && ((EntityTameable) entity).isOwner(owner)));
            }
        }
        return false;
    }
}
