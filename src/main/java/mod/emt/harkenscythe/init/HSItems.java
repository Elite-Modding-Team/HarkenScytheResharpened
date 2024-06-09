package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.blocks.HSSoulCake;
import mod.emt.harkenscythe.items.HSArmor;
import mod.emt.harkenscythe.items.HSBiomassSeedGerminated;
import mod.emt.harkenscythe.items.HSCreepball;
import mod.emt.harkenscythe.items.HSEssenceKeeper;
import mod.emt.harkenscythe.items.HSEssenceKeeperBlood;
import mod.emt.harkenscythe.items.HSEssenceKeeperSoul;
import mod.emt.harkenscythe.items.HSEssenceVessel;
import mod.emt.harkenscythe.items.HSEssenceVesselBlood;
import mod.emt.harkenscythe.items.HSEssenceVesselSoul;
import mod.emt.harkenscythe.items.HSFood;
import mod.emt.harkenscythe.items.HSItem;
import mod.emt.harkenscythe.items.HSItemBlockSpecial;
import mod.emt.harkenscythe.items.HSNecronomicon;
import mod.emt.harkenscythe.items.tools.HSAxe;
import mod.emt.harkenscythe.items.tools.HSGlaive;
import mod.emt.harkenscythe.items.tools.HSHoe;
import mod.emt.harkenscythe.items.tools.HSPickaxe;
import mod.emt.harkenscythe.items.tools.HSScythe;
import mod.emt.harkenscythe.items.tools.HSShovel;
import mod.emt.harkenscythe.items.tools.HSSword;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings({"deprecation", "unused"})
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSItems
{
    @GameRegistry.ObjectHolder("essence_keeper")
    public static HSEssenceKeeper essence_keeper;
    @GameRegistry.ObjectHolder("essence_keeper_blood")
    public static HSEssenceKeeperBlood essence_keeper_blood;
    @GameRegistry.ObjectHolder("essence_keeper_soul")
    public static HSEssenceKeeperSoul essence_keeper_soul;
    @GameRegistry.ObjectHolder("essence_vessel")
    public static HSEssenceVessel essence_vessel;
    @GameRegistry.ObjectHolder("essence_vessel_blood")
    public static HSEssenceVesselBlood essence_vessel_blood;
    @GameRegistry.ObjectHolder("essence_vessel_soul")
    public static HSEssenceVesselSoul essence_vessel_soul;

    @GameRegistry.ObjectHolder("wooden_scythe")
    public static HSScythe wooden_scythe;
    @GameRegistry.ObjectHolder("stone_scythe")
    public static HSScythe stone_scythe;
    @GameRegistry.ObjectHolder("iron_scythe")
    public static HSScythe iron_scythe;
    @GameRegistry.ObjectHolder("golden_scythe")
    public static HSScythe golden_scythe;
    @GameRegistry.ObjectHolder("diamond_scythe")
    public static HSScythe diamond_scythe;

    @GameRegistry.ObjectHolder("wooden_glaive")
    public static HSGlaive wooden_glaive;
    @GameRegistry.ObjectHolder("stone_glaive")
    public static HSGlaive stone_glaive;
    @GameRegistry.ObjectHolder("iron_glaive")
    public static HSGlaive iron_glaive;
    @GameRegistry.ObjectHolder("golden_glaive")
    public static HSGlaive golden_glaive;
    @GameRegistry.ObjectHolder("diamond_glaive")
    public static HSGlaive diamond_glaive;

    @GameRegistry.ObjectHolder("blood_essence")
    public static HSItem blood_essence;
    @GameRegistry.ObjectHolder("soul_essence")
    public static HSItem soul_essence;

    @GameRegistry.ObjectHolder("ancient_necronomicon")
    public static HSNecronomicon ancient_necronomicon;
    @GameRegistry.ObjectHolder("ancient_necronomicon_page")
    public static HSItem ancient_necronomicon_page;
    @GameRegistry.ObjectHolder("carnage_book")
    public static HSItem carnage_book;
    @GameRegistry.ObjectHolder("shadow_book")
    public static HSItem shadow_book;

    @GameRegistry.ObjectHolder("creepball")
    public static HSCreepball creepball;
    @GameRegistry.ObjectHolder("biomass_seed")
    public static HSItem biomass_seed;
    @GameRegistry.ObjectHolder("germinated_biomass_seed")
    public static HSBiomassSeedGerminated germinated_biomass_seed;
    @GameRegistry.ObjectHolder("biomass")
    public static HSItem biomass;
    @GameRegistry.ObjectHolder("biomass_sword")
    public static HSSword biomass_sword;
    @GameRegistry.ObjectHolder("biomass_shovel")
    public static HSShovel biomass_shovel;
    @GameRegistry.ObjectHolder("biomass_pickaxe")
    public static HSPickaxe biomass_pickaxe;
    @GameRegistry.ObjectHolder("biomass_axe")
    public static HSAxe biomass_axe;
    @GameRegistry.ObjectHolder("biomass_hoe")
    public static HSHoe biomass_hoe;
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
    public static HSSword livingmetal_sword;
    @GameRegistry.ObjectHolder("livingmetal_shovel")
    public static HSShovel livingmetal_shovel;
    @GameRegistry.ObjectHolder("livingmetal_pickaxe")
    public static HSPickaxe livingmetal_pickaxe;
    @GameRegistry.ObjectHolder("livingmetal_axe")
    public static HSAxe livingmetal_axe;
    @GameRegistry.ObjectHolder("livingmetal_hoe")
    public static HSHoe livingmetal_hoe;
    @GameRegistry.ObjectHolder("livingmetal_helmet")
    public static HSArmor livingmetal_helmet;
    @GameRegistry.ObjectHolder("livingmetal_chestplate")
    public static HSArmor livingmetal_chestplate;
    @GameRegistry.ObjectHolder("livingmetal_leggings")
    public static HSArmor livingmetal_leggings;
    @GameRegistry.ObjectHolder("livingmetal_boots")
    public static HSArmor livingmetal_boots;

    /*@GameRegistry.ObjectHolder("soul_cake")
    public static HSItemBlockSpecial soul_cake;*/
    @GameRegistry.ObjectHolder("soul_cookie")
    public static HSFood soul_cookie;

    @GameRegistry.ObjectHolder("blunt_harken_blade")
    public static HSItem blunt_harken_blade;
    @GameRegistry.ObjectHolder("harken_athame")
    public static HSSword harken_athame;

    public static ArmorMaterial ARMOR_BIOMASS = EnumHelper.addArmorMaterial("biomass", "biomass", 14, new int[] {1, 4, 5, 2}, 17, SoundEvents.BLOCK_SLIME_PLACE, 0.5F).setRepairItem(new ItemStack(biomass));
    public static ArmorMaterial ARMOR_LIVINGMETAL = EnumHelper.addArmorMaterial("livingmetal", "livingmetal", 24, new int[] {2, 5, 6, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F).setRepairItem(new ItemStack(livingmetal_ingot));

    public static ToolMaterial TOOL_BIOMASS = EnumHelper.addToolMaterial("biomass", 3, 151, 6.5F, 1.5F, 17).setRepairItem(new ItemStack(biomass));
    public static ToolMaterial TOOL_LIVINGMETAL = EnumHelper.addToolMaterial("livingmetal", 3, 481, 7.5F, 2.5F, 20).setRepairItem(new ItemStack(livingmetal_ingot));

    @SubscribeEvent
    public static void onRegisterItemsEvent(@Nonnull final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();

        // ITEMS
        registry.registerAll
            (
                HSRegistry.setup(new HSEssenceKeeper(), "essence_keeper").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSEssenceKeeperBlood(), "essence_keeper_blood").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSEssenceKeeperSoul(), "essence_keeper_soul").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSEssenceVessel(), "essence_vessel").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSEssenceVesselBlood(), "essence_vessel_blood").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSEssenceVesselSoul(), "essence_vessel_soul").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSScythe(ToolMaterial.WOOD, 1.4F, EnumRarity.COMMON), "wooden_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSScythe(ToolMaterial.STONE, 1.4F, EnumRarity.COMMON), "stone_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSScythe(ToolMaterial.IRON, 1.4F, EnumRarity.COMMON), "iron_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSScythe(ToolMaterial.GOLD, 1.4F, EnumRarity.COMMON), "golden_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSScythe(ToolMaterial.DIAMOND, 1.4F, EnumRarity.COMMON), "diamond_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSScythe(TOOL_BIOMASS, 1.45F, EnumRarity.UNCOMMON), "biomass_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSScythe(TOOL_LIVINGMETAL, 1.45F, EnumRarity.UNCOMMON), "livingmetal_scythe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSGlaive(ToolMaterial.WOOD, 1.4F, EnumRarity.COMMON), "wooden_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSGlaive(ToolMaterial.STONE, 1.4F, EnumRarity.COMMON), "stone_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSGlaive(ToolMaterial.IRON, 1.4F, EnumRarity.COMMON), "iron_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSGlaive(ToolMaterial.GOLD, 1.4F, EnumRarity.COMMON), "golden_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSGlaive(ToolMaterial.DIAMOND, 1.4F, EnumRarity.COMMON), "diamond_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSGlaive(TOOL_BIOMASS, 1.45F, EnumRarity.UNCOMMON), "biomass_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSGlaive(TOOL_LIVINGMETAL, 1.45F, EnumRarity.UNCOMMON), "livingmetal_glaive").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "blood_essence").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "soul_essence").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSNecronomicon(), "ancient_necronomicon").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "ancient_necronomicon_page").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "carnage_book").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "shadow_book").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSCreepball(), "creepball").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "biomass_seed").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBiomassSeedGerminated(), "germinated_biomass_seed").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "biomass").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSword(TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_sword").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSShovel(TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_shovel").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSPickaxe(TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_pickaxe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSAxe(TOOL_BIOMASS, 8.0F, -3.0F, EnumRarity.UNCOMMON), "biomass_axe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSHoe(TOOL_BIOMASS, EnumRarity.UNCOMMON), "biomass_hoe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_BIOMASS, 4, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON), "biomass_helmet").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_BIOMASS, 4, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON), "biomass_chestplate").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_BIOMASS, 4, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON), "biomass_leggings").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_BIOMASS, 4, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON), "biomass_boots").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.UNCOMMON), "livingmetal_ingot").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSword(TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_sword").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSShovel(TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_shovel").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSPickaxe(TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_pickaxe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSAxe(TOOL_LIVINGMETAL, 8.0F, -3.0F, EnumRarity.UNCOMMON), "livingmetal_axe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSHoe(TOOL_LIVINGMETAL, EnumRarity.UNCOMMON), "livingmetal_hoe").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.HEAD, EnumRarity.UNCOMMON), "livingmetal_helmet").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.CHEST, EnumRarity.UNCOMMON), "livingmetal_chestplate").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.LEGS, EnumRarity.UNCOMMON), "livingmetal_leggings").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSArmor(ARMOR_LIVINGMETAL, 4, EntityEquipmentSlot.FEET, EnumRarity.UNCOMMON), "livingmetal_boots").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItemBlockSpecial(HSBlocks.soul_cake, EnumRarity.UNCOMMON), "soul_cake").setMaxStackSize(1).setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSFood(3, 0.2F, false, 16, true, EnumRarity.UNCOMMON), "soul_cookie").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSItem(EnumRarity.COMMON), "blunt_harken_blade").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSword(ToolMaterial.IRON, EnumRarity.COMMON), "harken_athame").setCreativeTab(HarkenScythe.TAB)
            );

        // ITEM BLOCKS
        ForgeRegistries.BLOCKS.getValues().stream()
            .filter(block -> block.getRegistryName().getNamespace().equals(HarkenScythe.MOD_ID))
            .filter(block -> !(block instanceof HSSoulCake))
            .forEach(block -> registry.register(HSRegistry.setup(new ItemBlock(block), block.getRegistryName())));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event)
    {
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
