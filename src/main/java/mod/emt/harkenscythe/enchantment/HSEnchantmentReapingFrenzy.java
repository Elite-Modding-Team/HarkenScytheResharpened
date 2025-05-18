package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSEnumFaction;
import mod.emt.harkenscythe.item.tool.HSToolGlaive;
import mod.emt.harkenscythe.item.tool.HSToolScythe;

public class HSEnchantmentReapingFrenzy extends HSEnchantment
{
    public HSEnchantmentReapingFrenzy(String name)
    {
        super(name, Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 25;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        // Exclusive to only glaives and scythes.
        return stack.getItem() instanceof HSToolGlaive || stack.getItem() instanceof HSToolScythe;
    }

    @Override
    public boolean isAllowedOnBooks()
    {
        return HSConfig.ENCHANTMENTS.reapingFrenzyEnchanting;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return HSConfig.ENCHANTMENTS.reapingFrenzyEnchanting ? (stack.getItem() instanceof HSToolGlaive || stack.getItem() instanceof HSToolScythe) : false;
    }

    @Override
    protected HSEnumFaction getFaction()
    {
        return HSEnumFaction.NEUTRAL;
    }
}
