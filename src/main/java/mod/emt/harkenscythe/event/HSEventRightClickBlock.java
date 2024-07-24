package mod.emt.harkenscythe.event;

import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItemCreepball;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventRightClickBlock
{
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        EntityPlayer player = event.getEntityPlayer();
        ItemStack heldItem = event.getItemStack();

        if (heldItem.getItem() instanceof HSItemCreepball && state.getBlock() instanceof BlockSoulSand)
        {
            world.setBlockState(pos, HSBlocks.creep_block.getDefaultState());
            if (!player.capabilities.isCreativeMode)
            {
                heldItem.shrink(1);
            }
            world.playSound(player, pos, HSSoundEvents.ITEM_CREEP_BALL_USE.getSoundEvent(), SoundCategory.BLOCKS, 0.8F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            event.setCanceled(true);
        }
    }
}
