package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HSEnchantmentBlight extends HSEnchantment
{
    public HSEnchantmentBlight(String name)
    {
        super(name, Rarity.UNCOMMON, EnumEnchantmentType.BOW, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND}, Faction.SOUL);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
