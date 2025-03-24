package mod.emt.harkenscythe.compat.hwyla;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.cbcore.LangUtil;
import mod.emt.harkenscythe.block.HSBlockBiomassCrop;

public class HSHWYLABiomassCrop implements IWailaDataProvider
{
    public HSHWYLABiomassCrop()
    {
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currentTip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        if (config.getConfig("general.showcrop"))
        {
            IBlockState state = accessor.getBlockState();
            if (state.getBlock() instanceof HSBlockBiomassCrop)
            {
                int age = state.getValue(HSBlockBiomassCrop.AGE);
                float growthValue = (age / 3F) * 100F;
                if (growthValue < 100F)
                {
                    tooltip.add(LangUtil.translateG("hud.msg.growth") + " : " + LangUtil.translateG("hud.msg.growth.value", (int) growthValue));
                }
                else
                {
                    tooltip.add(LangUtil.translateG("hud.msg.growth") + " : " + LangUtil.translateG("hud.msg.mature"));
                }
            }
        }
        return tooltip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currentTip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
    {
        return tag;
    }
}
