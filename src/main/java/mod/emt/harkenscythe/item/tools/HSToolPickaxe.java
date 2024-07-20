package mod.emt.harkenscythe.item.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

@SuppressWarnings("deprecation")
public class HSToolPickaxe extends ItemPickaxe implements IHSTool
{
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolPickaxe(ToolMaterial material, EnumRarity rarity)
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
