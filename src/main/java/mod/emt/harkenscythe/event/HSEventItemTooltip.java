package mod.emt.harkenscythe.event;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.item.HSItemEssenceKeeperBlood;
import mod.emt.harkenscythe.item.HSItemEssenceKeeperSoul;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSEventItemTooltip
{
    private static final ResourceLocation BLOOD_ALTAR = new ResourceLocation(HarkenScythe.MOD_ID, "blood_altar");
    private static final ResourceLocation SOUL_ALTAR = new ResourceLocation(HarkenScythe.MOD_ID, "soul_altar");
    private static final ResourceLocation BIOMASS_SEED = new ResourceLocation(HarkenScythe.MOD_ID, "biomass_seed");
    private static final ResourceLocation BLUNT_HARKEN_BLADE = new ResourceLocation(HarkenScythe.MOD_ID, "blunt_harken_blade");
    private static final ResourceLocation HARKEN_ATHAME = new ResourceLocation(HarkenScythe.MOD_ID, "harken_athame");
    private static final ResourceLocation LIVINGMETAL_SHEARS = new ResourceLocation(HarkenScythe.MOD_ID, "livingmetal_shears");
    private static final ResourceLocation REAPER_SCYTHE = new ResourceLocation(HarkenScythe.MOD_ID, "reaper_scythe");
    private static final ResourceLocation SOUL_CAKE = new ResourceLocation(HarkenScythe.MOD_ID, "soul_cake");
    private static final ResourceLocation SOUL_COOKIE = new ResourceLocation(HarkenScythe.MOD_ID, "soul_cookie");

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item instanceof HSItemEssenceKeeperBlood)
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.collected_blood") + ": " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
        }
        else if (item instanceof HSItemEssenceKeeperSoul)
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.collected_souls") + ": " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(BLOOD_ALTAR) || ForgeRegistries.ITEMS.getKey(item).equals(SOUL_ALTAR))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.altar"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(HARKEN_ATHAME))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.athame"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(BIOMASS_SEED))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.biomass_seed"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(BLUNT_HARKEN_BLADE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.blunt_blade"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(LIVINGMETAL_SHEARS))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.shears"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(REAPER_SCYTHE))
        {
            event.getToolTip().add(1, "");
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.reaper_scythe.4"));
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.reaper_scythe.3"));
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.reaper_scythe.2"));
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.reaper_scythe.1"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SOUL_CAKE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.soul_cake"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SOUL_COOKIE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.soul_cookie"));
        }
    }
}
