package mod.emt.harkenscythe.client.sound;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.tileentity.HSTileEntityAltar;
import mod.emt.harkenscythe.tileentity.HSTileEntityBloodAltar;

@SideOnly(Side.CLIENT)
public class HSSoundAltar extends PositionedSound implements ITickableSound
{
    protected HSTileEntityAltar altar;
    protected BlockPos position;

    public HSSoundAltar(HSTileEntityAltar altar, float volume)
    {
        super(getSoundEvent(altar), SoundCategory.BLOCKS);
        this.repeat = true;
        this.altar = altar;
        this.volume = volume;
        this.position = this.altar.getPos();
        this.xPosF = position.getX();
        this.yPosF = position.getY();
        this.zPosF = position.getZ();
    }

    @Override
    public void update()
    {
        if (this.altar.isInvalid() || this.altar.bookSpread < 0.5F)
        {
            this.volume -= 0.02F;
        }
    }

    @Override
    public boolean isDonePlaying()
    {
        return this.volume <= 0.0F;
    }

    private static SoundEvent getSoundEvent(HSTileEntityAltar altar)
    {
        return altar instanceof HSTileEntityBloodAltar ? HSSoundEvents.BLOCK_BLOOD_ALTAR_LOOP.getSoundEvent() : HSSoundEvents.BLOCK_SOUL_ALTAR_LOOP.getSoundEvent();
    }
}
