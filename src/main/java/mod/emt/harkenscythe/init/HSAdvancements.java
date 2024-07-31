package mod.emt.harkenscythe.init;

import net.minecraft.util.ResourceLocation;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.advancement.HSAdvancementTrigger;

public class HSAdvancements
{
    public static final HSAdvancementTrigger USE_BLOOD_ALTAR = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "use_blood_altar"));
    public static final HSAdvancementTrigger USE_SOUL_ALTAR = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "use_soul_altar"));
    protected static final HSAdvancementTrigger[] TRIGGER_ARRAY = new HSAdvancementTrigger[] {USE_BLOOD_ALTAR, USE_SOUL_ALTAR};
}
