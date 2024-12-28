package mod.emt.harkenscythe.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class HSEntitySoulbearer extends EntityMob implements IMob
{
    public HSEntitySoulbearer(World world) {
        super(world);
        this.setSize(3.75F, 4.0F);
        this.isImmuneToFire = true;
    }

    // TODO: Flying melee attack?
    protected void initEntityAI()
    {
        //this.tasks.addTask(5, new EntityTitanbearer.AIRandomFly(this));
        this.tasks.addTask(7, new HSEntitySoulbearer.AILookAround(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return null;
    }

    static class AILookAround extends EntityAIBase
    {
        private final HSEntitySoulbearer parentEntity;

        public AILookAround(HSEntitySoulbearer bearer)
        {
            this.parentEntity = bearer;
            this.setMutexBits(2);
        }

        public boolean shouldExecute()
        {
            return true;
        }

        public void updateTask() {
            if (this.parentEntity.getAttackTarget() == null)
            {
                this.parentEntity.rotationYaw = -((float) MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float) Math.PI);
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
            } else
            {
                EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();

                if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D)
                {
                    double d1 = entitylivingbase.posX - this.parentEntity.posX;
                    double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
                    this.parentEntity.rotationYaw = -((float) MathHelper.atan2(d1, d2)) * (180F / (float) Math.PI);
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
                }
            }
        }
    }

    public boolean isAttacking()
    {
        return false;
    }
}