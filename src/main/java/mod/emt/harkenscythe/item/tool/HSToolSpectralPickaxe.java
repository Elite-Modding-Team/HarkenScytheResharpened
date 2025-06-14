package mod.emt.harkenscythe.item.tool;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;

import mod.emt.harkenscythe.init.HSMaterials;
import mod.emt.harkenscythe.init.HSAttributes;

public class HSToolSpectralPickaxe extends HSToolPickaxe implements IHSTool
{
    public AttributeModifier reach;

    public HSToolSpectralPickaxe()
    {
        super(HSMaterials.TOOL_SPECTRAL, EnumRarity.EPIC);
        this.reach = new AttributeModifier(HSAttributes.REACH_DISTANCE_ID, "reach distance spectral pickaxe", 3.0F, 0);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(EntityPlayer.REACH_DISTANCE.getName(), this.reach);
        }

        return multimap;
    }
}
