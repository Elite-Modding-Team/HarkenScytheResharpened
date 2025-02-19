package mod.emt.harkenscythe.client.sound;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.tileentity.HSTileEntityAbsorber;

@SideOnly(Side.CLIENT)
public abstract class HSSoundAbsorber extends PositionedSound implements ITickableSound
{
    protected HSTileEntityAbsorber absorber;
    protected BlockPos position;

    protected HSSoundAbsorber(SoundEvent sndEvt, HSTileEntityAbsorber absorber, float volume)
    {
        super(sndEvt.getSoundName(), SoundCategory.BLOCKS);
        this.repeat = true;
        this.absorber = absorber;
        this.volume = volume;
        this.position = this.absorber.getPos();
        this.xPosF = position.getX();
        this.yPosF = position.getY();
        this.zPosF = position.getZ();
    }

    @Override
    public void update()
    {
        if (this.absorber.isInvalid() || !this.absorber.isActive())
        {
            this.volume -= 0.05F;
        }
    }

    @Override
    public boolean isDonePlaying()
    {
        return this.volume <= 0.0F;
    }
}
