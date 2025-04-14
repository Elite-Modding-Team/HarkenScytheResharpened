package mod.emt.harkenscythe.compat.patchouli.item;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItem;
import vazkii.patchouli.api.PatchouliAPI;

public class HSItemGuidebook extends HSItem
{
    private static final ResourceLocation book = new ResourceLocation(HarkenScythe.MOD_ID, "reaper_guidebook");

    public static boolean isOpen()
    {
        return book.equals(PatchouliAPI.instance.getOpenBookGui());
    }

    public HSItemGuidebook()
    {
        super(EnumRarity.COMMON);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn)
    {
        ItemStack stack = player.getHeldItem(handIn);

        if (player instanceof EntityPlayerMP)
        {
            PatchouliAPI.instance.openBookGUI((EntityPlayerMP) player, book);
            world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.BLOCK_ALTAR_BOOK_OPEN.getSoundEvent(), SoundCategory.PLAYERS, 0.6F, 0.7F + player.getRNG().nextFloat() * 0.4F);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
