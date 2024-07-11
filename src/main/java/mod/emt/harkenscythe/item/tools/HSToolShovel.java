package mod.emt.harkenscythe.item.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class HSToolShovel extends ItemSpade implements IHSTool
{
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolShovel(ToolMaterial material, EnumRarity rarity)
    {
        super(material);
        this.rarity = rarity;
        this.material = material;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }

    @Override
    public ToolMaterial getToolMaterial()
    {
        return this.material;
    }
}
