package mod.emt.harkenscythe.item;

import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class HSItemFood extends ItemFood
{
    private final EnumRarity rarity;
    private final boolean alwaysEdible;
    private int itemUseDuration;

    public HSItemFood(int amount, float saturation, boolean isWolfFood, boolean alwaysEdible, EnumRarity rarity)
    {
        super(amount, saturation, isWolfFood);
        this.alwaysEdible = alwaysEdible;
        this.rarity = rarity;
    }

    public HSItemFood(int amount, float saturation, boolean isWolfFood, int eatingSpeed, boolean alwaysEdible, EnumRarity rarity)
    {
        super(amount, saturation, isWolfFood);
        this.alwaysEdible = alwaysEdible;
        this.itemUseDuration = eatingSpeed; // 32 by default
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (this == HSItems.soul_cookie)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 4 * 20, 0));
        }

        super.onFoodEaten(stack, world, player);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        if (itemUseDuration == 0)
        {
            return 32;
        }

        return itemUseDuration;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (player.canEat(this.alwaysEdible))
        {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
    }
}
