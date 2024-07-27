package mod.emt.harkenscythe.recipe;

import net.minecraft.item.ItemStack;

public class HSRecipeBloodAltar
{
    private final ItemStack input;
    private final ItemStack output;
    private final int requiredBlood;

    public HSRecipeBloodAltar(ItemStack input, ItemStack output, int requiredBlood)
    {
        this.input = input;
        this.output = output;
        this.requiredBlood = requiredBlood;
    }

    public ItemStack getInput()
    {
        return input;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    public int getRequiredBlood()
    {
        return requiredBlood;
    }
}
