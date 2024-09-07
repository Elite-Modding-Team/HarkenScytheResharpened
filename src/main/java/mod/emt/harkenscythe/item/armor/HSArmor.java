package mod.emt.harkenscythe.item.armor;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSItems;

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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flag)
    {
        if ((this == HSItems.bloodweave_hood) || (this == HSItems.bloodweave_robe) || (this == HSItems.bloodweave_pants) || (this == HSItems.bloodweave_shoes))
        {
            tooltip.add(new TextComponentTranslation("setbonus.harkenscythe").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add(" " + new TextComponentTranslation("setbonus.harkenscythe.bloodweave").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            tooltip.add("");
            tooltip.add(new TextComponentTranslation("tooltip.harkenscythe.no_break_blood").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add("");
        }

        if ((this == HSItems.soulweave_hood) || (this == HSItems.soulweave_robe) || (this == HSItems.soulweave_pants) || (this == HSItems.soulweave_shoes))
        {
            tooltip.add(new TextComponentTranslation("setbonus.harkenscythe").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add(" " + new TextComponentTranslation("setbonus.harkenscythe.soulweave").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            tooltip.add("");
            tooltip.add(new TextComponentTranslation("tooltip.harkenscythe.no_break_soul").setStyle(new Style().setColor(TextFormatting.GRAY)).getFormattedText());
            tooltip.add("");
        }
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
