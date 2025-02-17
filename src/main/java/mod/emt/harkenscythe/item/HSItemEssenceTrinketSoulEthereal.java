package mod.emt.harkenscythe.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import mod.emt.harkenscythe.config.HSConfig;

public class HSItemEssenceTrinketSoulEthereal extends HSItemEssenceTrinketSoul
{
    public HSItemEssenceTrinketSoulEthereal()
    {
        super();
        setMaxDamage(HSConfig.ITEMS.soulTrinketEtherealEssenceCapacity);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}
