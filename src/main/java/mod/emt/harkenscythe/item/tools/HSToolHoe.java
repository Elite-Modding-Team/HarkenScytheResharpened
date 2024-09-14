package mod.emt.harkenscythe.item.tools;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSItems;

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
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (stack.isItemDamaged() && this.getToolMaterial() == HSItems.TOOL_BIOMASS && entity.ticksExisted % 1200 == 0)
        {
            stack.setItemDamage(stack.getItemDamage() - world.rand.nextInt(3));
        }
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
