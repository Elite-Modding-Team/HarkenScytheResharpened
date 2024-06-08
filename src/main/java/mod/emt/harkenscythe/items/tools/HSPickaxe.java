package mod.emt.harkenscythe.items.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class HSPickaxe extends ItemPickaxe
{
    private final EnumRarity rarity;

    public HSPickaxe(ToolMaterial material, EnumRarity rarity)
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
