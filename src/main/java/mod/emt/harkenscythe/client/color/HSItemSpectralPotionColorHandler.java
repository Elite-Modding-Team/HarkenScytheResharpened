package mod.emt.harkenscythe.client.color;

import mod.emt.harkenscythe.item.HSItemSpectralPotion;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSItemSpectralPotionColorHandler implements IItemColor
{
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex)
    {
        return tintIndex > 0 ? -1 : ((HSItemSpectralPotion) stack.getItem()).getPotionEffect().getPotion().getLiquidColor();
    }
}
