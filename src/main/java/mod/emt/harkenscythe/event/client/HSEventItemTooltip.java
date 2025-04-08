package mod.emt.harkenscythe.event.client;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.item.HSItemEssence;
import mod.emt.harkenscythe.item.HSItemEssenceKeeperBlood;
import mod.emt.harkenscythe.item.HSItemEssenceKeeperSoul;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSEventItemTooltip
{
    private static final ResourceLocation BLOOD_ALTAR = new ResourceLocation(HarkenScythe.MOD_ID, "blood_altar");
    private static final ResourceLocation SOUL_ALTAR = new ResourceLocation(HarkenScythe.MOD_ID, "soul_altar");

    private static final ResourceLocation ABYSSAL_FRAGMENT = new ResourceLocation(HarkenScythe.MOD_ID, "abyssal_fragment");
    private static final ResourceLocation ANCIENT_NECRONOMICON = new ResourceLocation(HarkenScythe.MOD_ID, "ancient_necronomicon");
    private static final ResourceLocation ANCIENT_NECRONOMICON_PAGE = new ResourceLocation(HarkenScythe.MOD_ID, "ancient_necronomicon_page");
    private static final ResourceLocation BIOMASS = new ResourceLocation(HarkenScythe.MOD_ID, "biomass");
    private static final ResourceLocation BIOMASS_SEED = new ResourceLocation(HarkenScythe.MOD_ID, "biomass_seed");
    private static final ResourceLocation BLOOD_ESSENCE = new ResourceLocation(HarkenScythe.MOD_ID, "blood_essence");
    private static final ResourceLocation BLUNT_HARKEN_BLADE = new ResourceLocation(HarkenScythe.MOD_ID, "blunt_harken_blade");
    private static final ResourceLocation CARNAGE_BOOK = new ResourceLocation(HarkenScythe.MOD_ID, "carnage_book");
    private static final ResourceLocation CREEPBALL = new ResourceLocation(HarkenScythe.MOD_ID, "creepball");
    private static final ResourceLocation DAMAGED_VAMPIRE_KNIFE = new ResourceLocation(HarkenScythe.MOD_ID, "damaged_vampire_knife");
    private static final ResourceLocation DIMENSIONAL_MIRROR = new ResourceLocation(HarkenScythe.MOD_ID, "dimensional_mirror");
    private static final ResourceLocation GERMINATED_BIOMASS_SEED = new ResourceLocation(HarkenScythe.MOD_ID, "germinated_biomass_seed");
    private static final ResourceLocation HARKEN_ATHAME = new ResourceLocation(HarkenScythe.MOD_ID, "harken_athame");
    private static final ResourceLocation LIVINGMETAL_INGOT = new ResourceLocation(HarkenScythe.MOD_ID, "livingmetal_ingot");
    private static final ResourceLocation LIVINGMETAL_SHEARS = new ResourceLocation(HarkenScythe.MOD_ID, "livingmetal_shears");
    private static final ResourceLocation REAPER_SCYTHE = new ResourceLocation(HarkenScythe.MOD_ID, "reaper_scythe");
    private static final ResourceLocation SHADOW_BOOK = new ResourceLocation(HarkenScythe.MOD_ID, "shadow_book");
    private static final ResourceLocation SOUL_CAKE = new ResourceLocation(HarkenScythe.MOD_ID, "soul_cake");
    private static final ResourceLocation SOUL_COOKIE = new ResourceLocation(HarkenScythe.MOD_ID, "soul_cookie");
    private static final ResourceLocation SOUL_ESSENCE = new ResourceLocation(HarkenScythe.MOD_ID, "soul_essence");
    private static final ResourceLocation SOUL_SOOT = new ResourceLocation(HarkenScythe.MOD_ID, "soul_soot");
    private static final ResourceLocation SOUMBERGLASS_INGOT = new ResourceLocation(HarkenScythe.MOD_ID, "soumberglass_ingot");
    private static final ResourceLocation SPECTRAL_POTION_AFFLICTION = new ResourceLocation(HarkenScythe.MOD_ID, "spectral_potion_affliction");
    private static final ResourceLocation SPECTRAL_POTION_FLAME = new ResourceLocation(HarkenScythe.MOD_ID, "spectral_potion_flame");
    private static final ResourceLocation SPECTRAL_POTION_PURIFYING = new ResourceLocation(HarkenScythe.MOD_ID, "spectral_potion_purifying");
    private static final ResourceLocation SPECTRAL_POTION_WATER = new ResourceLocation(HarkenScythe.MOD_ID, "spectral_potion_water");
    private static final ResourceLocation UNPOWERED_TOTEM_OF_UNDYING = new ResourceLocation(HarkenScythe.MOD_ID, "unpowered_totem_of_undying");
    private static final ResourceLocation VAMPIRE_KNIFE = new ResourceLocation(HarkenScythe.MOD_ID, "vampire_knife");

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
        else if (ForgeRegistries.ITEMS.getKey(item).equals(ABYSSAL_FRAGMENT))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.abyssal_fragment"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(ANCIENT_NECRONOMICON))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.ancient_necronomicon"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(ANCIENT_NECRONOMICON_PAGE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.ancient_necronomicon_page"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(BIOMASS))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.biomass"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(BIOMASS_SEED))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.biomass_seed"));
        }
        else if (item instanceof HSItemEssence && ForgeRegistries.ITEMS.getKey(item).getPath().contains(BLOOD_ESSENCE.getPath()))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.blood_essence"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(BLUNT_HARKEN_BLADE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.blunt_blade"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(CARNAGE_BOOK))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.carnage_book"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(CREEPBALL))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.creepball"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(DAMAGED_VAMPIRE_KNIFE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.damaged_relic"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(DIMENSIONAL_MIRROR))
        {
            event.getToolTip().add(1, "");
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.dimensional_mirror"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(GERMINATED_BIOMASS_SEED))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.germinated_biomass_seed"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(HARKEN_ATHAME))
        {
            event.getToolTip().add(1, "");
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.athame"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(LIVINGMETAL_INGOT))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.livingmetal_ingot"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(LIVINGMETAL_SHEARS))
        {
            event.getToolTip().add(1, "");
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
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SHADOW_BOOK))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.shadow_book"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SOUL_CAKE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.soul_cake"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SOUL_COOKIE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.soul_cookie"));
        }
        else if (item instanceof HSItemEssence && ForgeRegistries.ITEMS.getKey(item).getPath().contains(SOUL_ESSENCE.getPath()))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.soul_essence"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SOUL_SOOT))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.soul_soot"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SOUMBERGLASS_INGOT))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.soumberglass_ingot"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SPECTRAL_POTION_AFFLICTION))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.spectral_potion_affliction"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SPECTRAL_POTION_FLAME))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.spectral_potion_flame"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SPECTRAL_POTION_PURIFYING))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.spectral_potion_purifying"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(SPECTRAL_POTION_WATER))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.spectral_potion_water"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(UNPOWERED_TOTEM_OF_UNDYING))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.totem_undying_unpowered"));
        }
        else if (ForgeRegistries.ITEMS.getKey(item).equals(VAMPIRE_KNIFE))
        {
            event.getToolTip().add(1, I18n.format("tooltip.harkenscythe.vampire_knife"));
        }
    }
}
