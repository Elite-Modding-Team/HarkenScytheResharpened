package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import mod.emt.harkenscythe.init.HSEnumFaction;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class HSEnchantmentNourishment extends HSEnchantment
{
    public HSEnchantmentNourishment(String name)
    {
        super(name, Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, EntityEquipmentSlot.values());
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    protected HSEnumFaction getFaction()
    {
        return HSEnumFaction.SOUL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack)
    {
        return super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean canApply(@Nonnull ItemStack stack)
    {
        return super.canApply(stack);
    }
}
