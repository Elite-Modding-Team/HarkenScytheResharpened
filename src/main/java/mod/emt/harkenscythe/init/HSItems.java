package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.block.HSBlockBiomassCrop;
import mod.emt.harkenscythe.block.HSBlockSoulCake;
import mod.emt.harkenscythe.compat.patchouli.item.HSItemGuidebook;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.item.*;
import mod.emt.harkenscythe.item.armor.HSArmor;
import mod.emt.harkenscythe.item.armor.HSArmorDyeable;
import mod.emt.harkenscythe.item.tool.*;

@SuppressWarnings({"deprecation"})
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSItems
{
    @GameRegistry.ObjectHolder("essence_keeper")
    public static HSItemEssenceKeeper essence_keeper;
    @GameRegistry.ObjectHolder("essence_keeper_blood")
    public static HSItemEssenceKeeperBlood essence_keeper_blood;
    @GameRegistry.ObjectHolder("essence_keeper_soul")
    public static HSItemEssenceKeeperSoul essence_keeper_soul;
    @GameRegistry.ObjectHolder("essence_vessel")
    public static HSItemEssenceVessel essence_vessel;
    @GameRegistry.ObjectHolder("essence_vessel_blood")
    public static HSItemEssenceVesselBlood essence_vessel_blood;
    @GameRegistry.ObjectHolder("essence_vessel_soul")
    public static HSItemEssenceVesselSoul essence_vessel_soul;

    @GameRegistry.ObjectHolder("wooden_scythe")
    public static HSToolScythe wooden_scythe;
    @GameRegistry.ObjectHolder("stone_scythe")
    public static HSToolScythe stone_scythe;
    @GameRegistry.ObjectHolder("iron_scythe")
    public static HSToolScythe iron_scythe;
    @GameRegistry.ObjectHolder("golden_scythe")
    public static HSToolScythe golden_scythe;
    @GameRegistry.ObjectHolder("diamond_scythe")
    public static HSToolScythe diamond_scythe;
    @GameRegistry.ObjectHolder("biomass_scythe")
    public static HSToolScythe biomass_scythe;
    @GameRegistry.ObjectHolder("livingmetal_scythe")
    public static HSToolScythe livingmetal_scythe;
    @GameRegistry.ObjectHolder("reaper_scythe")
    public static HSToolScythe reaper_scythe;
    @GameRegistry.ObjectHolder("lady_harken_scythe")
    public static HSToolScythe lady_harken_scythe;

    @GameRegistry.ObjectHolder("wooden_glaive")
    public static HSToolGlaive wooden_glaive;
    @GameRegistry.ObjectHolder("stone_glaive")
    public static HSToolGlaive stone_glaive;
    @GameRegistry.ObjectHolder("iron_glaive")
    public static HSToolGlaive iron_glaive;
    @GameRegistry.ObjectHolder("golden_glaive")
    public static HSToolGlaive golden_glaive;
    @GameRegistry.ObjectHolder("diamond_glaive")
    public static HSToolGlaive diamond_glaive;
    @GameRegistry.ObjectHolder("biomass_glaive")
    public static HSToolGlaive biomass_glaive;
    @GameRegistry.ObjectHolder("livingmetal_glaive")
    public static HSToolGlaive livingmetal_glaive;

    @GameRegistry.ObjectHolder("blood_essence")
    public static HSItem blood_essence;
    @GameRegistry.ObjectHolder("soul_essence")
    public static HSItem soul_essence;
    @GameRegistry.ObjectHolder("abyssal_fragment")
    public static HSItem abyssal_fragment;

    @GameRegistry.ObjectHolder("ancient_necronomicon")
    public static HSItemNecronomicon ancient_necronomicon;
    @GameRegistry.ObjectHolder("ancient_necronomicon_page")
    public static HSItem ancient_necronomicon_page;
    @GameRegistry.ObjectHolder("carnage_book")
    public static HSItem carnage_book;
    @GameRegistry.ObjectHolder("shadow_book")
    public static HSItem shadow_book;

    @GameRegistry.ObjectHolder("creepball")
    public static HSItemCreepball creepball;
    @GameRegistry.ObjectHolder("biomass_seed")
    public static HSItem biomass_seed;
    @GameRegistry.ObjectHolder("germinated_biomass_seed")
    public static HSItemBiomassSeedGerminated germinated_biomass_seed;
    @GameRegistry.ObjectHolder("biomass")
    public static HSItem biomass;
    @GameRegistry.ObjectHolder("biomass_sword")
    public static HSToolSword biomass_sword;
    @GameRegistry.ObjectHolder("biomass_shovel")
    public static HSToolShovel biomass_shovel;
    @GameRegistry.ObjectHolder("biomass_pickaxe")
    public static HSToolPickaxe biomass_pickaxe;
    @GameRegistry.ObjectHolder("biomass_axe")
    public static HSToolAxe biomass_axe;
    @GameRegistry.ObjectHolder("biomass_hoe")
    public static HSToolHoe biomass_hoe;
    @GameRegistry.ObjectHolder("biomass_helmet")
    public static HSArmor biomass_helmet;
    @GameRegistry.ObjectHolder("biomass_chestplate")
    public static HSArmor biomass_chestplate;
    @GameRegistry.ObjectHolder("biomass_leggings")
    public static HSArmor biomass_leggings;
    @GameRegistry.ObjectHolder("biomass_boots")
    public static HSArmor biomass_boots;

    @GameRegistry.ObjectHolder("livingmetal_ingot")
    public static HSItem livingmetal_ingot;
    @GameRegistry.ObjectHolder("livingmetal_sword")
    public static HSToolSword livingmetal_sword;
    @GameRegistry.ObjectHolder("livingmetal_shovel")
    public static HSToolShovel livingmetal_shovel;
    @GameRegistry.ObjectHolder("livingmetal_pickaxe")
    public static HSToolPickaxe livingmetal_pickaxe;
    @GameRegistry.ObjectHolder("livingmetal_axe")
    public static HSToolAxe livingmetal_axe;
    @GameRegistry.ObjectHolder("livingmetal_hoe")
    public static HSToolHoe livingmetal_hoe;
    @GameRegistry.ObjectHolder("livingmetal_shears")
    public static HSToolShears livingmetal_shears;
    @GameRegistry.ObjectHolder("livingmetal_helmet")
    public static HSArmor livingmetal_helmet;
    @GameRegistry.ObjectHolder("livingmetal_chestplate")
    public static HSArmor livingmetal_chestplate;
    @GameRegistry.ObjectHolder("livingmetal_leggings")
    public static HSArmor livingmetal_leggings;
    @GameRegistry.ObjectHolder("livingmetal_boots")
    public static HSArmor livingmetal_boots;

    @GameRegistry.ObjectHolder("soul_cake")
    public static HSItemBlockSpecial soul_cake;
    @GameRegistry.ObjectHolder("soul_cookie")
    public static HSItemFood soul_cookie;

    @GameRegistry.ObjectHolder("blunt_harken_blade")
    public static HSItem blunt_harken_blade;
    @GameRegistry.ObjectHolder("harken_athame")
    public static HSToolAthame harken_athame;

    @GameRegistry.ObjectHolder("unpowered_totem_of_undying")
    public static HSItem unpowered_totem_of_undying;

    @GameRegistry.ObjectHolder("dimensional_mirror")
    public static HSItemDimensionalMirror dimensional_mirror;

    @GameRegistry.ObjectHolder("spectral_pickaxe")
    public static HSToolPickaxe spectral_pickaxe;

    @GameRegistry.ObjectHolder("damaged_vampire_knife")
    public static HSItem damaged_vampire_knife;
    @GameRegistry.ObjectHolder("vampire_knife")
    public static HSToolVampireKnife vampire_knife;
    @GameRegistry.ObjectHolder("vampire_knife_projectile")
    public static HSItem vampire_knife_projectile;

    @GameRegistry.ObjectHolder("spectral_glass_bottle")
    public static HSItemSpectralBottle spectral_glass_bottle;
    @GameRegistry.ObjectHolder("spectral_potion_affliction")
    public static HSItemSpectralPotion spectral_potion_affliction;
    @GameRegistry.ObjectHolder("spectral_potion_flame")
    public static HSItemSpectralPotion spectral_potion_flame;
    @GameRegistry.ObjectHolder("spectral_potion_purifying")
    public static HSItemSpectralPotion spectral_potion_purifying;
    @GameRegistry.ObjectHolder("spectral_potion_water")
    public static HSItemSpectralPotion spectral_potion_water;

    @GameRegistry.ObjectHolder("bloodweave_hood")
    public static HSArmorDyeable bloodweave_hood;
    @GameRegistry.ObjectHolder("bloodweave_robe")
    public static HSArmorDyeable bloodweave_robe;
    @GameRegistry.ObjectHolder("bloodweave_pants")
    public static HSArmorDyeable bloodweave_pants;
    @GameRegistry.ObjectHolder("bloodweave_shoes")
    public static HSArmorDyeable bloodweave_shoes;
    @GameRegistry.ObjectHolder("soulweave_hood")
    public static HSArmorDyeable soulweave_hood;
    @GameRegistry.ObjectHolder("soulweave_robe")
    public static HSArmorDyeable soulweave_robe;
    @GameRegistry.ObjectHolder("soulweave_pants")
    public static HSArmorDyeable soulweave_pants;
    @GameRegistry.ObjectHolder("soulweave_shoes")
    public static HSArmorDyeable soulweave_shoes;

    @GameRegistry.ObjectHolder("reaper_guidebook")
    public static HSItem reaper_guidebook;

    public static ArmorMaterial ARMOR_BIOMASS = EnumHelper.addArmorMaterial("hs_armor_biomass", "biomass", 14, new int[] {1, 4, 5, 2}, 17, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, 0.5F).setRepairItem(new ItemStack(biomass));
    public static ArmorMaterial ARMOR_LIVINGMETAL = EnumHelper.addArmorMaterial("hs_armor_livingmetal", "livingmetal", 24, new int[] {2, 5, 6, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F).setRepairItem(new ItemStack(livingmetal_ingot));
    public static ArmorMaterial ARMOR_BLOODWEAVE = EnumHelper.addArmorMaterial("hs_armor_bloodweave", "bloodweave", 15, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F).setRepairItem(new ItemStack(Item.getItemFromBlock(HSBlocks.bloodweave_cloth)));
    public static ArmorMaterial ARMOR_SOULWEAVE = EnumHelper.addArmorMaterial("hs_armor_soulweave", "soulweave", 15, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F).setRepairItem(new ItemStack(Item.getItemFromBlock(HSBlocks.soulweave_cloth)));

    public static ToolMaterial TOOL_ATHAME_BASIC = EnumHelper.addToolMaterial("hs_tool_athame_basic", 2, 250, 6.0F, -1.5F, 14).setRepairItem(new ItemStack(Items.IRON_INGOT));
    public static ToolMaterial TOOL_BIOMASS = EnumHelper.addToolMaterial("hs_tool_biomass", 3, 151, 6.5F, 1.5F, 17).setRepairItem(new ItemStack(biomass));
    public static ToolMaterial TOOL_LIVINGMETAL = EnumHelper.addToolMaterial("hs_tool_livingmetal", 3, 481, 7.5F, 2.5F, 20).setRepairItem(new ItemStack(livingmetal_ingot));
    public static ToolMaterial TOOL_REAPER = EnumHelper.addToolMaterial("hs_tool_reaper", 4, 2466, 9.0F, 6.0F, 25).setRepairItem(new ItemStack(Items.BONE));
    public static ToolMaterial TOOL_SPECTRAL = EnumHelper.addToolMaterial("hs_tool_spectral", 4, 2466, 9.0F, 6.0F, 25).setRepairItem(new ItemStack(soul_essence));
    public static ToolMaterial TOOL_LADY_HARKEN = EnumHelper.addToolMaterial("hs_tool_lady_harken", 4, 2466, 9.0F, 6.0F, 25).setRepairItem(new ItemStack(soul_essence));

    // Non-Repairable
    public static ToolMaterial TOOL_VAMPIRE_KNIFE = EnumHelper.addToolMaterial("hs_tool_vampire_knife", 4, HSConfig.ITEMS.vampireKnifeMaxCharges, 9.0F, 0.0F, 25);

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
                HSRegistry.setup(new HSToolScythe(TOOL_BIOMASS, 1.45F, EnumRarity.UNCOMMON), "biomass_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(TOOL_LIVINGMETAL, 1.45F, EnumRarity.UNCOMMON), "livingmetal_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(TOOL_REAPER, 1.5F, EnumRarity.EPIC), "reaper_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolScythe(TOOL_LADY_HARKEN, 1.5F, EnumRarity.EPIC), "lady_harken_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.WOOD, 1.4F, EnumRarity.COMMON), "wooden_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.STONE, 1.4F, EnumRarity.COMMON), "stone_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.IRON, 1.4F, EnumRarity.COMMON), "iron_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.GOLD, 1.4F, EnumRarity.COMMON), "golden_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(ToolMaterial.DIAMOND, 1.4F, EnumRarity.COMMON), "diamond_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(TOOL_BIOMASS, 1.45F, EnumRarity.UNCOMMON), "biomass_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolGlaive(TOOL_LIVINGMETAL, 1.45F, EnumRarity.UNCOMMON), "livingmetal_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "blood_essence").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "soul_essence").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.RARE), "abyssal_fragment").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemNecronomicon(), "ancient_necronomicon").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "ancient_necronomicon_page").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "carnage_book").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "shadow_book").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemCreepball(), "creepball").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "biomass_seed").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemBiomassSeedGerminated(), "germinated_biomass_seed").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "biomass").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolSword(TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_sword").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolShovel(TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_shovel").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolPickaxe(TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_pickaxe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolAxe(TOOL_BIOMASS, 8.0F, -3.0F, EnumRarity.UNCOMMON), "biomass_axe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolHoe(TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_hoe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_BIOMASS, 4, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON), "biomass_helmet").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_BIOMASS, 4, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON), "biomass_chestplate").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_BIOMASS, 4, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON), "biomass_leggings").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_BIOMASS, 4, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON), "biomass_boots").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "livingmetal_ingot").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolSword(TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_sword").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolShovel(TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_shovel").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolPickaxe(TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_pickaxe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolAxe(TOOL_LIVINGMETAL, 8.0F, -3.0F, EnumRarity.UNCOMMON), "livingmetal_axe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolHoe(TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_hoe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolShears(481, EnumRarity.UNCOMMON, Ingredient.fromStacks(new ItemStack(livingmetal_ingot))), "livingmetal_shears").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON), "livingmetal_helmet").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON), "livingmetal_chestplate").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON), "livingmetal_leggings").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON), "livingmetal_boots").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemBlockSpecial(HSBlocks.soul_cake, EnumRarity.UNCOMMON), "soul_cake").setMaxStackSize(1).setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemFood(3, 0.2F, false, 16, true, EnumRarity.UNCOMMON), "soul_cookie").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "blunt_harken_blade").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolAthame(TOOL_ATHAME_BASIC, EnumRarity.COMMON), "harken_athame").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "unpowered_totem_of_undying").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemDimensionalMirror(EnumRarity.COMMON), "dimensional_mirror").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolSpectralPickaxe(), "spectral_pickaxe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.EPIC), "damaged_vampire_knife").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSToolVampireKnife(TOOL_VAMPIRE_KNIFE, 2.0F), "vampire_knife").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "vampire_knife_projectile"), // Fake item for projectile, ignore
                HSRegistry.setup(new HSItemSpectralBottle(EnumRarity.UNCOMMON), "spectral_glass_bottle").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(ARMOR_BLOODWEAVE, 0, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON, 11546150), "bloodweave_hood").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(ARMOR_BLOODWEAVE, 0, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON, 11546150), "bloodweave_robe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(ARMOR_BLOODWEAVE, 0, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON, 11546150), "bloodweave_pants").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(ARMOR_BLOODWEAVE, 0, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON, 11546150), "bloodweave_shoes").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(ARMOR_SOULWEAVE, 0, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON, 3847130), "soulweave_hood").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(ARMOR_SOULWEAVE, 0, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON, 3847130), "soulweave_robe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(ARMOR_SOULWEAVE, 0, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON, 3847130), "soulweave_pants").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmorDyeable(ARMOR_SOULWEAVE, 0, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON, 3847130), "soulweave_shoes").setCreativeTab(HarkenScythe.TAB)
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
