package mod.emt.harkenscythe.items;

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

public class HSFood extends ItemFood
{
    public int itemUseDuration;
    public boolean alwaysEdible;
    private final EnumRarity rarity;

    public HSFood(int amount, float saturation, boolean isWolfFood, boolean alwaysEdible, EnumRarity rarity)
    {
        super(amount, saturation, isWolfFood);
        this.alwaysEdible = alwaysEdible;
        this.rarity = rarity;
    }

    public HSFood(int amount, float saturation, boolean isWolfFood, int eatingSpeed, boolean alwaysEdible, EnumRarity rarity)
    {
        super(amount, saturation, isWolfFood);
        this.alwaysEdible = alwaysEdible;
        this.itemUseDuration = eatingSpeed; // 32 by default
        this.rarity = rarity;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        if (itemUseDuration == 0) {
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
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (this == HSItems.soul_cookie)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 4 * 20, 0));
        }

        super.onFoodEaten(stack, world, player);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }
}
