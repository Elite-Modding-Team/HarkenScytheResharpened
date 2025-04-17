package mod.emt.harkenscythe.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSSoundEvents;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventUseHoe
{
    @SubscribeEvent
    public static void onUseHoe(UseHoeEvent event)
    {
        if (!HSConfig.ITEMS.hoesTillCreepBlocks) return;
        World world = event.getWorld();
        BlockPos blockPos = event.getPos();
        IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == HSBlocks.creep_block)
        {
            world.setBlockState(blockPos, HSBlocks.creep_block_tilled.getDefaultState());
            world.playSound(null, blockPos, HSSoundEvents.ITEM_GLAIVE_TILL.getSoundEvent(), SoundCategory.BLOCKS, 0.8F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            event.setResult(Event.Result.ALLOW);
        }
    }
}
