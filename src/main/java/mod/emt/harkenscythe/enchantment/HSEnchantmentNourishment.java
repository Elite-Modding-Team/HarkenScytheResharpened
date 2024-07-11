package mod.emt.harkenscythe.enchantment;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HSEnchantmentNourishment extends Enchantment
{
    public HSEnchantmentNourishment(String name)
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, EntityEquipmentSlot.values());
        this.setName(HarkenScythe.MOD_ID + "." + name);
        this.setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public int getMaxLevel()
    {
        return 4;
    }
}
