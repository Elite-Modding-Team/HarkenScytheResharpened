package mod.emt.harkenscythe.items.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class HSAxe extends ItemAxe
{
    private final EnumRarity rarity;

    public HSAxe(ToolMaterial material, float damage, float speed, EnumRarity rarity)
    {
        super(material, damage, speed);
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }
}
