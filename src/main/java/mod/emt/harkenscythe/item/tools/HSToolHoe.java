package mod.emt.harkenscythe.item.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

@SuppressWarnings("deprecation")
public class HSToolHoe extends ItemHoe implements IHSTool
{
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolHoe(ToolMaterial material, EnumRarity rarity)
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
