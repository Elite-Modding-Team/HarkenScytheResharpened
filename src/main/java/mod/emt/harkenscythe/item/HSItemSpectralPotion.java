package mod.emt.harkenscythe.item;

import mod.emt.harkenscythe.entity.HSEntitySpectralPotion;
import mod.emt.harkenscythe.init.HSSoundEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HSItemSpectralPotion extends HSItem
{
    private final PotionEffect potionEffect;

    public HSItemSpectralPotion(EnumRarity rarity, Potion potion)
    {
        super(rarity);
        this.setMaxStackSize(1);
        this.potionEffect = new PotionEffect(potion, 600);
    }

    public PotionEffect getPotionEffect()
    {
        return potionEffect;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!player.isCreative())
        {
            itemStack.shrink(1);
        }

        world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.ITEM_POTION_THROW, SoundCategory.NEUTRAL, 0.65F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
            HSEntitySpectralPotion spectralPotion = new HSEntitySpectralPotion(world, player, itemStack);
            spectralPotion.shoot(player, player.rotationPitch, player.rotationYaw, -20.0F, 0.7F, 1.0F);
            world.spawnEntity(spectralPotion);
        }

        player.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
