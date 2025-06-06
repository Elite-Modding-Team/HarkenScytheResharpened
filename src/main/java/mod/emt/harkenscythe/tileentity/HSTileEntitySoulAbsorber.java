package mod.emt.harkenscythe.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.client.sound.HSSoundAbsorberSoul;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.util.HSContainerHelper;

public class HSTileEntitySoulAbsorber extends HSTileEntityAbsorber implements ITickable
{
    @Override
    public Block getCrucibleType()
    {
        return HSBlocks.soul_crucible;
    }

    @Override
    public int scanContainerEssenceCounts(ItemStack container)
    {
        int totalCount = 0;
        if (HSContainerHelper.isSoulFaction(container))
        {
            totalCount = container.getMaxDamage() - container.getItemDamage();
        }
        return totalCount;
    }

    @Override
    public void playStartSound()
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), HSSoundEvents.BLOCK_SOUL_ABSORBER_START.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 1.2F), false);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void playActiveSound()
    {
        Minecraft.getMinecraft().getSoundHandler().playSound(new HSSoundAbsorberSoul(this, 1.0F));
    }

    @Override
    public void playStopSound()
    {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), HSSoundEvents.BLOCK_SOUL_ABSORBER_STOP.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 1.2F), false);
    }
}
