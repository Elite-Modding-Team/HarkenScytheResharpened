package mod.emt.harkenscythe.item.armor.bauble;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.SoundEvent;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import mod.emt.harkenscythe.init.HSAttributes;

public class HSBaublesItemLifesteal extends HSBaublesAttributeItem implements IBauble
{
    public HSBaublesItemLifesteal(EnumRarity rarity, BaubleType type, SoundEvent equipSound, SoundEvent unequipSound) {
        super(rarity, type, equipSound, unequipSound);
        
		this.attributeMap.put(HSAttributes.LIFESTEAL.getName(), new AttributeModifier(HSAttributes.LIFESTEAL_ID, "lifesteal bauble", 0.035D, 1));
		this.attributeMap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(HSAttributes.ATTACK_DAMAGE_ID, "lifesteal bauble damage", -0.05D, 1));
    }
}
