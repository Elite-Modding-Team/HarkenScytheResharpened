package mod.emt.harkenscythe.blocks;

import java.util.Random;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.items.HSEssenceKeeper;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSBloodCrucible extends BlockCauldron
{
    public HSBloodCrucible()
    {
        super();
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {

    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote) return false;

        ItemStack stack = player.getHeldItem(hand);
        if (stack.isEmpty()) return false;

        Item item = stack.getItem();
        if (!(item instanceof HSEssenceKeeper)) return false;

        int level = state.getValue(LEVEL);
        if (level < 3 && !player.isSneaking() && (item == HSItems.essence_keeper_blood || item == HSItems.essence_vessel_blood))
        {
            fillCrucible(world, pos, state, player, hand, stack, item, level);
        }
        else if (level > 0 && player.isSneaking())
        {
            emptyCrucible(world, pos, state, player, hand, stack, item, level);
        }

        return true;
    }

    @Override
    public void fillWithRain(World world, BlockPos pos)
    {

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this));
    }

    private void fillCrucible(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, Item item, int level)
    {
        if (!player.capabilities.isCreativeMode)
        {
            if (stack.getItemDamage() + 20 < stack.getMaxDamage())
            {
                stack.setItemDamage(stack.getItemDamage() + 20);
            }
            else
            {
                stack.shrink(1);
                if (item == HSItems.essence_keeper_blood)
                {
                    player.setHeldItem(hand, new ItemStack(HSItems.essence_keeper));
                }
                else if (item == HSItems.essence_vessel_blood)
                {
                    player.setHeldItem(hand, new ItemStack(HSItems.essence_vessel));
                }
            }
        }
        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        setWaterLevel(world, pos, state, level + 1);
        player.addStat(StatList.getObjectUseStats(item));
    }

    private void emptyCrucible(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, Item item, int level)
    {
        if (!player.capabilities.isCreativeMode)
        {
            if (item == HSItems.essence_keeper || item == HSItems.essence_vessel)
            {
                stack.shrink(1);
                ItemStack newStack = item == HSItems.essence_keeper ? new ItemStack(HSItems.essence_keeper_blood) : new ItemStack(HSItems.essence_vessel_blood);
                if (item == HSItems.essence_vessel)
                {
                    newStack.setItemDamage(newStack.getMaxDamage() - 20);
                }
                player.setHeldItem(hand, newStack);
            }
            else if (item == HSItems.essence_keeper_blood || item == HSItems.essence_vessel_blood)
            {
                if (stack.getItemDamage() == 0) return;
                if (stack.getItemDamage() > 0)
                {
                    stack.setItemDamage(stack.getItemDamage() - 20);
                }
                if (stack.getItemDamage() <= 0)
                {
                    stack.shrink(1);
                    ItemStack newStack = item == HSItems.essence_keeper_blood ? new ItemStack(HSItems.essence_keeper_blood) : new ItemStack(HSItems.essence_vessel_blood);
                    player.setHeldItem(hand, newStack);
                }
            }
        }
        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        setWaterLevel(world, pos, state, level - 1);
        player.addStat(StatList.getObjectUseStats(item));
    }
}
