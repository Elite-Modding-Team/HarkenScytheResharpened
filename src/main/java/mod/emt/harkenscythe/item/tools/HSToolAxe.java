package mod.emt.harkenscythe.item.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

@SuppressWarnings("deprecation")
public class HSToolAxe extends ItemAxe implements IHSTool
{
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolAxe(ToolMaterial material, float damage, float speed, EnumRarity rarity)
    {
        super(material, damage, speed);
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
