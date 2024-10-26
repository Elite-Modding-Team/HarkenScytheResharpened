package mod.emt.harkenscythe.compat.tinkers.traits;

import mod.emt.harkenscythe.event.HSEventLivingDeath;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitSoulConjuration extends AbstractTrait
{
    // Chance is 20%
    private static double CHANCE = 0.2D;

    public TraitSoulConjuration()
    {
        super("soul_conjuration", 0x006B9F);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        World world = target.getEntityWorld();

        if (!target.isEntityAlive() && !world.isRemote & random.nextDouble() <= CHANCE)
        {
            HSEventLivingDeath.spawnSoul(world, target);
        }
    }
}
