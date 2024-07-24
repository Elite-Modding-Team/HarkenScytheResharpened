package mod.emt.harkenscythe.client.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.item.HSItemSpectralPotion;

@SideOnly(Side.CLIENT)
public class HSItemSpectralPotionColorHandler implements IItemColor
{
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex)
    {
        return tintIndex > 0 ? -1 : ((HSItemSpectralPotion) stack.getItem()).getPotionEffect().getPotion().getLiquidColor();
    }
}
