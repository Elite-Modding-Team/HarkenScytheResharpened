package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.tileentities.HSTileEntityAltar;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class HSAltar extends BlockEnchantmentTable
{
    public HSAltar()
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
        if (te instanceof HSTileEntityAltar)
        {
            HSTileEntityAltar altar = (HSTileEntityAltar) te;
            altar.dropItem();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HSTileEntityAltar)
        {
            HSTileEntityAltar altar = (HSTileEntityAltar) te;
            int altarX = altar.getPos().getX();
            int altarY = altar.getPos().getY();
            int altarZ = altar.getPos().getZ();
            ItemStack altarStack = altar.getInputStack();
            ItemStack heldStack = player.getHeldItem(hand);

            if (heldStack.getItem() == HSItems.harken_athame)
            {
                if (!altarStack.isEmpty() && altar.getValidRecipe())
                {
                    handleRecipe(world, altar, altarStack, player, altarX, altarY, altarZ);
                    return true;
                }
            }
            else if (!heldStack.isEmpty())
            {
                if (altarStack.isEmpty())
                {
                    altar.setInputStack(heldStack.splitStack(1));
                    player.world.playSound(altarX, altarY, altarZ, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    return true;
                }
                else if (altarStack.getMaxStackSize() > altarStack.getCount() && ItemStack.areItemsEqual(altarStack, heldStack) && ItemStack.areItemStackTagsEqual(altarStack, heldStack))
                {
                    heldStack.shrink(1);
                    player.world.playSound(altarX, altarY, altarZ, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    altarStack.grow(1);
                    return true;
                }
            }
            else
            {
                ItemStack itemStack = altar.getInputStack();
                if (!itemStack.isEmpty())
                {
                    player.addItemStackToInventory(itemStack);
                    player.world.playSound(altarX, altarY, altarZ, SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    altar.setInputStack(ItemStack.EMPTY);
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract int getRequiredEssence(ItemStack inputStack);

    protected abstract ItemStack getOutput(Item inputItem);

    protected abstract SoundEvent getSoundEvent();

    protected void handleRecipe(World world, HSTileEntityAltar altar, ItemStack altarStack, EntityPlayer player, int altarX, int altarY, int altarZ)
    {
        int requiredEssence = getRequiredEssence(altar.getInputStack());
        altar.decreaseCrucibleEssenceCount(requiredEssence);

        if (altarStack.isItemDamaged())
        {
            altarStack.setItemDamage(altarStack.getItemDamage() - requiredEssence);
        }
        else
        {
            if (!player.world.isRemote)
            {
                player.world.spawnEntity(new EntityItem(player.world, altarX + 0.5D, altarY + 1.5D, altarZ + 0.5D, getOutput(altarStack.getItem())));
            }
            altar.getInputStack().shrink(1);
        }

        player.world.playSound(altarX, altarY, altarZ, getSoundEvent(), SoundCategory.BLOCKS, 0.8F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
        player.world.playSound(altarX, altarY, altarZ, SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);

        for (int i = 0; i < requiredEssence; i++)
        {
            player.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, altarX + world.rand.nextFloat(), altarY + 1.0D + world.rand.nextFloat(), altarZ + world.rand.nextFloat(), 0.0D, 0.5D, 0.0D);
        }

        altar.setEssenceCount(altar.scanCrucibleEssenceCounts());
        altar.getValidRecipe();
    }
}
