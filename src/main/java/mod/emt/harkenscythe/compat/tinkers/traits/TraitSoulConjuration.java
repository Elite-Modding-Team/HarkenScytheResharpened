package mod.emt.harkenscythe.compat.tinkers.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.emt.harkenscythe.event.HSEventLivingDeath;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitSoulConjuration extends AbstractTrait
{
    public TraitSoulConjuration()
    {
        super("soul_conjuration", 0x006B9F);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        World world = target.getEntityWorld();

        // Chance is 20%
        if (!target.isEntityAlive() && !world.isRemote & random.nextDouble() <= 0.2D)
        {
            HSEventLivingDeath.spawnSoul(world, target);
        }
    }
}
