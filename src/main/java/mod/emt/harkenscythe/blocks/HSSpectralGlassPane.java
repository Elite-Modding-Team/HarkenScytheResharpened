package mod.emt.harkenscythe.blocks;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: Spectral Glass can be passed through during the day but is solid during the night
// There should be a way to invert it (sneak + right-click maybe?) which changes the texture and reverses the roles (solid at day and not solid at night)
public class HSSpectralGlassPane extends BlockPane {
    public HSSpectralGlassPane()
    {
        super(Material.GLASS, true);
        this.setHardness(3.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setResistance(15.0F);
        this.setSoundType(SoundType.GLASS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}