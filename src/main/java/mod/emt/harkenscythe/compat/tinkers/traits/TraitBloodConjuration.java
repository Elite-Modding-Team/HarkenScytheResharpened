package mod.emt.harkenscythe.compat.tinkers.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.event.HSEventLivingHurt;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class TraitBloodConjuration extends AbstractTraitLeveled
{
    public TraitBloodConjuration(int level)
    {
        super("blood_conjuration", 0x87201B, HSConfig.MOD_INTEGRATION.bloodConjurationTraitMaxLevel, level);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        World world = target.getEntityWorld();
        ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, name));

        // Chance is 20% * level (configurable)
        if (wasHit && !target.isDead && random.nextDouble() <= (HSConfig.MOD_INTEGRATION.bloodConjurationChancePerLevel * data.level))
        {
            HSEventLivingHurt.spawnBlood(world, target);
        }
    }
}
