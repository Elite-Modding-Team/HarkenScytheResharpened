package mod.emt.harkenscythe.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import mod.emt.harkenscythe.HarkenScythe;

public class HSPotionBleeding extends Potion
{
    public HSPotionBleeding(String name)
    {
        super(true, 0x8B0000);
        setPotionName("effect." + HarkenScythe.MOD_ID + "." + name);
        setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
        entity.attackEntityFrom(DamageSource.MAGIC, 1.0F);
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration % 20 == 0;
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
