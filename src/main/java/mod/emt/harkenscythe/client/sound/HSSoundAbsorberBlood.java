package mod.emt.harkenscythe.client.sound;

import net.minecraft.client.audio.ITickableSound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.tileentity.HSTileEntityAbsorber;

@SideOnly(Side.CLIENT)
public class HSSoundAbsorberBlood extends HSSoundAbsorber implements ITickableSound
{
    public HSSoundAbsorberBlood(HSTileEntityAbsorber absorber, float volume)
    {
        super(HSSoundEvents.BLOCK_BLOOD_ABSORBER_LOOP.getSoundEvent(), absorber, volume);
    }
}
