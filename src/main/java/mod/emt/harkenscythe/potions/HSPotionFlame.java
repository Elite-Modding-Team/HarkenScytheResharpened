package mod.emt.harkenscythe.potions;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HSPotionFlame extends Potion
{
    public HSPotionFlame(String name)
    {
        super(false, 0xE49A3A);
        setPotionName("effect." + HarkenScythe.MOD_ID + "." + name);
        setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public boolean shouldRender(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(PotionEffect effect)
    {
        return false;
    }
}
