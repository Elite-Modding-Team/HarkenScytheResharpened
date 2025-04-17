package mod.emt.harkenscythe.item;

import net.minecraft.block.BlockSoulSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSItemCreepball extends HSItem
{
    public HSItemCreepball()
    {
        super(EnumRarity.COMMON);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (world.getBlockState(pos).getBlock() instanceof BlockSoulSand)
        {
            world.setBlockState(pos, HSBlocks.creep_block.getDefaultState());
            if (!player.capabilities.isCreativeMode)
            {
                player.getHeldItem(hand).shrink(1);
            }
            world.playSound(player, pos, HSSoundEvents.ITEM_CREEP_BALL_USE.getSoundEvent(), SoundCategory.BLOCKS, 0.8F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }
}
