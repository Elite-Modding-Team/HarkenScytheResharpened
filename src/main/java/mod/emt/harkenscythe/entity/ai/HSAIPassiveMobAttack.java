package mod.emt.harkenscythe.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;

public class HSAIPassiveMobAttack extends EntityAIAttackMelee
{
    public HSAIPassiveMobAttack(EntityCreature entity, double moveSpeed, boolean longMemory)
    {
        super(entity, moveSpeed, longMemory);
    }

    @Override
    protected void checkAndPerformAttack(EntityLivingBase entity, double distance)
    {
        double reach = getAttackReachSqr(entity);
        if (distance <= reach && this.attackTick <= 0)
        {
            this.attackTick = 20;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            entity.attackEntityFrom(DamageSource.causeMobDamage(this.attacker), 2.0F);
        }
    }
}
