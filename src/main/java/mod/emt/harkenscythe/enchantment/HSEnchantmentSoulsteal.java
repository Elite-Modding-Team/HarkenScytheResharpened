package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HSEnchantmentSoulsteal extends HSEnchantment
{
    public HSEnchantmentSoulsteal(String name)
    {
        super(name, Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND}, Faction.SOUL);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
