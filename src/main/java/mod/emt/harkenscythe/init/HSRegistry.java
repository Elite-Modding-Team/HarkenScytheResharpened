package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.advancement.HSAdvancementTrigger;
import mod.emt.harkenscythe.client.renderer.*;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.*;
import mod.emt.harkenscythe.recipe.HSRecipeRefreshTomeUse;
import mod.emt.harkenscythe.tileentity.*;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSRegistry
{
    public static final IRarity RARITY_BLOODY = new IRarity()
    {
        @Override
        public TextFormatting getColor()
        {
            return TextFormatting.RED;
        }

        @Override
        public String getName()
        {
            return "Bloody";
        }
    };
    private static int entityID = 1;

    @Nonnull
    public static <T extends IForgeRegistryEntry> T setup(@Nonnull final T entry, @Nonnull final String name)
    {
        return setup(entry, new ResourceLocation(HarkenScythe.MOD_ID, name));
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName)
    {
        Preconditions.checkNotNull(entry, "Entry to setup must not be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign must not be null!");

        entry.setRegistryName(registryName);
        if (entry instanceof Block) ((Block) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath());
        if (entry instanceof Item) ((Item) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath());
        return entry;
    }

    public static void registerEntity(String name, Class<? extends Entity> clazz, int eggColor1, int eggColor2)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(HarkenScythe.MOD_ID, name), clazz, HarkenScythe.MOD_ID + "." + name, entityID++, HarkenScythe.instance, 64, 1, true, eggColor1, eggColor2);
    }

    public static void registerEntity(String name, Class<? extends Entity> clazz)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(HarkenScythe.MOD_ID, name), clazz, HarkenScythe.MOD_ID + "." + name, entityID++, HarkenScythe.instance, 64, 1, true);
    }

    public static void registerAdvancements()
    {
        for (HSAdvancementTrigger trigger : HSAdvancements.TRIGGER_ARRAY)
        {
            CriteriaTriggers.register(trigger);
        }
    }

    @SubscribeEvent
    public static void registerEnchantments(@Nonnull final RegistryEvent.Register<Enchantment> event)
    {
        if (HSConfig.GENERAL.disableEnchantments) return;

        HarkenScythe.LOGGER.info("Registering enchantments...");

        final IForgeRegistry<Enchantment> registry = event.getRegistry();

        if (HSConfig.ENCHANTMENTS.enchantmentBlight) registry.register(HSEnchantments.BLIGHT);
        if (HSConfig.ENCHANTMENTS.enchantmentBloodletting) registry.register(HSEnchantments.BLOODLETTING);
        if (HSConfig.ENCHANTMENTS.enchantmentExude) registry.register(HSEnchantments.EXUDE);
        if (HSConfig.ENCHANTMENTS.enchantmentHemorrhage) registry.register(HSEnchantments.HEMORRHAGE);
        if (HSConfig.ENCHANTMENTS.enchantmentNourishment) registry.register(HSEnchantments.NOURISHMENT);
        if (HSConfig.ENCHANTMENTS.enchantmentReapingFrenzy) registry.register(HSEnchantments.REAPING_FRENZY);
        if (HSConfig.ENCHANTMENTS.enchantmentSoulTether) registry.register(HSEnchantments.SOULSTEAL);
        if (HSConfig.ENCHANTMENTS.enchantmentWillingness) registry.register(HSEnchantments.WILLINGNESS);
    }

    @SubscribeEvent
    public static void registerEntities(@Nonnull final RegistryEvent.Register<EntityEntry> event)
    {
        HarkenScythe.LOGGER.info("Registering entities...");

        registerEntity("harbinger", HSEntityHarbinger.class, 2829099, 14079702);

        registerEntity("blood", HSEntityBlood.class);
        registerEntity("soul", HSEntitySoul.class);

        registerEntity("ectoglobin", HSEntityEctoglobin.class, 2304558, 14870762);
        registerEntity("hemoglobin", HSEntityHemoglobin.class, 3084561, 15455958);

        registerEntity("spectral_human", HSEntitySpectralHuman.class, 5334917, 12698049);
        registerEntity("spectral_miner", HSEntitySpectralMiner.class, 3638918, 15132390);

        registerEntity("spectral_potion", HSEntitySpectralPotion.class);
        registerEntity("vampire_knife", HSEntityVampireKnife.class);

        registerEntity("exospider", HSEntityExospider.class, 3285790, 1641735);

        registerEntitySpawns();
    }

    public static void registerEntitySpawns()
    {
        if (HSConfig.ENTITIES.harbingerSpawnProbability > 0)
        {
            EntityRegistry.addSpawn(HSEntityHarbinger.class, HSConfig.ENTITIES.harbingerSpawnProbability, 1, 1, EnumCreatureType.MONSTER, getEntityBiomes(EntityZombie.class));
            EntitySpawnPlacementRegistry.setPlacementType(HSEntityHarbinger.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }
        if (HSConfig.ENTITIES.spectralHumanSpawnProbability > 0)
        {
            EntityRegistry.addSpawn(HSEntitySpectralHuman.class, HSConfig.ENTITIES.spectralHumanSpawnProbability, 1, 2, EnumCreatureType.MONSTER, getEntityBiomes(EntityZombie.class));
            EntitySpawnPlacementRegistry.setPlacementType(HSEntitySpectralHuman.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }
        if (HSConfig.ENTITIES.spectralMinerSpawnProbability > 0)
        {
            EntityRegistry.addSpawn(HSEntitySpectralMiner.class, HSConfig.ENTITIES.spectralMinerSpawnProbability, 1, 1, EnumCreatureType.MONSTER, getEntityBiomes(EntityZombie.class));
            EntitySpawnPlacementRegistry.setPlacementType(HSEntitySpectralMiner.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }
        if (HSConfig.ENTITIES.exospiderSpawnProbability > 0)
        {
            EntityRegistry.addSpawn(HSEntityExospider.class, HSConfig.ENTITIES.exospiderSpawnProbability, 1, 3, EnumCreatureType.MONSTER, getEntityBiomes(EntityPigZombie.class));
            EntitySpawnPlacementRegistry.setPlacementType(HSEntityExospider.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }
    }

    // Gets biomes from selected entity.
    public static Biome[] getEntityBiomes(Class<? extends Entity> spawn)
    {
        List<Biome> biomes = new ArrayList<>();

        for (Biome biome : Biome.REGISTRY)
        {
            List<Biome.SpawnListEntry> spawnList = biome.getSpawnableList(EnumCreatureType.MONSTER);

            for (Biome.SpawnListEntry list : spawnList)
                if (list.entityClass == spawn)
                {
                    biomes.add(biome);
                    break;
                }
        }

        return biomes.toArray(new Biome[0]);
    }

    @SubscribeEvent
    public static void registerPotions(@Nonnull final RegistryEvent.Register<Potion> event)
    {
        if (!HSConfig.GENERAL.disableEnchantments) event.getRegistry().register(HSPotions.BLEEDING);

        if (HSConfig.GENERAL.disablePotions) return;

        HarkenScythe.LOGGER.info("Registering potions...");

        event.getRegistry().registerAll(
            HSPotions.AFFLICTION,
            HSPotions.FLAME,
            HSPotions.PURIFYING,
            HSPotions.WATER
        );
    }

    @SubscribeEvent
    public static void registerRecipes(@Nonnull final RegistryEvent.Register<IRecipe> event)
    {
        HarkenScythe.LOGGER.info("Registering ore dictionary entries...");

        final IForgeRegistry<IRecipe> registry = event.getRegistry();

        // Ore Dictionary
        for (Item item : ForgeRegistries.ITEMS)
        {
            if (item.equals(Items.POTIONITEM))
            {
                OreDictionary.registerOre("potion", item);
            }
        }

        // Misc Recipes
        registry.register(new HSRecipeRefreshTomeUse());

        // Furnace Recipes
        GameRegistry.addSmelting(new ItemStack(HSItems.soul_soot), new ItemStack(HSItems.soumberglass_ingot), 0.7F);

        OreDictionary.registerOre("blockBiomass", HSBlocks.biomass_block);
        OreDictionary.registerOre("blockGlassSpectral", HSBlocks.spectral_glass);
        OreDictionary.registerOre("blockGlassSpectral", HSBlocks.spectral_glass_inverted);
        OreDictionary.registerOre("blockLivingmetal", HSBlocks.livingmetal_block);

        OreDictionary.registerOre("paneGlassSpectral", HSBlocks.spectral_glass_pane);
        OreDictionary.registerOre("paneGlassSpectral", HSBlocks.spectral_glass_pane_inverted);

        OreDictionary.registerOre("essenceHarken", HSItems.blood_essence);
        OreDictionary.registerOre("essenceHarken", HSItems.blood_essence_sickly);
        OreDictionary.registerOre("essenceHarken", HSItems.blood_essence_intoxicated);
        OreDictionary.registerOre("essenceHarken", HSItems.blood_essence_warped);
        OreDictionary.registerOre("essenceHarken", HSItems.soul_essence);
        OreDictionary.registerOre("essenceHarken", HSItems.soul_essence_grieving);
        OreDictionary.registerOre("essenceHarken", HSItems.soul_essence_culled);
        OreDictionary.registerOre("essenceHarken", HSItems.soul_essence_spectral);
        OreDictionary.registerOre("essenceHarken", HSItems.soul_essence_wrathful);

        OreDictionary.registerOre("ingotBiomass", HSItems.biomass);
        OreDictionary.registerOre("ingotLivingmetal", HSItems.livingmetal_ingot);

        HarkenScythe.LOGGER.info("Registering recipes...");

        // Blood Altar
        // TODO: Better way to repair items, preferably like the absorbers
        HSAltarRecipes.addBloodRecipe(HSItems.biomass_seed, HSItems.germinated_biomass_seed, 5);
        HSAltarRecipes.addBloodRecipe(HSItems.blood_butcherer, HSItems.blood_butcherer, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_hood, HSItems.bloodweave_hood, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_robe, HSItems.bloodweave_robe, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_pants, HSItems.bloodweave_pants, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_shoes, HSItems.bloodweave_shoes, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.deadtime_watch, HSItems.deadtime_watch, 5);
        HSAltarRecipes.addBloodRecipe(HSItems.essence_trinket_blood, HSItems.essence_trinket_blood, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.essence_trinket_blood_ethereal, HSItems.essence_trinket_blood_ethereal, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.vampire_knife, HSItems.vampire_knife, 10);
        HSAltarRecipes.addBloodRecipe(Items.GLASS_BOTTLE, Items.DRAGON_BREATH, 20);
        HSAltarRecipes.addBloodRecipe(new ItemStack(Items.GOLDEN_APPLE, 1), new ItemStack(Items.GOLDEN_APPLE, 1, 1), 100); // Notch Apple
        HSAltarRecipes.addBloodRecipe(Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE, 1);
        HSAltarRecipes.addBloodRecipe(Items.POISONOUS_POTATO, Items.POTATO, 1);
        HSAltarRecipes.addBloodRecipe(Items.CHORUS_FRUIT_POPPED, Items.CHORUS_FRUIT, 1);
        HSAltarRecipes.addBloodRecipe(Items.ROTTEN_FLESH, Items.LEATHER, 1);
        HSAltarRecipes.addBloodRecipe("wool", Item.getItemFromBlock(HSBlocks.bloodweave_cloth), 5);
        HSAltarRecipes.addBloodRecipesConfig(HSConfig.RECIPES.customBloodAltarRecipes);

        // Soul Altar
        // TODO: Better way to repair items, preferably like the absorbers
        HSAltarRecipes.addSoulRecipe(HSItems.dimensional_mirror, HSItems.dimensional_mirror, 5);
        HSAltarRecipes.addSoulRecipe(HSItems.essence_trinket_soul, HSItems.essence_trinket_soul, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.essence_trinket_soul_ethereal, HSItems.essence_trinket_soul_ethereal, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.soulweave_hood, HSItems.soulweave_hood, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.soulweave_robe, HSItems.soulweave_robe, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.soulweave_pants, HSItems.soulweave_pants, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.soulweave_shoes, HSItems.soulweave_shoes, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.spectral_potion_flame, HSItems.spectral_potion_affliction, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.spectral_potion_water, HSItems.spectral_potion_purifying, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.refresh_tome, HSItems.refresh_tome, 5);
        HSAltarRecipes.addSoulRecipe(HSItems.shadow_book, HSItems.refresh_tome, 5);
        HSAltarRecipes.addSoulRecipe(Items.CAKE, HSItems.soul_cake, 10);
        HSAltarRecipes.addSoulRecipe(Items.COOKIE, HSItems.soul_cookie, 1);
        HSAltarRecipes.addSoulRecipe(HSItems.unpowered_totem_of_undying, Items.TOTEM_OF_UNDYING, 50);
        HSAltarRecipes.addSoulRecipe("ingotIron", HSItems.livingmetal_ingot, 5);
        HSAltarRecipes.addSoulRecipe("sand", Item.getItemFromBlock(Blocks.SOUL_SAND), 5);
        HSAltarRecipes.addSoulRecipe("wool", Item.getItemFromBlock(HSBlocks.soulweave_cloth), 5);
        HSAltarRecipes.addSoulRecipe("blockGlass", Item.getItemFromBlock(HSBlocks.spectral_glass), 1);
        HSAltarRecipes.addSoulRecipe("paneGlass", Item.getItemFromBlock(HSBlocks.spectral_glass_pane), 1);
        HSAltarRecipes.addSoulRecipe("potion", Items.EXPERIENCE_BOTTLE, 10);
        HSAltarRecipes.addSoulRecipesConfig(HSConfig.RECIPES.customSoulAltarRecipes);

        // Third Party Mod Integration
        if (Loader.isModLoaded("patchouli") && !HSConfig.GENERAL.disableGuidebook)
        {
            registry.register(new ShapelessOreRecipe(null, HSItems.reaper_guidebook, Items.BOOK, "essenceHarken").setRegistryName(HarkenScythe.MOD_ID, "reaper_guidebook"));
        }
    }

    @SubscribeEvent
    public static void registerSoundEvents(@Nonnull final RegistryEvent.Register<SoundEvent> event)
    {
        HarkenScythe.LOGGER.info("Registering sound events...");

        final IForgeRegistry<SoundEvent> registry = event.getRegistry();

        for (HSSoundEvents soundEvents : HSSoundEvents.values())
        {
            registry.register(soundEvents.getSoundEvent());
        }
    }

    public static void registerTileEntities()
    {
        HarkenScythe.LOGGER.info("Registering tile entities...");

        GameRegistry.registerTileEntity(HSTileEntityBloodAltar.class, new ResourceLocation(HarkenScythe.MOD_ID, "blood_altar"));
        GameRegistry.registerTileEntity(HSTileEntityBloodAbsorber.class, new ResourceLocation(HarkenScythe.MOD_ID, "blood_absorber"));
        GameRegistry.registerTileEntity(HSTileEntityCrucible.class, new ResourceLocation(HarkenScythe.MOD_ID, "crucible"));
        GameRegistry.registerTileEntity(HSTileEntityLivingmetalCore.class, new ResourceLocation(HarkenScythe.MOD_ID, "livingmetal_core"));
        GameRegistry.registerTileEntity(HSTileEntitySoulAltar.class, new ResourceLocation(HarkenScythe.MOD_ID, "soul_altar"));
        GameRegistry.registerTileEntity(HSTileEntitySoulAbsorber.class, new ResourceLocation(HarkenScythe.MOD_ID, "soul_absorber"));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(@Nonnull final ModelRegistryEvent event)
    {
        HarkenScythe.LOGGER.info("Registering entity renderers...");

        RenderingRegistry.registerEntityRenderingHandler(HSEntityBlood.class, new HSRendererEntityBlood.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityEctoglobin.class, new HSRendererEntityEctoglobin.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityExospider.class, new HSRendererEntityExospider.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityHarbinger.class, new HSRendererEntityHarbinger.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityHemoglobin.class, new HSRendererEntityHemoglobin.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySoul.class, new HSRendererEntitySoul.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySpectralHuman.class, new HSRendererEntitySpectralHuman.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySpectralMiner.class, new HSRendererEntitySpectralMiner.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySpectralPotion.class, new HSRendererEntitySpectralPotion.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityVampireKnife.class, new HSRendererEntityVampireKnife.Factory());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerTESRs(@Nonnull final RegistryEvent<Block> event)
    {
        HarkenScythe.LOGGER.info("Registering tile entity special renderers...");

        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntityBloodAbsorber.class, new HSRendererBlockAbsorber());
        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntityBloodAltar.class, new HSRendererBlockBloodAltar());
        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntitySoulAbsorber.class, new HSRendererBlockAbsorber());
        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntitySoulAltar.class, new HSRendererBlockSoulAltar());
    }
}
