package mod.emt.harkenscythe.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.HSEntityEctoglobin;
import mod.emt.harkenscythe.init.HSItems;

public class HSBlockSoulCrucible extends HSBlockCrucible
{
    public HSBlockSoulCrucible()
    {
        super(MapColor.BLACK);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (HSConfig.ENTITIES.globinCrucibleSpawning && !world.isRemote && state.getValue(LEVEL) > 0)
        {
            HSEntityEctoglobin ectoglobin = new HSEntityEctoglobin(world);
            int size = 2;
            if ((double) state.getValue(LEVEL) / MAX_LEVEL < 0.3D)
            {
                size = 1;
            }
            else if ((double) state.getValue(LEVEL) / MAX_LEVEL > 0.6D)
            {
                size = 3;
            }
            ectoglobin.setSize(size, true);
            ectoglobin.setPosition(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(ectoglobin);
        }
    }

    @Override
    protected Item getEssenceKeeper()
    {
        return HSItems.essence_keeper_soul;
    }

    @Override
    protected Item getEssenceVessel()
    {
        return HSItems.essence_vessel_soul;
    }

    @Override
    protected Item getEssenceItem()
    {
        return HSItems.soul_essence;
    }
}
