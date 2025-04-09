package mod.emt.harkenscythe.compat.tinkers.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.emt.harkenscythe.event.HSEventLivingHurt;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitBloodConjuration extends AbstractTrait
{
    public TraitBloodConjuration()
    {
        super("blood_conjuration", 0x87201B);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        World world = target.getEntityWorld();

        // Chance is 20%
        if (wasHit && !target.isDead && random.nextDouble() <= 0.2D)
        {
            HSEventLivingHurt.spawnBlood(world, target);
        }
    }
}
