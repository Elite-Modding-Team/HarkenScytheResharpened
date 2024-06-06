package mod.emt.harkenscythe.items;

import javax.annotation.Nullable;
import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HSEssenceKeeperSoul extends HSEssenceKeeper
{
    public HSEssenceKeeperSoul()
    {
        super();
        setMaxDamage(20);
        addPropertyOverride(new ResourceLocation(HarkenScythe.MOD_ID, "level"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
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
}
