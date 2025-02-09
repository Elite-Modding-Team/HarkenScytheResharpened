package mod.emt.harkenscythe.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import mod.emt.harkenscythe.tileentity.HSTileEntitySoulAbsorber;

public class HSBlockSoulAbsorber extends HSBlockAbsorber
{
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new HSTileEntitySoulAbsorber();
    }
}
