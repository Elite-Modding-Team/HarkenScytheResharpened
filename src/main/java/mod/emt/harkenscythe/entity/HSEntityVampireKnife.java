package mod.emt.harkenscythe.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import io.netty.buffer.ByteBuf;
import java.awt.*;
import java.util.List;
import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.util.HSDamageSource;

public class HSEntityVampireKnife extends EntityArrow implements IThrowableEntity, IEntityAdditionalSpawnData
{
    @SuppressWarnings("unchecked")
    private static final Predicate<Entity> WEAPON_TARGETS = Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, Entity::canBeCollidedWith);
    private final float damage;
    @Nullable
    private IBlockState inBlockState;
    private int ticksInGround;
    private int ticksInAir;
    private boolean beenInGround;
    private int knockBack;
    private int xTile;
    private int yTile;
    private int zTile;
    private int soundTimer;

    public HSEntityVampireKnife(World world)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inBlockState = null;
        inGround = false;
        arrowShake = 0;
        ticksInAir = 0;
        pickupStatus = PickupStatus.DISALLOWED;
        damage = (float) HSConfig.ITEMS.vampireKnifeProjectileDamage;
        knockBack = 1;
        setSize(0.5F, 0.5F);
    }

    public HSEntityVampireKnife(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public HSEntityVampireKnife(World world, EntityLivingBase shooter)
    {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1D, shooter.posZ);
        setThrower(shooter);
        soundTimer = 0;
    }

    @Override
    public Entity getThrower()
    {
        return shootingEntity;
    }

    @Override
    public void setThrower(Entity entity)
    {
        shootingEntity = entity;
    }

    @Override
    public void shoot(Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        float x = -MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f * 0.017453292F);
        float y = -MathHelper.sin(f * 0.017453292F);
        float z = MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f * 0.017453292F);
        shoot(x, y, z, f3, f4);
        motionX += entity.motionX;
        motionZ += entity.motionZ;

        if (!entity.onGround)
        {
            motionY += entity.motionY;
        }
    }

    @Override
    public void setVelocity(double d, double d1, double d2)
    {
        motionX = d;
        motionY = d1;
        motionZ = d2;

        if (aimRotation() && prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(d * d + d2 * d2);
            float n = (float) (MathHelper.atan2(d, d2) * 180.0D / 3.141592653589793D);
            rotationYaw = n;
            prevRotationYaw = n;
            float n2 = (float) (MathHelper.atan2(d1, f) * 180.0D / 3.141592653589793D);
            rotationPitch = n2;
            prevRotationPitch = n2;
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            ticksInGround = 0;
        }
    }

    @Override
    public void onUpdate()
    {
        onEntityUpdate();

        if (inGround || beenInGround)
        {
            return;
        }

        rotationPitch -= 70.0F;
        if (rotationPitch <= -360) rotationPitch += 360;
        if (soundTimer >= 3)
        {
            playSound(HSSoundEvents.ITEM_VAMPIRE_KNIFE_THROW.getSoundEvent(), 0.1F, 3.0F / (rand.nextFloat() * 0.2F + 0.6F + ticksInAir / 15.0F));
            soundTimer = 0;
        }

        ++soundTimer;
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void setKnockbackStrength(int i)
    {
        knockBack = i;
    }

    // Fixes buggy projectile behavior on the client
    @Override
    public void writeSpawnData(ByteBuf data)
    {
        data.writeInt(shootingEntity != null ? shootingEntity.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        final Entity shooter = world.getEntityByID(data.readInt());

        if (shooter instanceof EntityLivingBase)
        {
            this.shootingEntity = shooter;
        }
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();

        if (aimRotation() && prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
            float n = (float) (MathHelper.atan2(motionX, motionZ) * 180.0D / 3.141592653589793D);
            rotationYaw = n;
            prevRotationYaw = n;
            float n2 = (float) (MathHelper.atan2(motionY, f) * 180.0D / 3.141592653589793D);
            rotationPitch = n2;
            prevRotationPitch = n2;
        }

        if (this.ticksExisted > HSConfig.ITEMS.vampireKnifeDespawnTime)
        {
            this.setDead();
        }

        BlockPos blockpos = new BlockPos(xTile, yTile, zTile);
        IBlockState iblockstate = world.getBlockState(blockpos);

        if (iblockstate.getMaterial() != Material.AIR)
        {
            AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(world, blockpos);

            if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(posX, posY, posZ)))
            {
                inGround = true;
            }
        }

        if (arrowShake > 0)
        {
            --arrowShake;
        }

        if (isWet())
        {
            extinguish();
        }

        if (inGround)
        {
            if (!iblockstate.equals(inBlockState) && !world.collidesWithAnyBlock(getEntityBoundingBox().grow(0.05D)))
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
            }
            else if (!world.isRemote)
            {
                ++ticksInGround;
                int t = getMaxLifetime();
                if (t != 0 && ticksInGround >= t)
                {
                    if (FMLLaunchHandler.side().isClient())
                    {
                        for (double offset = -0.5; offset <= 0.5; offset += 0.1)
                        {
                            HSParticleHandler.spawnColoredParticle(EnumParticleTypes.REDSTONE, this.posX + offset, this.posY + offset, this.posZ, Color.getColor("Blood Red", 12124160), 0.0D, 0.0D, 0.0D);
                            HSParticleHandler.spawnColoredParticle(EnumParticleTypes.REDSTONE, this.posX + offset, this.posY - offset, this.posZ, Color.getColor("Blood Red", 12124160), 0.0D, 0.0D, 0.0D);
                        }
                    }
                    setDead();
                }
            }

            ++timeInGround;
            return;
        }

        timeInGround = 0;
        ++ticksInAir;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        Vec3d vec3d2 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
        RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
        vec3d = new Vec3d(posX, posY, posZ);
        vec3d2 = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);

        if (raytraceresult != null)
        {
            vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
        }

        Entity entity = findEntity(vec3d, vec3d2);

        if (entity != null)
        {
            raytraceresult = new RayTraceResult(entity);
        }

        if (raytraceresult != null)
        {
            if (raytraceresult.entityHit != null)
            {
                onEntityHit(raytraceresult.entityHit);
            }
            else
            {
                onGroundHit(raytraceresult);
            }
        }

        if (getIsCritical())
        {
            for (int i1 = 0; i1 < 2; ++i1)
            {
                world.spawnParticle(EnumParticleTypes.CRIT, posX + motionX * i1 / 4.0D, posY + motionY * i1 / 4.0D, posZ + motionZ * i1 / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
            }
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;

        if (aimRotation())
        {
            float f2 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
            float n3 = (float) (MathHelper.atan2(motionX, motionZ) * 180.0D / 3.141592653589793D);
            rotationYaw = n3;
            prevRotationYaw = n3;
            float n4 = (float) (MathHelper.atan2(motionY, f2) * 180.0D / 3.141592653589793D);
            rotationPitch = n4;
            prevRotationPitch = n4;
        }

        float res = getAirResistance();
        float grav = getGravity();

        if (isInWater())
        {
            for (int i2 = 0; i2 < 4; ++i2)
            {
                float f3 = 0.25f;
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3, motionX, motionY, motionZ);
            }
        }

        motionX *= res;
        motionY *= res;
        motionZ *= res;

        if (!hasNoGravity())
        {
            motionY -= grav;
        }

        setPosition(posX, posY, posZ);
        doBlockCollisions();
    }

    public void onEntityHit(Entity entity)
    {
        if (entity != null && entity == this.shootingEntity) return;
        applyEntityHitEffects(entity);

        // Ignore invincibility frames
        if (entity instanceof EntityLivingBase && HSConfig.ITEMS.vampireKnifeProjectileIgnoreIFrames)
        {
            EntityLivingBase base = (EntityLivingBase) entity;
            base.hurtResistantTime = 0;
            base.hurtTime = 0;
        }

        if (FMLLaunchHandler.side().isClient())
        {
            for (double offset = -0.5; offset <= 0.5; offset += 0.1)
            {
                HSParticleHandler.spawnColoredParticle(EnumParticleTypes.REDSTONE, this.posX + offset, this.posY + offset, this.posZ, Color.getColor("Blood Red", 12124160), 0.0D, 0.0D, 0.0D);
                HSParticleHandler.spawnColoredParticle(EnumParticleTypes.REDSTONE, this.posX + offset, this.posY - offset, this.posZ, Color.getColor("Blood Red", 12124160), 0.0D, 0.0D, 0.0D);
            }
        }

        setDead();
    }

    public void applyEntityHitEffects(Entity entity)
    {
        if (isBurning() && !(entity instanceof EntityEnderman))
        {
            entity.setFire(5);
        }

        if (entity instanceof EntityLivingBase)
        {
            EntityLivingBase entityLiving = (EntityLivingBase) entity;

            float motionDamage = (float) ((Math.abs(motionY) * 2) + damage);
            entityLiving.attackEntityFrom(new HSDamageSource("hs_lifesteal", this.getThrower()), motionDamage);
            playSound(HSSoundEvents.BLOCK_BLOOD_ABSORBER_STOP.getSoundEvent(), 0.4F, 2.0F / (this.world.rand.nextFloat() * 0.4F + 0.8F));

            // Heals by 7.5% (configurable) of damage dealt to the target with indicating leeching particles
            if (shootingEntity instanceof EntityPlayer && ((EntityPlayer) shootingEntity).shouldHeal())
            {
                ((EntityPlayer) shootingEntity).heal(this.damage * (float) HSConfig.ITEMS.vampireKnifeProjectileHealing);
                shootingEntity.playSound(HSSoundEvents.ENTITY_HEMOGLOBIN_IDLE.getSoundEvent(), 0.2F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 0.8F));
                shootingEntity.playSound(HSSoundEvents.BLOCK_BLOOD_ABSORBER_START.getSoundEvent(), 0.2F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 0.8F));

                if (FMLLaunchHandler.side().isClient())
                {
                    HSParticleHandler.spawnBeamParticles(EnumParticleTypes.REDSTONE, 20, world, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, Color.getColor("Blood Red", 12124160), shootingEntity.posX, shootingEntity.posY + shootingEntity.getEyeHeight(), shootingEntity.posZ);
                }
            }

            if (knockBack > 0)
            {
                float f = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);

                if (f > 0.0F)
                {
                    entity.addVelocity(motionX * knockBack * 0.6D / f, 0.1D, motionZ * knockBack * 0.6D / f);
                }
            }

            if (shootingEntity instanceof EntityLivingBase)
            {
                EnchantmentHelper.applyThornEnchantments(entityLiving, shootingEntity);
                EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) shootingEntity, entityLiving);
            }

            if (shootingEntity instanceof EntityPlayerMP && shootingEntity != entity && entity instanceof EntityPlayer)
            {
                ((EntityPlayerMP) shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
            }

            if (shootingEntity instanceof EntityLivingBase)
            {
                int i = EnchantmentHelper.getKnockbackModifier((EntityLivingBase) shootingEntity);

                if (i != 0)
                {
                    ((EntityLivingBase) entity).knockBack(this, i * 0.4F, -MathHelper.sin(rotationYaw * 0.017453292f), -MathHelper.cos(rotationYaw * 0.017453292f));
                }

                i = EnchantmentHelper.getFireAspectModifier((EntityLivingBase) shootingEntity);

                if (i > 0 && !entity.isBurning())
                {
                    entity.setFire(1);
                }
            }
        }
    }

    public void onGroundHit(RayTraceResult raytraceResult)
    {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = world.getBlockState(blockpos);
        motionX = raytraceResult.hitVec.x - posX;
        motionY = raytraceResult.hitVec.y - posY;
        motionZ = raytraceResult.hitVec.z - posZ;
        float f1 = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        posX -= motionX / f1 * 0.05D;
        posY -= motionY / f1 * 0.05D;
        posZ -= motionZ / f1 * 0.05D;
        inGround = true;
        beenInGround = true;
        setIsCritical(false);
        arrowShake = getMaxArrowShake();

        if (inBlockState != null)
        {
            inBlockState.getBlock().onEntityCollision(world, blockpos, inBlockState, this);
        }

        playSound(HSSoundEvents.BLOCK_LIVINGMETAL_STEP.getSoundEvent(), 0.4F, 3.0F / (this.world.rand.nextFloat() * 0.4F + 0.8F));

        if (FMLLaunchHandler.side().isClient())
        {
            HSParticleHandler.spawnColoredParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, Color.getColor("Blood Red", 12124160), 1.0D, 0.0D, 0.0D);
        }

        setDead();
    }

    public boolean aimRotation()
    {
        return beenInGround;
    }

    public int getMaxLifetime()
    {
        return 1200;
    }

    public int getMaxArrowShake()
    {
        return 4;
    }

    public float getGravity()
    {
        return 0.0025F;
    }

    public float getAirResistance()
    {
        return 0.98F;
    }

    @Nullable
    protected Entity findEntity(Vec3d vec3d, Vec3d vec3d1)
    {
        Entity entity = null;
        List<Entity> list = world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1.0D), WEAPON_TARGETS);
        double d = 0.0D;

        for (Entity entity2 : list)
        {
            if (entity2 != shootingEntity || ticksInAir >= 5)
            {
                AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().grow(0.3D);
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d1);

                if (raytraceresult != null)
                {
                    double d2 = vec3d.squareDistanceTo(raytraceresult.hitVec);

                    if (d2 < d || d == 0.0D)
                    {
                        entity = entity2;
                        d = d2;
                    }
                }
            }
        }

        return entity;
    }
}
