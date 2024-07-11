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
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class HSEntitySpectralPotion extends EntityThrowable implements IEntityAdditionalSpawnData
{
    private PotionEffect potionEffect;
    private ItemStack potionStack = new ItemStack(HSItems.spectral_glass_bottle);

    public HSEntitySpectralPotion(World world)
    {
        super(world);
    }

    public HSEntitySpectralPotion(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public HSEntitySpectralPotion(World world, EntityLivingBase thrower)
    {
        super(world, thrower);
    }

    public HSEntitySpectralPotion(World world, EntityLivingBase thrower, ItemStack potionStack)
    {
        super(world, thrower);
        if (potionStack.getItem() instanceof HSItemSpectralPotion)
        {
            this.potionEffect = ((HSItemSpectralPotion) potionStack.getItem()).getPotionEffect();
            this.potionStack = potionStack;
        }
    }

    public ItemStack getPotionStack()
    {
        return potionStack;
    }

    public PotionEffect getPotionEffect()
    {
        return potionEffect;
    }

    // Fixes buggy projectile behavior on the client
    @Override
    public void writeSpawnData(ByteBuf data)
    {
        data.writeInt(thrower != null ? thrower.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        final Entity shooter = world.getEntityByID(data.readInt());

        if (shooter instanceof EntityLivingBase)
        {
            this.thrower = (EntityLivingBase) shooter;
        }
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
            if (potionEffect.getPotion() instanceof HSPotionFlame)
            {
                this.world.playEvent(2002, new BlockPos(this), potionEffect.getPotion().getLiquidColor());
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
            else if (potionEffect.getPotion() instanceof HSPotionWater)
            {
                this.world.playEvent(2002, new BlockPos(this), potionEffect.getPotion().getLiquidColor());
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
            else if (potionEffect != null)
            {
                this.world.playEvent(2002, new BlockPos(this), potionEffect.getPotion().getLiquidColor());
                EntityAreaEffectCloud effectCloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
                effectCloud.setRadius(3.0F);
                effectCloud.setRadiusOnUse(-0.5F);
                effectCloud.setWaitTime(10);
                effectCloud.setDuration(effectCloud.getDuration() / 2);
                effectCloud.setRadiusPerTick(-effectCloud.getRadius() / (float) effectCloud.getDuration());
                effectCloud.addEffect(potionEffect);
                this.world.spawnEntity(effectCloud);
            }
            this.playSound(HSSoundEvents.ITEM_POTION_BREAK, 0.75F, 1.0F);
            this.setDead();
        }
    }
}
