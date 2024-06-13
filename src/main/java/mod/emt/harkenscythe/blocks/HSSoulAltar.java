package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.tileentities.HSTileEntitySoulAltar;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSSoulAltar extends BlockEnchantmentTable
{
    public HSSoulAltar()
    {
        super();
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HSTileEntitySoulAltar)
        {
            HSTileEntitySoulAltar altar = (HSTileEntitySoulAltar) te;
            altar.dropItem();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new HSTileEntitySoulAltar();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HSTileEntitySoulAltar)
        {
            HSTileEntitySoulAltar altar = (HSTileEntitySoulAltar) te;
            ItemStack altarItem = altar.getInputStack();
            ItemStack heldItem = player.getHeldItem(hand);

            if (heldItem.getItem() == HSItems.harken_athame)
            {
                if (!altarItem.isEmpty() && altar.isValidRecipe())
                {
                    Item item = altar.getInputStack().getItem();
                    int requiredSouls = HSAltarRecipes.getRequiredSouls(altar.getInputStack().getItem());
                    altar.decreaseCrucibleLevel(requiredSouls / 10);
                    altar.getInputStack().shrink(1);
                    if (!player.world.isRemote) player.world.spawnEntity(new EntityItem(player.world, altar.getPos().getX() + 0.5D, altar.getPos().getY() + 1.5D, altar.getPos().getZ() + 0.5D, new ItemStack(HSAltarRecipes.getOutputSoul(item))));
                    player.world.playSound(altar.getPos().getX(), altar.getPos().getY(), altar.getPos().getZ(), HSSoundEvents.BLOCK_SOUL_ALTAR_ENCHANT, SoundCategory.BLOCKS, 0.8F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    player.world.playSound(altar.getPos().getX(), altar.getPos().getY(), altar.getPos().getZ(), SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    for (int i = 0; i < requiredSouls; i++)
                    {
                        player.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, altar.getPos().getX() + 0.5D, altar.getPos().getY() + 2.0D, altar.getPos().getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    }
                    altar.updateSoulCount();
                    altar.updateRecipe();
                    return true;
                }
            }
            else if (!heldItem.isEmpty())
            {
                if (altarItem.isEmpty())
                {
                    altar.setInputStack(heldItem.splitStack(1));
                    player.world.playSound(altar.getPos().getX(), altar.getPos().getY(), altar.getPos().getZ(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    return true;
                }
                else if (altarItem.getMaxStackSize() > altarItem.getCount() && ItemStack.areItemsEqual(altarItem, heldItem) && ItemStack.areItemStackTagsEqual(altarItem, heldItem))
                {
                    heldItem.shrink(1);
                    player.world.playSound(altar.getPos().getX(), altar.getPos().getY(), altar.getPos().getZ(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    altarItem.grow(1);
                    return true;
                }
            }
            else
            {
                ItemStack itemStack = altar.getInputStack();
                if (!itemStack.isEmpty())
                {
                    player.addItemStackToInventory(itemStack);
                    player.world.playSound(altar.getPos().getX(), altar.getPos().getY(), altar.getPos().getZ(), SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    altar.setInputStack(ItemStack.EMPTY);
                    return true;
                }
            }
        }
        return false;
    }
}
