package mod.emt.harkenscythe.item;

import net.minecraft.item.ItemStack;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSItems;

public class HSItemEssenceVesselBlood extends HSItemEssenceKeeperBlood
{
    public HSItemEssenceVesselBlood()
    {
        super();
        setMaxDamage(HSConfig.ITEMS.bloodVesselEssenceCapacity);
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
