package mod.emt.harkenscythe.client.renderer;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.tileentity.HSTileEntityAltar;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSRendererBlockBloodAltar extends HSRendererBlockAltar
{
    private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation(HarkenScythe.MOD_ID, "textures/entities/blood_altar_book.png");

    @Override
    public ResourceLocation getBookTexture()
    {
        return TEXTURE_BOOK;
    }

    @Override
    public int getTextColor(HSTileEntityAltar te)
    {
        return te.getValidRecipe() ? 0xFF5555 : 0xFFFFFF;
    }
}
