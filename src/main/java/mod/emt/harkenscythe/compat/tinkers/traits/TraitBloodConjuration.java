package mod.emt.harkenscythe.compat.tinkers.traits;

import mod.emt.harkenscythe.event.HSEventLivingHurt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitBloodConjuration extends AbstractTrait
{
    // Chance is 5%
    private static double CHANCE = 0.05D;

    public TraitBloodConjuration()
    {
        super("blood_conjuration", 0x87201B);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        World world = target.getEntityWorld();

        if (wasHit && !target.isDead && random.nextDouble() <= CHANCE)
        {
            HSEventLivingHurt.spawnBlood(world, target);
        }
    }
}
