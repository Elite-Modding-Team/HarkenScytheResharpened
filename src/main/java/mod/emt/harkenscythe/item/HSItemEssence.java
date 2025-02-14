package mod.emt.harkenscythe.item;

import net.minecraft.item.EnumRarity;

public class HSItemEssence extends HSItem
{
    private int essenceValue;

    public HSItemEssence(EnumRarity rarity, int essenceValue)
    {
        super(rarity);
        this.setEssenceValue(essenceValue);
    }

    public int getEssenceValue()
    {
        return this.essenceValue;
    }

    public void setEssenceValue(int essenceValue)
    {
        this.essenceValue = essenceValue;
    }
}
