package mod.emt.harkenscythe.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class HSAIMasterHurtByTarget extends EntityAITarget
{
    EntityCreature minion;
    EntityLivingBase master;
    EntityLivingBase target;
    private int timestamp;

    public HSAIMasterHurtByTarget(EntityCreature minion, EntityLivingBase master)
    {
        super(minion, false);
        this.minion = minion;
        this.master = master;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        if (this.master == null)
        {
            return false;
        }
        else
        {
            this.target = this.master.getRevengeTarget();
            int i = this.master.getRevengeTimer();
            return i != this.timestamp && this.isSuitableTarget(this.target, false);
        }
    }

    @Override
    public void startExecuting()
    {
        if (this.target != this.master)
        {
            this.taskOwner.setAttackTarget(this.target);
        }
        if (this.master != null)
        {
            this.timestamp = this.master.getRevengeTimer();
        }
        super.startExecuting();
    }
}
