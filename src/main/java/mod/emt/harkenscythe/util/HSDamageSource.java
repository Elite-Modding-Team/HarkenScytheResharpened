package mod.emt.harkenscythe.util;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class HSDamageSource extends EntityDamageSource
{
    private final String damageType;

    public HSDamageSource(String damageType, @Nullable Entity damageSourceEntity)
    {
        super(damageType, damageSourceEntity);
        this.damageType = damageType;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBase)
    {
        return new TextComponentTranslation("message.harkenscythe." + damageType, entityLivingBase.getDisplayName());
    }
}
