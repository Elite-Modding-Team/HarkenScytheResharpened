package mod.emt.harkenscythe.item;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;

@SuppressWarnings("deprecation")
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
