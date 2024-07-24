package mod.emt.harkenscythe.item;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSItems;

public class HSItemSpectralBottle extends HSItem
{
    public HSItemSpectralBottle(EnumRarity rarity)
    {
        super(rarity);
        this.setMaxStackSize(16);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (!world.isRemote)
        {
            ItemStack itemStack = player.getHeldItem(hand);
            RayTraceResult rayTraceResult = this.rayTrace(world, player, true);
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos pos = rayTraceResult.getBlockPos();
                IBlockState state = world.getBlockState(pos);
                IBlockState stateUp = world.getBlockState(pos.up());
                if (state.getMaterial() == Material.WATER || stateUp.getMaterial() == Material.FIRE)
                {
                    if (stateUp.getMaterial() == Material.FIRE) world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 11);
                    itemStack.shrink(1);
                    player.addItemStackToInventory(state.getMaterial() == Material.WATER ? new ItemStack(HSItems.spectral_potion_water) : new ItemStack(HSItems.spectral_potion_flame));
                    world.playSound(null, player.getPosition(), state.getMaterial() == Material.WATER ? SoundEvents.ITEM_BOTTLE_FILL : SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
                    player.addStat(StatList.getObjectUseStats(this));
                    return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
