package mod.emt.harkenscythe.item.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

@SuppressWarnings("deprecation")
public class HSToolSword extends ItemSword implements IHSTool
{
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolSword(ToolMaterial material, EnumRarity rarity)
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
