package mod.emt.harkenscythe.compat.bloodmagic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import WayofTime.bloodmagic.ConfigHandler;
import WayofTime.bloodmagic.util.helper.PlayerSacrificeHelper;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.util.HSContainerHelper;

public class HSBloodMagicPlugin
{
    public static ActionResult<ItemStack> onBloodContainerRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();

        int lpAdded = ConfigHandler.values.sacrificialDaggerConversion * 2;

        if (PlayerSacrificeHelper.findAndFillAltar(world, player, lpAdded, true))
        {
            if (!player.capabilities.isCreativeMode)
            {
                if (stack.getItemDamage() + 1 < stack.getMaxDamage())
                {
                    stack.setItemDamage(stack.getItemDamage() + 1);
                }
                else
                {
                    stack.shrink(1);
                    player.setHeldItem(hand, HSContainerHelper.getEmptyContainer(stack));
                }
            }

            float pitch = stack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) stack.getItemDamage() / stack.getMaxDamage() * 0.5F);
            if (!HSContainerHelper.isVessel(stack)) pitch += 0.5F;
            world.playSound(null, player.getPosition(), HSSoundEvents.ITEM_BOTTLE_REMOVE.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, pitch);
            world.playSound(null, player.getPosition(), HSSoundEvents.RANDOM_SUMMON.getSoundEvent(), SoundCategory.BLOCKS, 0.3F, pitch);
            world.playSound(null, player.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

            double posX = player.posX;
            double posY = player.posY;
            double posZ = player.posZ;
            for (int l = 0; l < 8; ++l)
            {
                world.spawnParticle(EnumParticleTypes.REDSTONE, posX + Math.random() - Math.random(), posY + Math.random() - Math.random(), posZ + Math.random() - Math.random(), 0, 0, 0);
            }

            player.addStat(StatList.getObjectUseStats(item));

            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }

        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
