package mod.emt.harkenscythe.item.armor.bauble;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import mod.emt.harkenscythe.item.HSItem;

public class HSBaublesItem extends HSItem implements IBauble
{
    BaubleType type;
    EnumRarity rarity;
    SoundEvent equipSound;
    SoundEvent unequipSound;
    
    public HSBaublesItem(EnumRarity rarity, BaubleType type, SoundEvent equipSound, SoundEvent unequipSound)
    {
        super(rarity);
        this.maxStackSize = 1;
        this.type = type;
        this.rarity = rarity;
        this.equipSound = equipSound;
        this.unequipSound = unequipSound;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack)
    {
        return type;
    }
    
    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player)
    {
        player.world.playSound(null, player.posX, player.posY, player.posZ, equipSound, SoundCategory.PLAYERS, 0.8F, 1.0F);
    }
    
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
    {
        player.world.playSound(null, player.posX, player.posY, player.posZ, unequipSound, SoundCategory.PLAYERS, 0.8F, 1.0F);
    }
}
