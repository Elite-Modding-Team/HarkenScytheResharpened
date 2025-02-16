package mod.emt.harkenscythe.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import mod.emt.harkenscythe.config.HSConfig;

public class HSItemEssenceTrinketBloodEthereal extends HSItemEssenceTrinketBlood
{
    public HSItemEssenceTrinketBloodEthereal()
    {
        super();
        setMaxDamage(HSConfig.ITEMS.bloodTrinketEtherealEssenceCapacity);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}
