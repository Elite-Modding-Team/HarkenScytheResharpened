package mod.emt.harkenscythe.items.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class HSHoe extends ItemHoe
{
    EnumRarity rarity;

    public HSHoe(ToolMaterial material, EnumRarity rarity)
    {
        super(material);
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }
}
