package mod.emt.harkenscythe.item.armor.bauble;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import mod.emt.harkenscythe.init.HSEnumContainerType;
import mod.emt.harkenscythe.init.HSEnumFaction;
import mod.emt.harkenscythe.item.HSItemEssenceContainer;

public class HSBaublesItemEssenceTrinket extends HSItemEssenceContainer implements IBauble
{
    public HSBaublesItemEssenceTrinket(HSEnumContainerType containerType, HSEnumFaction faction)
    {
        super(containerType, faction);
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
