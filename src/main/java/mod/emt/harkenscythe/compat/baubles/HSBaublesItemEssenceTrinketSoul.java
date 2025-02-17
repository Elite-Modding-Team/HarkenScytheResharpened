package mod.emt.harkenscythe.compat.baubles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import mod.emt.harkenscythe.item.HSItemEssenceTrinketSoul;

public class HSBaublesItemEssenceTrinketSoul extends HSItemEssenceTrinketSoul implements IBauble
{
    public HSBaublesItemEssenceTrinketSoul()
    {
        super();
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack)
    {
        return BaubleType.TRINKET;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase player)
    {
        onUpdate(itemStack, player.getEntityWorld(), player, BaublesApi.isBaubleEquipped((EntityPlayer) player, this), false);
    }
}
