package mod.emt.harkenscythe.block;

import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSAdvancements;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.tileentity.HSTileEntityAltar;

public abstract class HSBlockAltar extends BlockEnchantmentTable
{
    protected HSBlockAltar()
    {
        super();
        setHardness(5.0F);
        setResistance(2000.0F);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof HSTileEntityAltar && hand == EnumHand.MAIN_HAND)
        {
            HSTileEntityAltar altar = (HSTileEntityAltar) te;
            int altarX = altar.getPos().getX();
            int altarY = altar.getPos().getY();
            int altarZ = altar.getPos().getZ();
            ItemStack altarStack = altar.getInputStack();
            ItemStack heldStack = player.getHeldItem(hand);

            if (heldStack.getItem() == HSItems.harken_athame)
            {
                if (player.getCooldownTracker().hasCooldown(heldStack.getItem()))
                {
                    return false;
                }
                else if (!altarStack.isEmpty() && altar.getValidRecipe())
                {
                    handleRecipe(world, altar, altarStack, player, altarX, altarY, altarZ);
                    player.getCooldownTracker().setCooldown(heldStack.getItem(), 20);
                    return true;
                }
                else
                {
                    player.sendStatusMessage(new TextComponentTranslation("message.harkenscythe.altar.invalid_recipe"), true);
                }
            }
            else if (!heldStack.isEmpty())
            {
                if (altarStack.isEmpty())
                {
                    altar.setInputStack(heldStack.splitStack(1));
                    world.playSound(altarX, altarY, altarZ, HSSoundEvents.ITEM_ATHAME_CREATE.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.75F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    return true;
                }
                else if (altarStack.getMaxStackSize() > altarStack.getCount() && ItemStack.areItemsEqual(altarStack, heldStack) && ItemStack.areItemStackTagsEqual(altarStack, heldStack))
                {
                    heldStack.shrink(1);
                    world.playSound(altarX, altarY, altarZ, HSSoundEvents.ITEM_ATHAME_CREATE.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.75F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    altarStack.grow(1);
                    return true;
                }
            }
            else
            {
                ItemStack itemStack = altar.getInputStack();
                if (!itemStack.isEmpty())
                {
                    altar.setInputStack(ItemStack.EMPTY);
                    if (!world.isRemote) player.addItemStackToInventory(itemStack);
                    world.playSound(altarX, altarY, altarZ, getSoundEventFail(), SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract int getRequiredEssence(ItemStack inputStack);

    protected abstract ItemStack getOutput(Item inputItem);

    protected abstract SoundEvent getSoundEvent();

    protected abstract SoundEvent getSoundEventFail();

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
            if (!world.isRemote)
            {
                world.spawnEntity(new EntityItem(world, altarX + 0.5D, altarY + 1.5D, altarZ + 0.5D, getOutput(altarStack.getItem())));
            }
            altar.getInputStack().shrink(1);
        }

        world.playSound(altarX, altarY, altarZ, getSoundEvent(), SoundCategory.BLOCKS, 0.8F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);
        world.playSound(altarX, altarY, altarZ, SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.BLOCKS, 1.0F, 1.5F / (altar.getWorld().rand.nextFloat() * 0.4F + 1.2F), false);

        for (int i = 0; i < requiredEssence; i++)
        {
            world.spawnParticle(EnumParticleTypes.SPELL_WITCH, altarX + world.rand.nextFloat(), altarY + 1.0D + world.rand.nextFloat(), altarZ + world.rand.nextFloat(), 0.0D, 0.5D, 0.0D);
        }

        if (player instanceof EntityPlayerMP)
        {
            if (this instanceof HSBlockBloodAltar) HSAdvancements.USE_BLOOD_ALTAR.trigger((EntityPlayerMP) player);
            else HSAdvancements.USE_SOUL_ALTAR.trigger((EntityPlayerMP) player);
        }

        altar.setEssenceCount(altar.scanCrucibleEssenceCounts());
        altar.getValidRecipe();
    }
}
