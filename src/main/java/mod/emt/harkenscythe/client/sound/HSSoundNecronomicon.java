package mod.emt.harkenscythe.client.sound;

import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItemNecronomicon;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HSSoundNecronomicon extends PositionedSound implements ITickableSound
{
    protected EntityPlayer player;
    protected BlockPos position;

    public HSSoundNecronomicon(EntityPlayer player)
    {
        super(HSSoundEvents.ITEM_NECRONOMICON_ACTIVE.getSoundEvent().getSoundName(), SoundCategory.PLAYERS);
        this.repeat = true;
        this.player = player;
        this.position = player.getPosition();
        this.xPosF = position.getX();
        this.yPosF = position.getY();
        this.zPosF = position.getZ();
    }

    @Override
    public void update()
    {
        if (!(this.player.getActiveItemStack().getItem() instanceof HSItemNecronomicon))
        {
            this.volume = this.volume - 0.1F;
        }
    }

    @Override
    public boolean isDonePlaying()
    {
        return this.volume <= 0.0F;
    }
}
