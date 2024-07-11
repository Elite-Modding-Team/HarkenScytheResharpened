package mod.emt.harkenscythe.init;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.enchantment.HSEnchantmentBloodletting;
import mod.emt.harkenscythe.enchantment.HSEnchantmentExude;
import mod.emt.harkenscythe.enchantment.HSEnchantmentNourishment;
import mod.emt.harkenscythe.enchantment.HSEnchantmentSoulsteal;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSEnchantments
{
    public static final HSEnchantmentBloodletting BLOODLETTING = new HSEnchantmentBloodletting("bloodletting");
    public static final HSEnchantmentExude EXUDE = new HSEnchantmentExude("exude");
    public static final HSEnchantmentNourishment NOURISHMENT = new HSEnchantmentNourishment("nourishment");
    public static final HSEnchantmentSoulsteal SOULSTEAL = new HSEnchantmentSoulsteal("soulsteal");
}
