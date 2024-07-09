package mod.emt.harkenscythe.items.tools;

import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class HSShears extends ItemShears implements IHSTool
{
    private final EnumRarity rarity;
    private final Ingredient repairMaterial;

    public HSShears(int durability, EnumRarity rarity, Ingredient repairMaterial)
    {
        this.rarity = rarity;
        this.repairMaterial = repairMaterial;
        this.setMaxDamage(durability);
        this.setMaxStackSize(1);
    }

    // Hardcoded blocks...
    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();

        if (material != Material.WEB && material != Material.LEAVES)
        {
            return material == Material.CLOTH ? 5.0F : super.getDestroySpeed(stack, state);
        }
        else
        {
            return 15.0F;
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repairMaterial.test(repair) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public ToolMaterial getToolMaterial()
    {
        return this.repairMaterial == Ingredient.fromStacks(new ItemStack(HSItems.livingmetal_ingot)) ? HSItems.TOOL_LIVINGMETAL : null;
    }
}
