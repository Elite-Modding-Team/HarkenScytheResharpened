package mod.emt.harkenscythe.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.HSEntityHemoglobin;
import mod.emt.harkenscythe.init.HSEnumFaction;

public class HSBlockBloodCrucible extends HSBlockCrucible
{
    public HSBlockBloodCrucible()
    {
        super(MapColor.RED);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (HSConfig.ENTITIES.globinCrucibleSpawning && !world.isRemote && state.getValue(LEVEL) > 0)
        {
            HSEntityHemoglobin hemoglobin = new HSEntityHemoglobin(world);
            int size = 2;
            if ((double) state.getValue(LEVEL) / MAX_LEVEL < 0.3D)
            {
                size = 1;
            }
            else if ((double) state.getValue(LEVEL) / MAX_LEVEL > 0.6D)
            {
                size = 3;
            }
            hemoglobin.setSize(size, true);
            hemoglobin.setPosition(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(hemoglobin);
        }
    }

    @Override
    protected HSEnumFaction getFaction()
    {
        return HSEnumFaction.BLOOD;
    }
}
