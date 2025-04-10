package mod.emt.harkenscythe.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import mod.emt.harkenscythe.HarkenScythe;

public abstract class HSEnchantment extends Enchantment
{
    private Faction faction;

    protected HSEnchantment(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots, Faction faction)
    {
        super(rarity, type, slots);
        this.setName(HarkenScythe.MOD_ID + "." + name);
        this.setRegistryName(HarkenScythe.MOD_ID, name);
        this.setFaction(faction);
    }

    public Faction getFaction()
    {
        return this.faction;
    }

    public void setFaction(Faction faction)
    {
        this.faction = faction;
    }

    public enum Faction
    {
        BLOOD, SOUL, NEUTRAL
    }
}
