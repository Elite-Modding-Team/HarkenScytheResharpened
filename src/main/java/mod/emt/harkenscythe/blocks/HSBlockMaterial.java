package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.init.HSBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSBlockMaterial extends Block
{
    public HSBlockMaterial(Material material, MapColor mapColor, float hardness, float resistance, SoundType soundType)
    {
        super(material, mapColor);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setSoundType(soundType);
    }
    
	@Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
	{
		if (this == HSBlocks.biomass_block) {
			return true;
		}
		
		return false;
    }
}
