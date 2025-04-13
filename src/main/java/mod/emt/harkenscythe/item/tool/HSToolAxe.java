package mod.emt.harkenscythe.item.tool;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.emt.harkenscythe.init.HSMaterials;

@SuppressWarnings("deprecation")
public class HSToolAxe extends ItemAxe implements IHSTool
{
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolAxe(ToolMaterial material, float damage, float speed, EnumRarity rarity)
    {
        super(material, damage, speed);
        this.rarity = rarity;
        this.material = material;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (stack.isItemDamaged() && entity != null && this.getToolMaterial() == HSMaterials.TOOL_BIOMASS && entity.ticksExisted % 40 == 0)
        {
            stack.attemptDamageItem(-1, world.rand, entity instanceof EntityPlayerMP ? (EntityPlayerMP) entity : null);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public ToolMaterial getToolMaterial()
    {
        return this.material;
    }
}
