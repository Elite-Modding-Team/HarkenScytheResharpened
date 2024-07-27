package mod.emt.harkenscythe.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSItems;

public class HSItemEssenceKeeperBlood extends HSItemEssenceKeeper
{
    public HSItemEssenceKeeperBlood()
    {
        super();
        setMaxDamage(HSConfig.ITEMS.bloodKeeperEssenceCapacity);
        addPropertyOverride(new ResourceLocation(HarkenScythe.MOD_ID, "level"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
            {
                return 1.0F - ((float) getDamage(stack) / getMaxDamage(stack));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.getItemDamage() == 0;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        if (!hasContainerItem(stack))
        {
            return ItemStack.EMPTY;
        }
        return new ItemStack(HSItems.essence_keeper);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }
}
