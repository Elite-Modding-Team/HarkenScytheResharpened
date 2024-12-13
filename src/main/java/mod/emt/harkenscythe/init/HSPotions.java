package mod.emt.harkenscythe.init;

import net.minecraftforge.fml.common.registry.GameRegistry;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.potion.*;

@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSPotions
{
    public static final HSPotionAffliction AFFLICTION = new HSPotionAffliction("affliction");
    public static final HSPotionFlame FLAME = new HSPotionFlame("flame");
    public static final HSPotionBleeding BLEEDING = new HSPotionBleeding("bleeding");
    public static final HSPotionPurifying PURIFYING = new HSPotionPurifying("purifying");
    public static final HSPotionWater WATER = new HSPotionWater("water");
}
