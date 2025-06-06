package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSEnumFaction;

public class HSEnchantmentSoulsteal extends HSEnchantment
{
    public HSEnchantmentSoulsteal(String name)
    {
        super(name, Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel()
    {
        return HSConfig.ENCHANTMENTS.soulTetherMaxLevel;
    }

    @Override
    protected HSEnumFaction getFaction()
    {
        return HSEnumFaction.SOUL;
    }
}
