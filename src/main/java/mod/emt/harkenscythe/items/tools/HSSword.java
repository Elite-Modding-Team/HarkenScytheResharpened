package mod.emt.harkenscythe.items.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class HSSword extends ItemSword
{
    private final EnumRarity rarity;

    public HSSword(ToolMaterial material, EnumRarity rarity)
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
