package mod.emt.harkenscythe.items;

import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.item.ItemStack;

public class HSEssenceVesselSoul extends HSEssenceKeeperSoul
{
    public HSEssenceVesselSoul()
    {
        super();
        setMaxDamage(40);
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        if (!hasContainerItem(stack))
        {
            return ItemStack.EMPTY;
        }
        return new ItemStack(HSItems.essence_vessel);
    }
}
