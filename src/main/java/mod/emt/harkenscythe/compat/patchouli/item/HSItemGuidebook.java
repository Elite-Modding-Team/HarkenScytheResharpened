package mod.emt.harkenscythe.compat.patchouli.item;

import javax.annotation.Nonnull;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.item.HSItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

public class HSItemGuidebook extends HSItem
{
    private static final ResourceLocation book = new ResourceLocation(HarkenScythe.MOD_ID, "reaper_guidebook");

    public HSItemGuidebook()
    {
        super(EnumRarity.COMMON);
    }

    public static boolean isOpen()
    {
        return book.equals(PatchouliAPI.instance.getOpenBookGui());
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn)
    {
        ItemStack stack = player.getHeldItem(handIn);

        if (player instanceof EntityPlayerMP)
        {
            PatchouliAPI.instance.openBookGUI((EntityPlayerMP) player, book);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
