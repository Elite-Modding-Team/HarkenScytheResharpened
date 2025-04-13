package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSEnumFaction;

public abstract class HSEnchantment extends Enchantment
{
    protected HSEnchantment(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots)
    {
        super(rarity, type, slots);
        this.setName(HarkenScythe.MOD_ID + "." + name);
        this.setRegistryName(HarkenScythe.MOD_ID, name);
    }

    protected abstract HSEnumFaction getFaction();
}
