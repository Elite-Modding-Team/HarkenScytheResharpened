package mod.emt.harkenscythe.item.tool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class HSToolAthame extends HSToolSword implements IHSTool
{
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolAthame(ToolMaterial material, EnumRarity rarity)
    {
        super(material, rarity);
        this.rarity = rarity;
        this.material = material;
        setNoRepair();
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        return true;
    }

    @Override
    public boolean isDamageable()
    {
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }

    // Wouldn't make sense to apply these enchantments to an item that doesn't even break and take damage
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        if (enchantment == Enchantments.MENDING || enchantment == Enchantments.UNBREAKING) return false;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public ToolMaterial getToolMaterial()
    {
        return this.material;
    }
}
