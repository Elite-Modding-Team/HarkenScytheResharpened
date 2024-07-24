package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import mod.emt.harkenscythe.HarkenScythe;

public class HSEnchantmentExude extends Enchantment
{
    public HSEnchantmentExude(String name)
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, EntityEquipmentSlot.values());
        this.setName(HarkenScythe.MOD_ID + "." + name);
        this.setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
