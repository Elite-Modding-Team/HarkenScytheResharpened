package mod.emt.harkenscythe.recipe;

import net.minecraft.item.ItemStack;

public class SoulAltarRecipe
{
    private final ItemStack input;
    private final ItemStack output;
    private final int requiredSouls;

    public SoulAltarRecipe(ItemStack input, ItemStack output, int requiredSouls)
    {
        this.input = input;
        this.output = output;
        this.requiredSouls = requiredSouls;
    }

    public ItemStack getInput()
    {
        return input;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    public int getRequiredSouls()
    {
        return requiredSouls;
    }
}
