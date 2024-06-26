package mod.emt.harkenscythe.potions;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HSPotionAffliction extends Potion
{
    public HSPotionAffliction(String name)
    {
        super(true, 0x484D48);
        setPotionName("effect." + HarkenScythe.MOD_ID + "." + name);
        setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier)
    {
        Potion[] negativeEffects = new Potion[] {MobEffects.POISON, MobEffects.WEAKNESS, MobEffects.SLOWNESS, MobEffects.MINING_FATIGUE, MobEffects.BLINDNESS};
        for (Potion negativeEffect : negativeEffects)
        {
            if (entityLivingBase.isPotionActive(negativeEffect)) return;
        }
        entityLivingBase.addPotionEffect(new PotionEffect(negativeEffects[entityLivingBase.getRNG().nextInt(negativeEffects.length)], 600));
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
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
