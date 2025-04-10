package mod.emt.harkenscythe.enchantment;

import mod.emt.harkenscythe.item.tool.HSToolGlaive;
import mod.emt.harkenscythe.item.tool.HSToolScythe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class HSEnchantmentAutoReap extends HSEnchantment
{
    public HSEnchantmentAutoReap(String name)
    {
        super(name, Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND}, Faction.NEUTRAL);
    }
 
    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 25;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public boolean isTreasureEnchantment()
    {
        return true;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
    	// Exclusive to only glaives and scythes.
        return stack.getItem() instanceof HSToolGlaive || stack.getItem() instanceof HSToolScythe;
    }
    
    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
    	// Cannot be applied with Mending.
        return super.canApplyTogether(ench) && ench != Enchantments.MENDING;
    }
}
