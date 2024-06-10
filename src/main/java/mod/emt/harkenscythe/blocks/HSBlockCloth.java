package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.init.HSBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSBlockCloth extends Block
{
    public HSBlockCloth(MapColor mapColor)
    {
        super(Material.CLOTH, mapColor);
        this.setHardness(0.8F);
        this.setSoundType(SoundType.CLOTH);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return this == HSBlocks.bloodweave_cloth || this == HSBlocks.soulweave_cloth;
    }
}
