package mod.emt.harkenscythe.item.armor;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("deprecation")
public class HSArmor extends ItemArmor
{
    private final EnumRarity rarity;

    public HSArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, EnumRarity rarity)
    {
        super(material, renderIndex, equipmentSlot);
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        if (stack.getItem() == HSItems.livingmetal_leggings)
        {
            return new ResourceLocation(HarkenScythe.MOD_ID, "textures/models/armor/livingmetal_2.png").toString();
        }

        if (stack.getItem() == HSItems.biomass_helmet || stack.getItem() == HSItems.biomass_chestplate || stack.getItem() == HSItems.biomass_boots)
        {
            return new ResourceLocation(HarkenScythe.MOD_ID, "textures/models/armor/biomass_1.png").toString();
        }

        if (stack.getItem() == HSItems.biomass_leggings)
        {
            return new ResourceLocation(HarkenScythe.MOD_ID, "textures/models/armor/biomass_2.png").toString();
        }

        return new ResourceLocation(HarkenScythe.MOD_ID, "textures/models/armor/livingmetal_1.png").toString();
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        if (this.getArmorMaterial() == HSItems.ARMOR_BLOODWEAVE || this.getArmorMaterial() == HSItems.ARMOR_SOULWEAVE)
        {
            super.setDamage(stack, Math.min(stack.getMaxDamage() - 1, damage));
        }
        else
        {
            super.setDamage(stack, damage);
        }
    }
}
