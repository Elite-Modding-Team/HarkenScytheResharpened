package mod.emt.harkenscythe.item.armor.bauble;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import java.util.List;
import java.util.Map.Entry;

public class HSBaublesAttributeItem extends HSBaublesItem implements IBauble
{
    protected final Multimap<String, AttributeModifier> attributeMap = HashMultimap.create();

    public HSBaublesAttributeItem(EnumRarity rarity, BaubleType type, SoundEvent equipSound, SoundEvent unequipSound)
    {
        super(rarity, type, equipSound, unequipSound);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player)
    {
        super.onEquipped(itemstack, player);

        if (!player.world.isRemote)
        {
            player.getAttributeMap().applyAttributeModifiers(attributeMap);
        }
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
    {
        super.onUnequipped(itemstack, player);

        if (!player.world.isRemote)
        {
            player.getAttributeMap().removeAttributeModifiers(attributeMap);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add("");
        tooltip.add(new TextComponentTranslation("info.equip").getFormattedText());

        for (Entry<String, AttributeModifier> entry : this.attributeMap.entries())
        {
            double amount = entry.getValue().getAmount();
            String name = "attribute.name." + entry.getKey();

            if (entry.getValue().getOperation() == 1)
            {
                amount *= 100;
            }

            if (amount < 0)
            {
                tooltip.add(" " + new TextComponentTranslation("attribute.modifier.take." + entry.getValue().getOperation(), ItemStack.DECIMALFORMAT.format(Math.abs(amount)), I18n.format(name))
                    .setStyle(new Style().setColor(TextFormatting.RED)).getFormattedText());
            }
            else
            {
                tooltip.add(" " + new TextComponentTranslation("attribute.modifier.plus." + entry.getValue().getOperation(), ItemStack.DECIMALFORMAT.format(Math.abs(amount)), I18n.format(name))
                    .setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            }
        }
    }
}
