package mod.emt.harkenscythe.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SuppressWarnings("deprecation")
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

    @Override
    public boolean isRepairable()
    {
        return false;
    }

    @Override
    public float getXpRepairRatio(ItemStack stack)
    {
        return 0;
    }
}
