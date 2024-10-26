package mod.emt.harkenscythe.compat.tinkers.traits;

import java.awt.Color;
import java.util.List;

import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.entity.HSEntityBlood;
import mod.emt.harkenscythe.init.HSSoundEvents;
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
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitBloodIntervention extends AbstractTrait
{
    public TraitBloodIntervention()
    {
        super("blood_intervention", 0x87201B);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (!isSelected) return;
        if (entity.ticksExisted % 40 == 0 && ToolHelper.getCurrentDurability(tool) >= 1)
        {
            int radius = 8;
            AxisAlignedBB area = new AxisAlignedBB(entity.posX - radius, entity.posY - radius, entity.posZ - radius, entity.posX + radius, entity.posY + radius, entity.posZ + radius);
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, area, EntitySelectors.IS_ALIVE);

            for (Entity entity1 : list)
            {
                if (entity1 instanceof HSEntityBlood)
                {
                    if (ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool))
                    {
                        // 2% of total durability
                        int calc = ToolHelper.getMaxDurability(tool) * 2 / 100;

                        tool.damageItem(-calc, entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null);
                    }

                    if (entity instanceof EntityPlayer)
                    {
                        EntityPlayer player = (EntityPlayer) entity;

                        // 30 seconds of Strength
                        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30 * 20, 0));
                    }

                    if (world.isRemote)
                    {
                        HSParticleHandler.spawnBeamParticles(EnumParticleTypes.REDSTONE, 25, world, entity1.posX, entity1.posY + entity1.getEyeHeight(), entity1.posZ,
                                Color.getColor("Blood Red", 12124160), entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
                    }

                    entity.playSound(HSSoundEvents.ESSENCE_SOUL_SUMMON.getSoundEvent(), 0.4F, 2.0F / (world.rand.nextFloat() * 0.4F + 1.2F));
                }
            }
        }
    }
}
