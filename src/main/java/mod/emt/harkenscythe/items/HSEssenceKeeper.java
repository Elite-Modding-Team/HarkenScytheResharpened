package mod.emt.harkenscythe.items;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HSEssenceKeeper extends Item
{
    public HSEssenceKeeper()
    {
        super();
        setCreativeTab(HarkenScythe.TAB);
        setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
