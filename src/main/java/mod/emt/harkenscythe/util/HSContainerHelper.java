package mod.emt.harkenscythe.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mod.emt.harkenscythe.init.HSEnumContainerType;
import mod.emt.harkenscythe.init.HSEnumFaction;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.item.HSItemEssenceContainer;

public class HSContainerHelper
{
    public static HSEnumContainerType getContainerType(ItemStack stack)
    {
        if (stack.getItem() instanceof HSItemEssenceContainer)
        {
            return ((HSItemEssenceContainer) stack.getItem()).getContainerType();
        }
        return null;
    }

    public static HSEnumFaction getFaction(ItemStack stack)
    {
        if (stack.getItem() instanceof HSItemEssenceContainer)
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
