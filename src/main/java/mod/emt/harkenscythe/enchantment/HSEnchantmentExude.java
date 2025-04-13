package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import mod.emt.harkenscythe.init.HSEnumFaction;

public class HSEnchantmentExude extends HSEnchantment
{
    public HSEnchantmentExude(String name)
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
        return HSEnumFaction.BLOOD;
    }
}
