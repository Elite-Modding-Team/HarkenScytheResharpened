package mod.emt.harkenscythe.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HSCreativeTabs extends CreativeTabs
{
    public HSCreativeTabs(int length, String name)
    {
        super(length, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack createIcon()
    {
        return new ItemStack(HSItems.essence_keeper, 1);
    }
}
