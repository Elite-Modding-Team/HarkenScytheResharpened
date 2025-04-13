package mod.emt.harkenscythe.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;

import java.util.List;

public class HSAIFollowHerd extends EntityAIBase
{
    private final EntityLiving entity;
    private final Predicate<EntityLiving> followPredicate;
    private final double speedModifier;
    private final PathNavigate navigation;
    private final float stopDistance;
    private final float areaSize;
    private EntityLiving followingEntity;
    private int timeToRecalcPath;
    private float oldWaterCost;

    public HSAIFollowHerd(final EntityLiving entity, double speedModifier, float stopDistance, float areaSize)
    {
        this.entity = entity;
        this.followPredicate = entityLiving -> entityLiving != null && entity != entityLiving;
        this.speedModifier = speedModifier;
        this.navigation = entity.getNavigator();
        this.stopDistance = stopDistance;
        this.areaSize = areaSize;
        this.setMutexBits(3);

        if (!(entity.getNavigator() instanceof PathNavigateGround) && !(entity.getNavigator() instanceof PathNavigateFlying))
        {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    public boolean shouldExecute()
    {
        if (this.entity.ticksExisted % 100 == 0 && this.followingEntity == null)
        {
            List<EntityLiving> list = this.entity.world.getEntitiesWithinAABB(this.entity.getClass(), this.entity.getEntityBoundingBox().grow(this.areaSize), this.followPredicate);

            if (!list.isEmpty())
            {
                for (EntityLiving entityliving : list)
                {
                    if (!entityliving.isInvisible())
                    {
                        this.followingEntity = entityliving;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting()
    {
        return this.followingEntity != null && !this.navigation.noPath() && this.entity.getDistanceSq(this.followingEntity) > (double) (this.stopDistance * this.stopDistance);
    }

    public void startExecuting()
    {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);
        this.entity.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    public void resetTask()
    {
        this.followingEntity = null;
        this.navigation.clearPath();
        this.entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    public void updateTask()
    {
        if (this.followingEntity != null && !this.entity.getLeashed())
        {
            this.entity.getLookHelper().setLookPositionWithEntity(this.followingEntity, 10.0F, (float) this.entity.getVerticalFaceSpeed());

            if (--this.timeToRecalcPath <= 0)
            {
                this.timeToRecalcPath = 10;
                double d0 = this.entity.posX - this.followingEntity.posX;
                double d1 = this.entity.posY - this.followingEntity.posY;
                double d2 = this.entity.posZ - this.followingEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (d3 > (this.stopDistance * this.stopDistance))
                {
                    this.navigation.tryMoveToEntityLiving(this.followingEntity, this.speedModifier);
                }
                else
                {
                    this.navigation.clearPath();
                    EntityLookHelper entitylookhelper = this.followingEntity.getLookHelper();

                    if (d3 <= this.stopDistance || entitylookhelper.getLookPosX() == this.entity.posX && entitylookhelper.getLookPosY() == this.entity.posY && entitylookhelper.getLookPosZ() == this.entity.posZ)
                    {
                        double d4 = this.followingEntity.posX - this.entity.posX;
                        double d5 = this.followingEntity.posZ - this.entity.posZ;
                        this.navigation.tryMoveToXYZ(this.entity.posX - d4, this.entity.posY, this.entity.posZ - d5, this.speedModifier);
                    }
                }
            }
        }
    }
}
