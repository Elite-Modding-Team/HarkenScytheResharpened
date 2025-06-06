package mod.emt.harkenscythe.compat.tinkers.traits.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTraitLeveled;

import java.awt.*;
import java.util.List;

import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.entity.HSEntityBlood;
import mod.emt.harkenscythe.init.HSSoundEvents;

import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitBloodInterventionArmor extends AbstractArmorTraitLeveled
{
    public TraitBloodInterventionArmor(int level)
    {
        super("blood_intervention", 0x87201B, 3, level);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player)
    {
        ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(armor, name));
        
        if (player.ticksExisted % (120 / data.level) == 0 && ToolHelper.getCurrentDurability(armor) >= 1)
        {
            int radius = 2 + (2 * data.level);
            AxisAlignedBB area = new AxisAlignedBB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, area, EntitySelectors.IS_ALIVE);

            for (Entity entity1 : list)
            {
                if (entity1 instanceof HSEntityBlood)
                {
                    if (ToolHelper.getCurrentDurability(armor) < ToolHelper.getMaxDurability(armor))
                    {
                        // 1% of total durability
                        int calc = ToolHelper.getMaxDurability(armor) / 100;

                        if (player instanceof EntityLivingBase)
                        {
                            ArmorHelper.healArmor(armor, calc, player, EntityLiving.getSlotForItemStack(armor).getIndex());
                        }
                    }

                    if (player instanceof EntityPlayer)
                    {
                        // 10 seconds of Strength * level
                        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, (10 * data.level) * 20, 0));
                    }

                    if (FMLLaunchHandler.side().isClient())
                    {
                        HSParticleHandler.spawnBeamParticles(EnumParticleTypes.REDSTONE, 20, world, entity1.posX, entity1.posY + entity1.getEyeHeight(), entity1.posZ, Color.getColor("Blood Red", 12124160), player.posX, player.posY + player.getEyeHeight(), player.posZ);
                    }

                    player.playSound(HSSoundEvents.RANDOM_SUMMON.getSoundEvent(), 0.2F, 2.0F / (world.rand.nextFloat() * 0.4F + 1.2F));
                }
            }
        }
    }
}
