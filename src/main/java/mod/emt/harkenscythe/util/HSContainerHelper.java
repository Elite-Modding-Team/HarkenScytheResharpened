package mod.emt.harkenscythe.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;

import mod.emt.harkenscythe.entity.HSEntityBlood;
import mod.emt.harkenscythe.entity.HSEntityEssence;
import mod.emt.harkenscythe.entity.HSEntitySoul;
import mod.emt.harkenscythe.init.HSEnumContainerType;
import mod.emt.harkenscythe.init.HSEnumFaction;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItemEssenceContainer;

public class HSContainerHelper
{
    public static boolean isContainer(ItemStack stack)
    {
        return stack.getItem() instanceof HSItemEssenceContainer;
    }

    public static HSEnumContainerType getContainerType(ItemStack stack)
    {
        if (isContainer(stack))
        {
            return ((HSItemEssenceContainer) stack.getItem()).getContainerType();
        }
        return null;
    }

    public static HSEnumFaction getFaction(ItemStack stack)
    {
        if (isContainer(stack))
        {
            return ((HSItemEssenceContainer) stack.getItem()).getFaction();
        }
        return null;
    }

    public static boolean isBasicKeeper(ItemStack stack)
    {
        HSEnumContainerType containerType = getContainerType(stack);
        return containerType == HSEnumContainerType.BASIC_KEEPER;
    }

    public static boolean isKeeper(ItemStack stack)
    {
        HSEnumContainerType containerType = getContainerType(stack);
        return containerType == HSEnumContainerType.KEEPER;
    }

    public static boolean isVessel(ItemStack stack)
    {
        HSEnumContainerType containerType = getContainerType(stack);
        return containerType == HSEnumContainerType.VESSEL;
    }

    public static boolean isTrinket(ItemStack stack)
    {
        HSEnumContainerType containerType = getContainerType(stack);
        return containerType == HSEnumContainerType.TRINKET;
    }

    public static boolean isEtherealTrinket(ItemStack stack)
    {
        HSEnumContainerType containerType = getContainerType(stack);
        return containerType == HSEnumContainerType.ETHEREAL_TRINKET;
    }

    public static boolean isAnyTrinket(ItemStack stack)
    {
        return isTrinket(stack) || isEtherealTrinket(stack);
    }

    public static ItemStack getEmptyContainer(ItemStack stack)
    {
        return new ItemStack(getContainerItem(getContainerType(stack), HSEnumFaction.NEUTRAL));
    }

    public static ItemStack getFullContainer(ItemStack stack)
    {
        return new ItemStack(getContainerItem(getContainerType(stack), getFaction(stack)));
    }

    public static ItemStack getFullContainerFaction(ItemStack stack, HSEnumFaction faction)
    {
        return new ItemStack(getContainerItem(getContainerType(stack), faction));
    }

    public static boolean isBloodFaction(ItemStack stack)
    {
        HSEnumFaction faction = getFaction(stack);
        return faction == HSEnumFaction.BLOOD;
    }

    public static boolean isSoulFaction(ItemStack stack)
    {
        HSEnumFaction faction = getFaction(stack);
        return faction == HSEnumFaction.SOUL;
    }

    public static boolean isNeutralFaction(ItemStack stack)
    {
        HSEnumFaction faction = getFaction(stack);
        return faction == HSEnumFaction.NEUTRAL;
    }

    public static boolean collectEssence(ItemStack stack, HSEntityEssence essence, EntityPlayer player, EnumHand hand)
    {
        if (!essence.isDead && essence.getHealth() > 0)
        {
            // Getting essence faction and quantity
            int quantity = 0;
            if (essence.getFaction() == HSEnumFaction.BLOOD)
            {
                quantity = ((HSEntityBlood) essence).getBloodQuantity();
            }
            else if (essence.getFaction() == HSEnumFaction.SOUL)
            {
                quantity = ((HSEntitySoul) essence).getSoulQuantity();
            }
            if (quantity <= 0) return false;
            // Interacting with an empty container
            if (isNeutralFaction(stack))
            {
                ItemStack newStack = getFullContainerFaction(stack, essence.getFaction());
                newStack.setItemDamage(newStack.getMaxDamage() - quantity);
                stack.shrink(1);
                player.setHeldItem(hand, newStack);
            }
            // Interacting with a (partially) filled container
            else if ((isBloodFaction(stack) && essence.getFaction() == HSEnumFaction.BLOOD) || (isSoulFaction(stack) && essence.getFaction() == HSEnumFaction.SOUL))
            {
                // Already filled
                if (stack.getItemDamage() == 0) return false;
                // Partially filled
                if (stack.getItemDamage() > 0)
                {
                    stack.setItemDamage(stack.getItemDamage() - quantity);
                }
                // Becoming filled
                if (stack.getItemDamage() <= 0)
                {
                    ItemStack newStack = getFullContainer(stack);
                    stack.shrink(1);
                    player.setHeldItem(hand, newStack);
                }
            }
            else
            {
                return false;
            }
            float pitch = stack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) stack.getItemDamage() / stack.getMaxDamage() * 0.5F);
            if (!isVessel(stack)) pitch += 0.5F;
            player.world.playSound(null, player.getPosition(), HSSoundEvents.ITEM_BOTTLE_ESSENCE.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, pitch);
            player.world.spawnParticle(EnumParticleTypes.CLOUD, essence.posX, essence.posY + 1.5D, essence.posZ, 0.0D, 0.1D, 0.0D);
            essence.recentlyHit = 60;
            essence.setHealth(0);
            return true;
        }
        return false;
    }

    private static Item getContainerItem(HSEnumContainerType containerType, HSEnumFaction faction)
    {
        if (faction == HSEnumFaction.NEUTRAL)
        {
            if (containerType == HSEnumContainerType.BASIC_KEEPER) return HSItems.basic_essence_keeper;
            if (containerType == HSEnumContainerType.KEEPER) return HSItems.essence_keeper;
            if (containerType == HSEnumContainerType.VESSEL) return HSItems.essence_vessel;
        }
        else if (faction == HSEnumFaction.BLOOD)
        {
            if (containerType == HSEnumContainerType.BASIC_KEEPER) return HSItems.basic_essence_keeper_blood;
            if (containerType == HSEnumContainerType.KEEPER) return HSItems.essence_keeper_blood;
            if (containerType == HSEnumContainerType.VESSEL) return HSItems.essence_vessel_blood;
            if (containerType == HSEnumContainerType.TRINKET) return HSItems.essence_trinket_blood;
            if (containerType == HSEnumContainerType.ETHEREAL_TRINKET) return HSItems.essence_trinket_blood_ethereal;
        }
        else if (faction == HSEnumFaction.SOUL)
        {
            if (containerType == HSEnumContainerType.BASIC_KEEPER) return HSItems.basic_essence_keeper_soul;
            if (containerType == HSEnumContainerType.KEEPER) return HSItems.essence_keeper_soul;
            if (containerType == HSEnumContainerType.VESSEL) return HSItems.essence_vessel_soul;
            if (containerType == HSEnumContainerType.TRINKET) return HSItems.essence_trinket_soul;
            if (containerType == HSEnumContainerType.ETHEREAL_TRINKET) return HSItems.essence_trinket_soul_ethereal;
        }
        return null;
    }
}
