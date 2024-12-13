package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HSEnchantmentHemorrhage extends HSEnchantment
{
    public HSEnchantmentHemorrhage(String name)
    {
        super(name, Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND}, Faction.BLOOD);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
