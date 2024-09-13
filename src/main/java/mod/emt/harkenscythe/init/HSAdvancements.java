package mod.emt.harkenscythe.init;

import net.minecraft.util.ResourceLocation;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.advancement.HSAdvancementTrigger;

public class HSAdvancements
{
    public static final HSAdvancementTrigger USE_BLOOD_ALTAR = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "use_blood_altar"));
    public static final HSAdvancementTrigger USE_SOUL_ALTAR = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "use_soul_altar"));
    public static final HSAdvancementTrigger USE_NECRONOMICON = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "use_necronomicon"));
    public static final HSAdvancementTrigger ENCOUNTER_ECTOGLOBIN = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "encounter_ectogoblin"));
    public static final HSAdvancementTrigger ENCOUNTER_HEMOGLOBIN = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "encounter_hemogoblin"));
    public static final HSAdvancementTrigger ENCOUNTER_HARBINGER = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "encounter_harbinger"));
    public static final HSAdvancementTrigger ENCOUNTER_SPECTRAL_HUMAN = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "encounter_spectral_human"));
    public static final HSAdvancementTrigger ENCOUNTER_SPECTRAL_MINER = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "encounter_spectral_miner"));
    public static final HSAdvancementTrigger ENCOUNTER_SPECTRAL_MOB = new HSAdvancementTrigger(new ResourceLocation(HarkenScythe.MOD_ID, "encounter_spectral_mob"));

    protected static final HSAdvancementTrigger[] TRIGGER_ARRAY = new HSAdvancementTrigger[]
        {
            USE_BLOOD_ALTAR,
            USE_SOUL_ALTAR,
            USE_NECRONOMICON,
            ENCOUNTER_ECTOGLOBIN,
            ENCOUNTER_HEMOGLOBIN,
            ENCOUNTER_HARBINGER,
            ENCOUNTER_SPECTRAL_HUMAN,
            ENCOUNTER_SPECTRAL_MINER,
            ENCOUNTER_SPECTRAL_MOB
        };
}
