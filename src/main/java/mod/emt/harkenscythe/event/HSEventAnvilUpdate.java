package mod.emt.harkenscythe.event;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.item.HSItem;
import mod.emt.harkenscythe.item.tool.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventAnvilUpdate
{
    // Unbreaking still applies to items on anvils regardless of whether the items don't accept it in enchantment tables or not
    // This event should fix that
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event)
    {
        if (event.getLeft().isEmpty() || event.getRight().isEmpty())
        {
            return;
        }

        if (event.getLeft().getItem() instanceof HSItem || event.getLeft().getItem() instanceof HSToolAthame || event.getLeft().getItem() instanceof HSToolVampireKnife || event.getLeft().getItem() instanceof HSToolBloodButcherer)
        {
            if (EnchantmentHelper.getEnchantments(event.getRight()).keySet().stream().anyMatch(e -> e == Enchantments.UNBREAKING))
            {
                event.setCanceled(true);
            }
        }
    }
}
