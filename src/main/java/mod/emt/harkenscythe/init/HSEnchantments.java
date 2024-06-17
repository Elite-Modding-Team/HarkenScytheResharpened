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
    @GameRegistry.ObjectHolder("bloodletting")
    public static HSEnchantBloodletting bloodletting;
    @GameRegistry.ObjectHolder("exude")
    public static HSEnchantExude exude;
    @GameRegistry.ObjectHolder("nourishment")
    public static HSEnchantNourishment nourishment;
    @GameRegistry.ObjectHolder("soulsteal")
    public static HSEnchantSoulsteal soulsteal;
}
