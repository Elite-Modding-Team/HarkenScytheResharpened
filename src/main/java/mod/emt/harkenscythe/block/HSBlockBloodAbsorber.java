package mod.emt.harkenscythe.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import mod.emt.harkenscythe.tileentity.HSTileEntityBloodAbsorber;

public class HSBlockBloodAbsorber extends HSBlockAbsorber
{
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new HSTileEntityBloodAbsorber();
    }
}
