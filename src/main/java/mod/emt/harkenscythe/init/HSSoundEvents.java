package mod.emt.harkenscythe.init;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSSoundEvents
{
    public static final SoundEvent ITEM_ATHAME_CREATE = new SoundEvent(new ResourceLocation(HarkenScythe.MOD_ID, "item.athame.create"));
}
