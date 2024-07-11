package mod.emt.harkenscythe.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HSItem extends Item
{
    private final EnumRarity rarity;

    public HSItem(EnumRarity rarity)
    {
        super();
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }
}
