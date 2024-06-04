package mod.emt.harkenscythe.blocks;

import java.util.Random;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSSoulCrucible extends BlockCauldron
{
    public HSSoulCrucible()
    {
        super();
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {

    }

    // TODO: Works, but needs refactoring
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return false;
        }
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty())
        {
            int level = state.getValue(LEVEL);
            Item item = stack.getItem();

            if (item == HSItems.essence_keeper || item == HSItems.essence_vessel || item == HSItems.essence_keeper_soul || item == HSItems.essence_vessel_soul)
            {
                // Filling the crucible
                if (level < 3 && !player.isSneaking())
                {
                    if (item == HSItems.essence_keeper_soul || item == HSItems.essence_vessel_soul)
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            if (stack.getItemDamage() + 20 < stack.getMaxDamage())
                            {
                                stack.setItemDamage(stack.getItemDamage() + 20);
                            }
                            else if (item == HSItems.essence_keeper_soul)
                            {
                                stack.shrink(1);
                                player.setHeldItem(hand, new ItemStack(HSItems.essence_keeper));
                            }
                            else if (item == HSItems.essence_vessel_soul)
                            {
                                stack.shrink(1);
                                player.setHeldItem(hand, new ItemStack(HSItems.essence_vessel));
                            }
                        }

                        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        this.setWaterLevel(world, pos, state, level + 1);
                    }
                }
                // Emptying the crucible
                else if (level > 0 && player.isSneaking())
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        if (item == HSItems.essence_keeper || item == HSItems.essence_vessel)
                        {
                            stack.shrink(1);
                            if (item == HSItems.essence_keeper)
                            {
                                ItemStack newEssenceKeeperSoul = new ItemStack(HSItems.essence_keeper_soul);
                                player.setHeldItem(hand, newEssenceKeeperSoul);
                            }
                            else if (item == HSItems.essence_vessel)
                            {
                                ItemStack newEssenceVesselSoul = new ItemStack(HSItems.essence_vessel_soul);
                                newEssenceVesselSoul.setItemDamage(newEssenceVesselSoul.getItemDamage() + 20);
                                player.setHeldItem(hand, newEssenceVesselSoul);
                            }
                        }
                        else if (item == HSItems.essence_keeper_soul || item == HSItems.essence_vessel_soul)
                        {
                            if (stack.getItemDamage() <= 0)
                            {
                                return false;
                            }
                            else if (stack.getItemDamage() - 20 < 0)
                            {
                                stack.setItemDamage(stack.getItemDamage() - 20);
                            }
                            else if (item == HSItems.essence_keeper_soul)
                            {
                                stack.shrink(1);
                                player.setHeldItem(hand, new ItemStack(HSItems.essence_keeper_soul));
                            }
                            else if (item == HSItems.essence_vessel_soul)
                            {
                                stack.shrink(1);
                                player.setHeldItem(hand, new ItemStack(HSItems.essence_vessel_soul));
                            }
                        }
                    }
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.setWaterLevel(world, pos, state, level - 1);
                }
                return true;
            }
        }
        return false;
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
}
