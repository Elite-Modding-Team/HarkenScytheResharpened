package mod.emt.harkenscythe.enchantment;

import mod.emt.harkenscythe.item.tool.HSToolGlaive;
import mod.emt.harkenscythe.item.tool.HSToolScythe;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class HSEnchantmentWillingness extends HSEnchantment
{
    public HSEnchantmentWillingness(String name)
    {
        super(name, Rarity.COMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND}, Faction.NEUTRAL);
    }

    @Override
    public int getMinEnchantability(int level)
    {
        return 5 + (level - 1) * 9;
    }

    @Override
    public int getMaxEnchantability(int level)
    {
        return this.getMinEnchantability(level) + 15;
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
    	// Exclusive to only glaives and scythes.
        return stack.getItem() instanceof HSToolGlaive || stack.getItem() instanceof HSToolScythe;
    }
}
