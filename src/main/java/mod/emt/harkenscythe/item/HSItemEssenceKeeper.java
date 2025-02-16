package mod.emt.harkenscythe.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class HSItemEssenceKeeper extends HSItem
{
    public HSItemEssenceKeeper()
    {
        super(EnumRarity.COMMON);
        setMaxStackSize(1);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }
}
