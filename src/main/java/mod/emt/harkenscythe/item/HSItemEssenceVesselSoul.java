package mod.emt.harkenscythe.item;

import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.item.ItemStack;

public class HSItemEssenceVesselSoul extends HSItemEssenceKeeperSoul
{
    public HSItemEssenceVesselSoul()
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
