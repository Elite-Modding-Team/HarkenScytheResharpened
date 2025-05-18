package mod.emt.harkenscythe.event;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSEnchantments;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.item.HSItem;
import mod.emt.harkenscythe.item.tool.HSToolAthame;
import mod.emt.harkenscythe.item.tool.HSToolBloodButcherer;
import mod.emt.harkenscythe.item.tool.HSToolVampireKnife;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventAnvilUpdate
{
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event)
    {
        // Unbreaking still applies to items on anvils regardless of whether the items accept it in enchantment tables or not
        // This event should fix that
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
        
        // Using Abyssal Fragments with scythes or glaives at an anvil will apply Reaping Frenzy
        if (HSEnchantments.REAPING_FRENZY.canApply(event.getLeft()) && HSConfig.ENCHANTMENTS.reapingFrenzyAnvil && event.getRight().getItem() == HSItems.abyssal_fragment && !EnchantmentHelper.getEnchantments(event.getLeft()).containsKey(HSEnchantments.REAPING_FRENZY))
        {
        	event.setCost(HSConfig.ENCHANTMENTS.reapingFrenzyAnvilCost);
        	event.setOutput(event.getLeft().copy());
        	event.getOutput().addEnchantment(HSEnchantments.REAPING_FRENZY, 1);
        	event.setMaterialCost(1);
        }
    }
}
