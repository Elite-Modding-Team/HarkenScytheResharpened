package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSEnumFaction;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class HSEnchantmentBloodletting extends HSEnchantment
{
    public HSEnchantmentBloodletting(String name)
    {
        super(name, Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel()
    {
        return HSConfig.ENCHANTMENTS.bloodlettingMaxLevel;
    }

    @Override
    protected HSEnumFaction getFaction()
    {
        return HSEnumFaction.BLOOD;
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
