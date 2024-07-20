package mod.emt.harkenscythe.entity;

import io.netty.buffer.ByteBuf;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItemSpectralPotion;
import mod.emt.harkenscythe.potion.HSPotionFlame;
import mod.emt.harkenscythe.potion.HSPotionWater;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class HSEntitySpectralPotion extends EntityThrowable implements IEntityAdditionalSpawnData
{
    private PotionEffect potionEffect;
    private ItemStack potionStack = new ItemStack(HSItems.spectral_glass_bottle);

    public HSEntitySpectralPotion(World world)
    {
        super(world);
    }

    public HSEntitySpectralPotion(World world, EntityLivingBase thrower, ItemStack potionStack)
    {
        super(world, thrower);
        if (potionStack.getItem() instanceof HSItemSpectralPotion)
        {
            setPotionEffect(((HSItemSpectralPotion) potionStack.getItem()).getPotionEffect());
            setPotionStack(potionStack);
        }
    }

    public PotionEffect getPotionEffect()
    {
        return this.potionEffect;
    }

    public void setPotionEffect(PotionEffect potionEffect)
    {
        this.potionEffect = potionEffect;
    }

    public ItemStack getPotionStack()
    {
        return this.potionStack;
    }

    public void setPotionStack(ItemStack potionStack)
    {
        this.potionStack = potionStack;
    }

    // Fixes buggy projectile behavior on the client
    @Override
    public void writeSpawnData(ByteBuf data)
    {
        data.writeInt(thrower != null ? thrower.getEntityId() : -1);
        ByteBufUtils.writeItemStack(data, this.getPotionStack());
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        final Entity shooter = world.getEntityByID(data.readInt());

        if (shooter instanceof EntityLivingBase)
        {
            this.thrower = (EntityLivingBase) shooter;
        }
        ItemStack stack = ByteBufUtils.readItemStack(data);
        this.setPotionStack(stack);
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.07F;
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            if (getPotionEffect().getPotion() instanceof HSPotionFlame)
            {
                this.world.playEvent(2002, new BlockPos(this), getPotionEffect().getPotion().getLiquidColor());
                for (int x = -1; x <= 1; x++)
                {
                    for (int y = 0; y <= 2; y++)
                    {
                        for (int z = -1; z <= 1; z++)
                        {
                            BlockPos pos = this.getPosition().add(x, y, z);
                            if (world.isAirBlock(pos))
                            {
                                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                            }
                        }
                    }
                }
            }
            else if (getPotionEffect().getPotion() instanceof HSPotionWater)
            {
                this.world.playEvent(2002, new BlockPos(this), getPotionEffect().getPotion().getLiquidColor());
                for (int x = -1; x <= 1; x++)
                {
                    for (int y = 0; y <= 2; y++)
                    {
                        for (int z = -1; z <= 1; z++)
                        {
                            BlockPos pos = this.getPosition().add(x, y, z);
                            if (world.getBlockState(pos).getBlock() == Blocks.FIRE)
                            {
                                world.setBlockToAir(pos);
                            }
                        }
                    }
                }
            }
            else if (getPotionEffect() != null)
            {
                this.world.playEvent(2002, new BlockPos(this), getPotionEffect().getPotion().getLiquidColor());
                EntityAreaEffectCloud effectCloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
                effectCloud.setRadius(3.0F);
                effectCloud.setRadiusOnUse(-0.5F);
                effectCloud.setWaitTime(10);
                effectCloud.setDuration(effectCloud.getDuration() / 2);
                effectCloud.setRadiusPerTick(-effectCloud.getRadius() / (float) effectCloud.getDuration());
                effectCloud.addEffect(getPotionEffect());
                this.world.spawnEntity(effectCloud);
            }
            this.playSound(HSSoundEvents.ITEM_POTION_BREAK.getSoundEvent(), 0.75F, 1.0F);
            this.setDead();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        if (getPotionStack() != null)
        {
            compound.setTag("PotionStack", getPotionStack().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("PotionStack"))
        {
            setPotionStack(new ItemStack(compound.getCompoundTag("PotionStack")));
        }
    }
}
