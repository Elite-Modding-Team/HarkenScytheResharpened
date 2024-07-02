package mod.emt.harkenscythe.client.renderers;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.tileentities.HSTileEntityAltar;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHSSoulAltar extends RenderHSAltar
{
    private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/soul_altar_book.png");

    @Override
    public ResourceLocation getBookTexture()
    {
        return TEXTURE_BOOK;
    }

    @Override
    public int getTextColor(HSTileEntityAltar te)
    {
        return te.getValidRecipe() ? 0x55FFFF : 0xFFFFFF;
    }
}
