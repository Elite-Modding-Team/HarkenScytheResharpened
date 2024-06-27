package mod.emt.harkenscythe.tileentities;

import mod.emt.harkenscythe.init.HSAltarRecipes;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class HSTileEntityBloodAltar extends HSTileEntityAltar implements ITickable
{
    private final ItemStack essenceStack = new ItemStack(HSItems.blood_essence);
    protected int bloodCount;
    protected boolean validRecipe;

    public boolean isValidRecipe()
    {
        return validRecipe;
    }

    public ItemStack getEssenceStack()
    {
        return essenceStack;
    }

    public int getBloodCount()
    {
        return bloodCount;
    }

    @Override
    public void update()
    {
        super.update();
        if (!this.getInputStack().isEmpty())
        {
            updateBloodCount();
            updateRecipe();
        }
    }

    @Override
    public void updateRecipe()
    {
        this.validRecipe = HSAltarRecipes.isValidInputBlood(this.getInputStack().getItem()) && HSAltarRecipes.getRequiredBlood(this.getInputStack().getItem()) <= this.bloodCount;
    }

    public void updateBloodCount()
    {
        this.bloodCount = scanCrucibleEssenceCounts();
    }
}
