package mod.emt.harkenscythe.init;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.enchantments.HSEnchantBloodletting;
import mod.emt.harkenscythe.enchantments.HSEnchantExude;
import mod.emt.harkenscythe.enchantments.HSEnchantNourishment;
import mod.emt.harkenscythe.enchantments.HSEnchantSoulsteal;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSEnchantments
{
    public static final HSEnchantBloodletting BLOODLETTING = new HSEnchantBloodletting("bloodletting");
    public static final HSEnchantExude EXUDE = new HSEnchantExude("exude");
    public static final HSEnchantNourishment NOURISHMENT = new HSEnchantNourishment("nourishment");
    public static final HSEnchantSoulsteal SOULSTEAL = new HSEnchantSoulsteal("soulsteal");
}
