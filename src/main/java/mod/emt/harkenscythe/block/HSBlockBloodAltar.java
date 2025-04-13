package mod.emt.harkenscythe.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSEnumFaction;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.tileentity.HSTileEntityBloodAltar;

public class HSBlockBloodAltar extends HSBlockAltar
{
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new HSTileEntityBloodAltar();
    }

    @Override
    protected int getRequiredEssence(ItemStack inputStack)
    {
        return HSAltarRecipes.getRequiredBlood(inputStack);
    }

    @Override
    protected ItemStack getOutput(Item inputItem)
    {
        return HSAltarRecipes.getOutputBlood(inputItem);
    }

    @Override
    protected SoundEvent getSoundEvent()
    {
        return HSSoundEvents.BLOCK_BLOOD_ALTAR_SUCCESS.getSoundEvent();
    }

    @Override
    protected SoundEvent getSoundEventFail()
    {
        return HSSoundEvents.BLOCK_BLOOD_ALTAR_FAIL.getSoundEvent();
    }

    @Override
    protected HSEnumFaction getFaction()
    {
        return HSEnumFaction.BLOOD;
    }
}
