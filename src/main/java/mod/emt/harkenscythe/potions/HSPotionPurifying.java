package mod.emt.harkenscythe.potions;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class HSPotionPurifying extends Potion
{
    public HSPotionPurifying(String name)
    {
        super(false, 0xFFFFFF);
        setPotionName("effect." + HarkenScythe.MOD_ID + "." + name);
        setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier)
    {
        if (entityLivingBase.isEntityUndead())
        {
            entityLivingBase.attackEntityFrom(DamageSource.MAGIC, 4.0F);
        }
        else
        {
            for (PotionEffect effect : entityLivingBase.getActivePotionEffects())
            {
                if (effect.getPotion().isBadEffect() && !effect.getCurativeItems().isEmpty()) entityLivingBase.removePotionEffect(effect.getPotion());
            }
        }
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
