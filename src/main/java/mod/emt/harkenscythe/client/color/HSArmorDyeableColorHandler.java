package mod.emt.harkenscythe.client.color;

import mod.emt.harkenscythe.item.armor.HSArmorDyeable;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSArmorDyeableColorHandler implements IItemColor
{
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex)
    {
        return tintIndex > 0 ? -1 : ((HSArmorDyeable) stack.getItem()).getColor(stack);
    }
}
