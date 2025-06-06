package mod.emt.harkenscythe.compat.tinkers.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.awt.*;
import java.util.List;
import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.entity.HSEntitySoul;
import mod.emt.harkenscythe.init.HSSoundEvents;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitSoulIntervention extends AbstractTraitLeveled
{
    public TraitSoulIntervention(int level)
    {
        super("soul_intervention", 0x006B9F, 3, level);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, name));
        
        if (!isSelected) return;
        if (entity.ticksExisted % (120 / data.level) == 0 && ToolHelper.getCurrentDurability(tool) >= 1)
        {
            int radius = 2 + (2 * data.level);
            AxisAlignedBB area = new AxisAlignedBB(entity.posX - radius, entity.posY - radius, entity.posZ - radius, entity.posX + radius, entity.posY + radius, entity.posZ + radius);
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, area, EntitySelectors.IS_ALIVE);

            for (Entity entity1 : list)
            {
                if (entity1 instanceof HSEntitySoul)
                {
                    if (ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool))
                    {
                        // 1% of total durability
                        int calc = ToolHelper.getMaxDurability(tool) / 100;

                        if (entity instanceof EntityLivingBase)
                        {
                            tool.damageItem(-calc, (EntityLivingBase) entity);
                        }
                    }

                    if (entity instanceof EntityPlayer)
                    {
                        EntityPlayer player = (EntityPlayer) entity;

                        // 10 seconds of Resistance * level
                        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (10 * data.level) * 20, 0));
                    }

                    if (FMLLaunchHandler.side().isClient())
                    {
                        HSParticleHandler.spawnBeamParticles(EnumParticleTypes.REDSTONE, 20, world, entity1.posX, entity1.posY + entity1.getEyeHeight(), entity1.posZ, Color.getColor("Soul Blue", 4560335), entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
                    }

                    entity.playSound(HSSoundEvents.RANDOM_SUMMON.getSoundEvent(), 0.2F, 2.0F / (world.rand.nextFloat() * 0.4F + 1.2F));
                }
            }
        }
    }
}
