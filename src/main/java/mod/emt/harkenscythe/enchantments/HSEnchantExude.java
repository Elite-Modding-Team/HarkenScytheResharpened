package mod.emt.harkenscythe.enchantments;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HSEnchantExude extends Enchantment
{
    public HSEnchantExude(String name)
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, EntityEquipmentSlot.values());
        this.setName(name);
        this.setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
