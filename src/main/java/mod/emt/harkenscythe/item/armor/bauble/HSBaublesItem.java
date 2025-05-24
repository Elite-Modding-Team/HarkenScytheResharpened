package mod.emt.harkenscythe.item.armor.bauble;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItem;

public class HSBaublesItem extends HSItem implements IBauble
{
    BaubleType type;
    EnumRarity rarity;
    
    public HSBaublesItem(EnumRarity rarity, BaubleType type)
    {
        super(rarity);
        this.maxStackSize = 1;
        this.type = type;
        this.rarity = rarity;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack)
    {
        return type;
    }
    
    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player)
    {
        player.world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.EQUIP_BAUBLE.getSoundEvent(), SoundCategory.PLAYERS, 0.8F, 1.0F);
    }
    
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
    {
        player.world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.UNEQUIP_BAUBLE.getSoundEvent(), SoundCategory.PLAYERS, 0.8F, 1.0F);
    }
}
