package mod.emt.harkenscythe.items;

import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.item.ItemStack;

public class HSEssenceVesselBlood extends HSEssenceKeeperBlood
{
    public HSEssenceVesselBlood()
    {
        super();
        setMaxDamage(80);
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
