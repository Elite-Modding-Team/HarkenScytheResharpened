package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import mod.emt.harkenscythe.init.HSEnumFaction;

public class HSEnchantmentBlight extends HSEnchantment
{
    public HSEnchantmentBlight(String name)
    {
        super(name, Rarity.UNCOMMON, EnumEnchantmentType.BOW, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    protected HSEnumFaction getFaction()
    {
        return HSEnumFaction.SOUL;
    }
}
