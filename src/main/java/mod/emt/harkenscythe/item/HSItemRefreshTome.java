package mod.emt.harkenscythe.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSItemRefreshTome extends HSItem
{
    public HSItemRefreshTome()
    {
        super(EnumRarity.RARE);
        setMaxDamage(HSConfig.ITEMS.refreshTomeDurability);
        setMaxStackSize(1);
        setNoRepair();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItemDamage() <= stack.getMaxDamage() - (stack.getMaxDamage() / HSConfig.ITEMS.refreshTomeUses))
        {
            // Reroll enchantments in enchanting tables
            if (!world.isRemote)
            {
                player.xpSeed = world.rand.nextInt();
                world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.ITEM_TOME_REFRESH.getSoundEvent(), SoundCategory.PLAYERS, 0.6F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            }

            if (!player.capabilities.isCreativeMode)
            {
                stack.setItemDamage(stack.getItemDamage() + (stack.getMaxDamage() / HSConfig.ITEMS.refreshTomeUses));
            }

            player.sendStatusMessage(new TextComponentTranslation("message.harkenscythe.refresh_tome.refresh"), true);
            player.getCooldownTracker().setCooldown(stack.getItem(), 60);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        else world.playSound(null, player.getPosition(), HSSoundEvents.BLOCK_SOUL_ALTAR_FAIL.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 1.2F));

        if (stack.getItemDamage() > stack.getMaxDamage() - (stack.getMaxDamage() / HSConfig.ITEMS.refreshTomeUses))
        {
            player.sendStatusMessage(new TextComponentTranslation("message.harkenscythe.refresh_tome.no_souls"), true);
            player.getCooldownTracker().setCooldown(stack.getItem(), 60);
        }

        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean isDamageable()
    {
        return false;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        return stack.copy();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return 1872873;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged;
    }
}
