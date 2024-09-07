package mod.emt.harkenscythe.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.tileentity.HSTileEntitySoulAltar;

public class HSBlockSoulAltar extends HSBlockAltar
{
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new HSTileEntitySoulAltar();
    }

    @Override
    protected int getRequiredEssence(ItemStack inputStack)
    {
        return HSAltarRecipes.getRequiredSouls(inputStack);
    }

    @Override
    protected ItemStack getOutput(Item inputItem)
    {
        return HSAltarRecipes.getOutputSoul(inputItem);
    }

    @Override
    protected SoundEvent getSoundEvent()
    {
        return HSSoundEvents.BLOCK_SOUL_ALTAR_ENCHANT.getSoundEvent();
    }

    @Override
    protected SoundEvent getSoundEventFail()
    {
        return HSSoundEvents.BLOCK_SOUL_ALTAR_ENCHANT_FAIL.getSoundEvent();
    }
}
