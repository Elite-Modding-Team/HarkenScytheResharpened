package mod.emt.harkenscythe.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: Spectral Glass can be passed through during the day but is solid during the night
// There should be a way to invert it (sneak + right-click maybe?) which changes the texture and reverses the roles (solid at day and not solid at night)
// Panes are also still needed
@SuppressWarnings("deprecation")
public class HSSpectralGlass extends Block
{
    public HSSpectralGlass()
    {
        super(Material.GLASS, Material.GLASS.getMaterialMapColor());
        this.setHardness(3.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setResistance(15.0F);
        this.setSoundType(SoundType.GLASS);
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        
        if (blockState != iblockstate)
        {
            return true;
        }

        if (block == this)
        {
            return false;
        }
        
        return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
