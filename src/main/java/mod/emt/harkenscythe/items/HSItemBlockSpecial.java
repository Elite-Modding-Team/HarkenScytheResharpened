package mod.emt.harkenscythe.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;

public class HSItemBlockSpecial extends ItemBlockSpecial
{
    private final EnumRarity rarity;

    public HSItemBlockSpecial(Block block, EnumRarity rarity)
    {
        super(block);
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }
}
