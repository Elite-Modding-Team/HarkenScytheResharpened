package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.block.HSBlockBiomassCrop;
import mod.emt.harkenscythe.block.HSBlockSoulCake;
import mod.emt.harkenscythe.compat.baubles.HSBaublesItemEssenceTrinketBlood;
import mod.emt.harkenscythe.compat.baubles.HSBaublesItemEssenceTrinketBloodEthereal;
import mod.emt.harkenscythe.compat.baubles.HSBaublesItemEssenceTrinketSoul;
import mod.emt.harkenscythe.compat.baubles.HSBaublesItemEssenceTrinketSoulEthereal;
import mod.emt.harkenscythe.compat.patchouli.item.HSItemGuidebook;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.item.*;
import mod.emt.harkenscythe.item.armor.HSArmor;
import mod.emt.harkenscythe.item.armor.HSArmorDyeable;
import mod.emt.harkenscythe.item.tool.*;

@SuppressWarnings({"DataFlowIssue", "deprecation", "unused"})
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSItems
{
    public static final HSItemEssenceKeeper essence_keeper = null;
    public static final HSItemEssenceKeeperBlood essence_keeper_blood = null;
    public static final HSItemEssenceKeeperSoul essence_keeper_soul = null;
    public static final HSItemEssenceVessel essence_vessel = null;
    public static final HSItemEssenceVesselBlood essence_vessel_blood = null;
    public static final HSItemEssenceVesselSoul essence_vessel_soul = null;
    public static final HSItemEssenceTrinketBlood essence_trinket_blood = null;
    public static final HSItemEssenceTrinketBloodEthereal essence_trinket_blood_ethereal = null;
    public static final HSItemEssenceTrinketSoul essence_trinket_soul = null;
    public static final HSItemEssenceTrinketSoulEthereal essence_trinket_soul_ethereal = null;

    public static final HSToolScythe wooden_scythe = null;
    public static final HSToolScythe stone_scythe = null;
    public static final HSToolScythe iron_scythe = null;
    public static final HSToolScythe golden_scythe = null;
    public static final HSToolScythe diamond_scythe = null;
    public static final HSToolScythe biomass_scythe = null;
    public static final HSToolScythe livingmetal_scythe = null;
    public static final HSToolScythe reaper_scythe = null;
    public static final HSToolScythe lady_harken_scythe = null;

    public static final HSToolGlaive wooden_glaive = null;
    public static final HSToolGlaive stone_glaive = null;
    public static final HSToolGlaive iron_glaive = null;
    public static final HSToolGlaive golden_glaive = null;
    public static final HSToolGlaive diamond_glaive = null;
    public static final HSToolGlaive biomass_glaive = null;
    public static final HSToolGlaive livingmetal_glaive = null;

    public static final HSItemEssence blood_essence = null;
    public static final HSItemEssence blood_essence_sickly = null;
    public static final HSItemEssence blood_essence_intoxicated = null;
    public static final HSItemEssence blood_essence_warped = null;

    public static final HSItemEssence soul_essence = null;
    public static final HSItemEssence soul_essence_grieving = null;
    public static final HSItemEssence soul_essence_culled = null;
    public static final HSItemEssence soul_essence_wrathful = null;
    public static final HSItemEssence soul_essence_spectral = null;

    public static final HSItem abyssal_fragment = null;

    public static final HSItemNecronomicon ancient_necronomicon = null;
    public static final HSItem ancient_necronomicon_page = null;
    public static final HSItem carnage_book = null;
    public static final HSItem shadow_book = null;

    public static final HSItemCreepball creepball = null;
    public static final HSItem biomass_seed = null;
    public static final HSItemBiomassSeedGerminated germinated_biomass_seed = null;
    public static final HSItem biomass = null;
    public static final HSToolSword biomass_sword = null;
    public static final HSToolShovel biomass_shovel = null;
    public static final HSToolPickaxe biomass_pickaxe = null;
    public static final HSToolAxe biomass_axe = null;
    public static final HSToolHoe biomass_hoe = null;
    public static final HSArmor biomass_helmet = null;
    public static final HSArmor biomass_chestplate = null;
    public static final HSArmor biomass_leggings = null;
    public static final HSArmor biomass_boots = null;

    public static final HSItem livingmetal_ingot = null;
    public static final HSToolSword livingmetal_sword = null;
    public static final HSToolShovel livingmetal_shovel = null;
    public static final HSToolPickaxe livingmetal_pickaxe = null;
    public static final HSToolAxe livingmetal_axe = null;
    public static final HSToolHoe livingmetal_hoe = null;
    public static final HSToolShears livingmetal_shears = null;
    public static final HSArmor livingmetal_helmet = null;
    public static final HSArmor livingmetal_chestplate = null;
    public static final HSArmor livingmetal_leggings = null;
    public static final HSArmor livingmetal_boots = null;

    public static final HSItemBlockSpecial soul_cake = null;
    public static final HSItemFood soul_cookie = null;

    public static final HSItem blunt_harken_blade = null;
    public static final HSToolAthame harken_athame = null;

    public static final HSItem unpowered_totem_of_undying = null;

    public static final HSItemDimensionalMirror dimensional_mirror = null;

    public static final HSToolPickaxe spectral_pickaxe = null;

    public static final HSItem damaged_vampire_knife = null;
    public static final HSToolVampireKnife vampire_knife = null;
    public static final HSItem vampire_knife_projectile = null;

    public static final HSItemSpectralBottle spectral_glass_bottle = null;
    public static final HSItemSpectralPotion spectral_potion_affliction = null;
    public static final HSItemSpectralPotion spectral_potion_flame = null;
    public static final HSItemSpectralPotion spectral_potion_purifying = null;
    public static final HSItemSpectralPotion spectral_potion_water = null;

    public static final HSArmorDyeable bloodweave_hood = null;
    public static final HSArmorDyeable bloodweave_robe = null;
    public static final HSArmorDyeable bloodweave_pants = null;
    public static final HSArmorDyeable bloodweave_shoes = null;
    public static final HSArmorDyeable soulweave_hood = null;
    public static final HSArmorDyeable soulweave_robe = null;
    public static final HSArmorDyeable soulweave_pants = null;
    public static final HSArmorDyeable soulweave_shoes = null;

    public static final HSItemDeadtimeWatch deadtime_watch = null;

    public static final HSItem reaper_guidebook = null;

    @SubscribeEvent
    public static void onRegisterItemsEvent(@Nonnull final RegistryEvent.Register<Item> event)
    {
        HarkenScythe.LOGGER.info("Registering items...");

        final IForgeRegistry<Item> registry = event.getRegistry();

        // ITEMS
        registry.registerAll
            (
                HSRegistry.setup(new HSItemEssenceKeeper(), "essence_keeper").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssenceKeeperBlood(), "essence_keeper_blood").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssenceKeeperSoul(), "essence_keeper_soul").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssenceVessel(), "essence_vessel").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssenceVesselBlood(), "essence_vessel_blood").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssenceVesselSoul(), "essence_vessel_soul").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(ToolMaterial.WOOD, 1.4F, EnumRarity.COMMON), "wooden_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(ToolMaterial.STONE, 1.4F, EnumRarity.COMMON), "stone_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(ToolMaterial.IRON, 1.4F, EnumRarity.COMMON), "iron_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(ToolMaterial.GOLD, 1.4F, EnumRarity.COMMON), "golden_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(ToolMaterial.DIAMOND, 1.4F, EnumRarity.COMMON), "diamond_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(HSMaterials.TOOL_BIOMASS, 1.45F, EnumRarity.UNCOMMON), "biomass_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(HSMaterials.TOOL_LIVINGMETAL, 1.45F, EnumRarity.UNCOMMON), "livingmetal_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(HSMaterials.TOOL_REAPER, 1.5F, EnumRarity.EPIC), "reaper_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(HSMaterials.TOOL_LADY_HARKEN, 1.5F, EnumRarity.EPIC), "lady_harken_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.WOOD, 1.4F, EnumRarity.COMMON), "wooden_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.STONE, 1.4F, EnumRarity.COMMON), "stone_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.IRON, 1.4F, EnumRarity.COMMON), "iron_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.GOLD, 1.4F, EnumRarity.COMMON), "golden_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.DIAMOND, 1.4F, EnumRarity.COMMON), "diamond_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(HSMaterials.TOOL_BIOMASS, 1.45F, EnumRarity.UNCOMMON), "biomass_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(HSMaterials.TOOL_LIVINGMETAL, 1.45F, EnumRarity.UNCOMMON), "livingmetal_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.COMMON, HSConfig.ENTITIES.essenceBloodCommonValue), "blood_essence").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.UNCOMMON, HSConfig.ENTITIES.essenceBloodSicklyValue), "blood_essence_sickly").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.RARE, HSConfig.ENTITIES.essenceBloodIntoxicatedValue), "blood_essence_intoxicated").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.EPIC, HSConfig.ENTITIES.essenceBloodWarpedValue), "blood_essence_warped").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.COMMON, HSConfig.ENTITIES.essenceSoulCommonValue), "soul_essence").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.UNCOMMON, HSConfig.ENTITIES.essenceSoulGrievingValue), "soul_essence_grieving").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.RARE, HSConfig.ENTITIES.essenceSoulCulledValue), "soul_essence_culled").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.EPIC, HSConfig.ENTITIES.essenceSoulWrathfulValue), "soul_essence_wrathful").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemEssence(EnumRarity.EPIC, HSConfig.ENTITIES.essenceSoulSpectralValue), "soul_essence_spectral").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.RARE), "abyssal_fragment").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemNecronomicon(), "ancient_necronomicon").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "ancient_necronomicon_page").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "carnage_book").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "shadow_book").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemCreepball(), "creepball").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "biomass_seed").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemBiomassSeedGerminated(), "germinated_biomass_seed").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "biomass").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolSword(HSMaterials.TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_sword").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolShovel(HSMaterials.TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_shovel").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolPickaxe(HSMaterials.TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_pickaxe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolAxe(HSMaterials.TOOL_BIOMASS, 8.0F, -3.0F, EnumRarity.UNCOMMON), "biomass_axe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolHoe(HSMaterials.TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_hoe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(HSMaterials.ARMOR_BIOMASS, 4, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON), "biomass_helmet").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(HSMaterials.ARMOR_BIOMASS, 4, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON), "biomass_chestplate").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(HSMaterials.ARMOR_BIOMASS, 4, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON), "biomass_leggings").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(HSMaterials.ARMOR_BIOMASS, 4, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON), "biomass_boots").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "livingmetal_ingot").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolSword(HSMaterials.TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_sword").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolShovel(HSMaterials.TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_shovel").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolPickaxe(HSMaterials.TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_pickaxe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolAxe(HSMaterials.TOOL_LIVINGMETAL, 8.0F, -3.0F, EnumRarity.UNCOMMON), "livingmetal_axe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolHoe(HSMaterials.TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_hoe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolShears(481, EnumRarity.UNCOMMON, Ingredient.fromStacks(new ItemStack(livingmetal_ingot))), "livingmetal_shears").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(HSMaterials.ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON), "livingmetal_helmet").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(HSMaterials.ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON), "livingmetal_chestplate").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(HSMaterials.ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON), "livingmetal_leggings").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(HSMaterials.ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON), "livingmetal_boots").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemBlockSpecial(HSBlocks.soul_cake, EnumRarity.UNCOMMON), "soul_cake").setMaxStackSize(1).setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemFood(3, 0.2F, false, 16, true, EnumRarity.UNCOMMON), "soul_cookie").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "blunt_harken_blade").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolAthame(HSMaterials.TOOL_ATHAME_BASIC, EnumRarity.COMMON), "harken_athame").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "unpowered_totem_of_undying").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemDimensionalMirror(EnumRarity.UNCOMMON), "dimensional_mirror").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolSpectralPickaxe(), "spectral_pickaxe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.EPIC), "damaged_vampire_knife").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolVampireKnife(HSMaterials.TOOL_VAMPIRE_KNIFE, 2.0F), "vampire_knife").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "vampire_knife_projectile"), // Fake item for projectile, ignore
                HSRegistry.setup(new HSItemSpectralBottle(EnumRarity.UNCOMMON), "spectral_glass_bottle").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(HSMaterials.ARMOR_BLOODWEAVE, 0, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON, 11546150), "bloodweave_hood").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(HSMaterials.ARMOR_BLOODWEAVE, 0, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON, 11546150), "bloodweave_robe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(HSMaterials.ARMOR_BLOODWEAVE, 0, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON, 11546150), "bloodweave_pants").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(HSMaterials.ARMOR_BLOODWEAVE, 0, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON, 11546150), "bloodweave_shoes").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(HSMaterials.ARMOR_SOULWEAVE, 0, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON, 3847130), "soulweave_hood").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(HSMaterials.ARMOR_SOULWEAVE, 0, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON, 3847130), "soulweave_robe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(HSMaterials.ARMOR_SOULWEAVE, 0, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON, 3847130), "soulweave_pants").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(HSMaterials.ARMOR_SOULWEAVE, 0, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON, 3847130), "soulweave_shoes").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemDeadtimeWatch(EnumRarity.UNCOMMON), "deadtime_watch").setCreativeTab(HarkenScythe.TAB)
            );

        // POTIONS
        if (!HSConfig.GENERAL.disablePotions)
        {
            registry.registerAll
                (
                    HSRegistry.setup(new HSItemSpectralPotion(EnumRarity.UNCOMMON, HSPotions.AFFLICTION), "spectral_potion_affliction").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSItemSpectralPotion(EnumRarity.UNCOMMON, HSPotions.FLAME), "spectral_potion_flame").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSItemSpectralPotion(EnumRarity.UNCOMMON, HSPotions.PURIFYING), "spectral_potion_purifying").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSItemSpectralPotion(EnumRarity.UNCOMMON, HSPotions.WATER), "spectral_potion_water").setCreativeTab(HarkenScythe.TAB)
                );
        }

        // THIRD PARTY MOD INTEGRATION
        if (Loader.isModLoaded("patchouli"))
        {
            registry.register(HSRegistry.setup(new HSItemGuidebook(), "reaper_guidebook").setCreativeTab(HarkenScythe.TAB));
        }
        if (Loader.isModLoaded("baubles"))
        {
            registry.registerAll
                (
                    HSRegistry.setup(new HSBaublesItemEssenceTrinketBlood(), "essence_trinket_blood").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSBaublesItemEssenceTrinketBloodEthereal(), "essence_trinket_blood_ethereal").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSBaublesItemEssenceTrinketSoul(), "essence_trinket_soul").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSBaublesItemEssenceTrinketSoulEthereal(), "essence_trinket_soul_ethereal").setCreativeTab(HarkenScythe.TAB)
                );
        }
        else
        {
            registry.registerAll
                (
                    HSRegistry.setup(new HSItemEssenceTrinketBlood(), "essence_trinket_blood").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSItemEssenceTrinketBloodEthereal(), "essence_trinket_blood_ethereal").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSItemEssenceTrinketSoul(), "essence_trinket_soul").setCreativeTab(HarkenScythe.TAB),
                    HSRegistry.setup(new HSItemEssenceTrinketSoulEthereal(), "essence_trinket_soul_ethereal").setCreativeTab(HarkenScythe.TAB)
                );
        }

        // ITEM BLOCKS
        ForgeRegistries.BLOCKS.getValues().stream()
            .filter(block -> block.getRegistryName().getNamespace().equals(HarkenScythe.MOD_ID))
            .filter(block -> !(block instanceof HSBlockSoulCake))
            .filter(block -> !(block instanceof HSBlockBiomassCrop))
            .forEach(block -> registry.register(HSRegistry.setup(new ItemBlock(block), block.getRegistryName())));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event)
    {
        HarkenScythe.LOGGER.info("Registering item models...");

        // ITEM MODELS
        for (final Item item : ForgeRegistries.ITEMS.getValues())
        {
            if (item.getRegistryName().getNamespace().equals(HarkenScythe.MOD_ID))
            {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }
}
