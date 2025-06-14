package mod.emt.harkenscythe.item.armor.bauble;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.SoundEvent;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import mod.emt.harkenscythe.init.HSAttributes;

public class HSBaublesItemEssenceAlteration extends HSBaublesAttributeItem implements IBauble
{
    public HSBaublesItemEssenceAlteration(EnumRarity rarity, BaubleType type, SoundEvent equipSound, SoundEvent unequipSound) {
        super(rarity, type, equipSound, unequipSound);
        
		this.attributeMap.put(HSAttributes.ESSENCE_ALTERATION.getName(), new AttributeModifier(HSAttributes.ESSENCE_ALTERATION_ID, "essence alteration bauble", 0.035D, 1));
    }
}
