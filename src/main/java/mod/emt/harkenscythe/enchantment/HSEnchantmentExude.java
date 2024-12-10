package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HSEnchantmentExude extends HSEnchantment
{
    public HSEnchantmentExude(String name)
    {
        super(name, Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, EntityEquipmentSlot.values(), Faction.SOUL);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
