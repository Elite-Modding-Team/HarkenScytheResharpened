package mod.emt.harkenscythe.block;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundTypes;
import mod.emt.harkenscythe.util.HSContainerHelper;

@SuppressWarnings("deprecation")
public class HSBlockCreep extends Block
{
    protected static final AxisAlignedBB CREEP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public HSBlockCreep()
    {
        super(Material.GRASS, MapColor.RED);
        this.setHardness(0.6F);
        this.setHarvestLevel("shovel", 0);
        this.setSoundType(HSSoundTypes.BIOMASS);
        this.setTickRandomly(true);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
    {
        return CREEP_AABB;
    }

    // TODO: See whether this properly works in the Nether
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (!world.isRemote && world.provider.isNether())
        {
            if (!world.isAreaLoaded(pos, 3)) return;
            if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2)
            {
                if (this == HSBlocks.creep_block) world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
            }
            else
            {
                if (world.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !world.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        IBlockState iblockstate = world.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = world.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == Blocks.SOUL_SAND && world.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(world, pos.up()) <= 2)
                        {
                            world.setBlockState(blockpos, HSBlocks.creep_block.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    // TODO: Fancier particles? Maybe little red particles?
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(state, world, pos, rand);

        if (rand.nextInt(10) == 0)
        {
            world.spawnParticle(EnumParticleTypes.TOWN_AURA, (float) pos.getX() + rand.nextFloat(), (float) pos.getY() + 1.1F, (float) pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Blocks.SOUL_SAND.getItemDropped(Blocks.SOUL_SAND.getDefaultState(), rand, fortune);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldStack = player.getHeldItem(hand);
        Item heldItem = heldStack.getItem();
        if (HSContainerHelper.isBloodFaction(heldStack) && this == HSBlocks.creep_block_tilled)
        {
            return bloodyGround(world, pos, player, hand, heldStack, heldItem);
        }
        else if (heldItem == HSItems.germinated_biomass_seed && (this == HSBlocks.creep_block_tilled || this == HSBlocks.creep_block_tilled_bloodied))
        {
            return plantCrop(world, pos, player, heldStack);
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        entity.motionX *= 0.4D;
        entity.motionZ *= 0.4D;
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    private boolean bloodyGround(World world, BlockPos pos, EntityPlayer player, EnumHand hand, ItemStack heldStack, Item heldItem)
    {
        world.setBlockState(pos, HSBlocks.creep_block_tilled_bloodied.getDefaultState());
        if (!player.capabilities.isCreativeMode)
        {
            if (heldStack.getItemDamage() + 5 < heldStack.getMaxDamage())
            {
                heldStack.setItemDamage(heldStack.getItemDamage() + 5);
            }
            else
            {
                ItemStack newStack = HSContainerHelper.getEmptyContainer(heldStack);
                heldStack.shrink(1);
                player.setHeldItem(hand, newStack);
            }
        }
        float pitch = heldStack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) heldStack.getItemDamage() / heldStack.getMaxDamage() * 0.5F);
        if (!HSContainerHelper.isVessel(heldStack)) pitch += 0.5F;
        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, pitch);
        player.addStat(StatList.getObjectUseStats(heldItem));
        return true;
    }

    private boolean plantCrop(World world, BlockPos pos, EntityPlayer player, ItemStack heldStack)
    {
        world.setBlockState(pos.up(), HSBlocks.biomass_crop.getDefaultState());
        if (player instanceof EntityPlayerMP)
        {
            CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), heldStack);
        }
        if (!player.capabilities.isCreativeMode)
        {
            heldStack.shrink(1);
        }
        return true;
    }
}
