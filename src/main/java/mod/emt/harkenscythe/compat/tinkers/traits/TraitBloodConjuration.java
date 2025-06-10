package mod.emt.harkenscythe.compat.tinkers.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import baubles.api.BaublesApi;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.event.HSEventLivingDeath;
import mod.emt.harkenscythe.event.HSEventLivingHurt;
import mod.emt.harkenscythe.init.HSItems;
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
        EntityPlayer playerSource = (EntityPlayer) player;

        // Chance is 20% * level (configurable)
        if (wasHit && !target.isDead && random.nextDouble() <= (HSConfig.MOD_INTEGRATION.bloodConjurationChancePerLevel * data.level))
        {
            if (BaublesApi.isBaubleEquipped(playerSource, HSItems.silence_ring) > 0) return;

            // Spawn souls instead if the reversal ring is equipped
            if (BaublesApi.isBaubleEquipped(playerSource, HSItems.reversal_ring) > 0)
            {
                HSEventLivingDeath.spawnSoul(world, target);
            } else
            {
                HSEventLivingHurt.spawnBlood(world, target);
            }
        }
    }
}
