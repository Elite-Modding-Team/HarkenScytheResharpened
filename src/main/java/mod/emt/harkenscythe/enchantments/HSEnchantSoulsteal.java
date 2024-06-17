package mod.emt.harkenscythe.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HSEnchantSoulsteal extends Enchantment
{
    public HSEnchantSoulsteal(String name)
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName(name);
        this.setRegistryName(name);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
}
