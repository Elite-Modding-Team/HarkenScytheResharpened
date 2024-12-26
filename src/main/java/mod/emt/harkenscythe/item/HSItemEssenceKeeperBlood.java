package mod.emt.harkenscythe.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.compat.bloodmagic.HSBloodMagicPlugin;
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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        return Loader.isModLoaded("bloodmagic") ? HSBloodMagicPlugin.onBloodContainerRightClick(world, player, hand) : super.onItemRightClick(world, player, hand);
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

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return 9443858;
    }
}
