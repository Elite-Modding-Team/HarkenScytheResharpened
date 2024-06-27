package mod.emt.harkenscythe.tileentities;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class HSTileEntitySoulAltar extends HSTileEntityAltar implements ITickable
{
    private final ItemStack essenceStack = new ItemStack(HSItems.soul_essence);
    protected int soulCount;
    protected boolean validRecipe;

    public boolean isValidRecipe()
    {
        return validRecipe;
    }

    public ItemStack getEssenceStack()
    {
        return essenceStack;
    }

    public int getSoulCount()
    {
        return soulCount;
    }

    @Override
    public void update()
    {
        super.update();
        if (!this.getInputStack().isEmpty())
        {
            updateSoulCount();
            updateRecipe();
        }
    }

    @Override
    public void updateRecipe()
    {
        this.validRecipe = HSAltarRecipes.isValidInputSoul(this.getInputStack().getItem()) && HSAltarRecipes.getRequiredSouls(this.getInputStack().getItem()) <= this.soulCount;
    }

    public void updateSoulCount()
    {
        this.soulCount = scanCrucibleEssenceCounts();
    }
}
