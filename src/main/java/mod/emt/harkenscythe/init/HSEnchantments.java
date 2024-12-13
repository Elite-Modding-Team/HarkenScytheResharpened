package mod.emt.harkenscythe.init;

import net.minecraftforge.fml.common.registry.GameRegistry;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.enchantment.*;

@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSEnchantments
{
    public static final HSEnchantmentBlight BLIGHT = new HSEnchantmentBlight("blight");
    public static final HSEnchantmentBloodletting BLOODLETTING = new HSEnchantmentBloodletting("bloodletting");
    public static final HSEnchantmentExude EXUDE = new HSEnchantmentExude("exude");
    public static final HSEnchantmentHemorrhage HEMORRHAGE = new HSEnchantmentHemorrhage("hemorrhage");
    public static final HSEnchantmentNourishment NOURISHMENT = new HSEnchantmentNourishment("nourishment");
    public static final HSEnchantmentSoulsteal SOULSTEAL = new HSEnchantmentSoulsteal("soulsteal");
}
