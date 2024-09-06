package mod.emt.harkenscythe.init;

import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.advancement.HSAdvancementTrigger;
import mod.emt.harkenscythe.client.renderer.*;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.*;
import mod.emt.harkenscythe.tileentity.HSTileEntityBloodAltar;
import mod.emt.harkenscythe.tileentity.HSTileEntityCrucible;
import mod.emt.harkenscythe.tileentity.HSTileEntityLivingmetalCore;
import mod.emt.harkenscythe.tileentity.HSTileEntitySoulAltar;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSRegistry
{
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
        EntityRegistry.registerModEntity(new ResourceLocation(HarkenScythe.MOD_ID, name), clazz, HarkenScythe.MOD_ID + "." + name, entityID++, HarkenScythe.instance, 64,
            1, true, eggColor1, eggColor2);
    }

    public static void registerEntity(String name, Class<? extends Entity> clazz)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(HarkenScythe.MOD_ID, name), clazz, HarkenScythe.MOD_ID + "." + name, entityID++, HarkenScythe.instance, 64,
            1, true);
    }

    public static void registerAdvancements()
    {
        for (HSAdvancementTrigger trigger : HSAdvancements.TRIGGER_ARRAY)
        {
            CriteriaTriggers.register(trigger);
        }
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event)
    {
        HarkenScythe.LOGGER.info("Registering enchantments...");

        event.getRegistry().registerAll(
            HSEnchantments.BLOODLETTING,
            HSEnchantments.EXUDE,
            HSEnchantments.NOURISHMENT,
            HSEnchantments.SOULSTEAL
        );
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
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

        registerEntitySpawns();
    }

    public static void registerEntitySpawns()
    {
        List<Biome> regularSpawning = Lists.newArrayList();
        for (Biome biome : Biome.REGISTRY)
        {
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);
            if (!types.contains(BiomeDictionary.Type.MUSHROOM) && !types.contains(BiomeDictionary.Type.WATER) && !types.contains(BiomeDictionary.Type.NETHER) && !types.contains(BiomeDictionary.Type.END))
            {
                regularSpawning.add(biome);
                if (HSConfig.GENERAL.debugMode) HarkenScythe.LOGGER.debug("Biome " + biome.getBiomeName() + " is valid for regular spawning");
            }
        }

        EntityRegistry.addSpawn(HSEntityHarbinger.class, 5, 1, 1, EnumCreatureType.MONSTER, regularSpawning.toArray(new Biome[0]));
        EntityRegistry.addSpawn(HSEntitySpectralMiner.class, 2, 1, 1, EnumCreatureType.MONSTER, regularSpawning.toArray(new Biome[0]));

        EntitySpawnPlacementRegistry.setPlacementType(HSEntityHarbinger.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(HSEntitySpectralMiner.class, EntityLiving.SpawnPlacementType.ON_GROUND);
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event)
    {
        HarkenScythe.LOGGER.info("Registering potions...");

        event.getRegistry().registerAll(
            HSPotions.AFFLICTION,
            HSPotions.FLAME,
            HSPotions.PURIFYING,
            HSPotions.WATER
        );
    }

    public static void registerRecipes()
    {
        HarkenScythe.LOGGER.info("Registering altar recipes and ore dictionary entries...");

        // Blood Altar
        HSAltarRecipes.addBloodRecipe(HSItems.biomass_seed, HSItems.germinated_biomass_seed, 20);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_hood, HSItems.bloodweave_hood, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_robe, HSItems.bloodweave_robe, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_pants, HSItems.bloodweave_pants, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_shoes, HSItems.bloodweave_shoes, 10);
        HSAltarRecipes.addBloodRecipe(Items.GLASS_BOTTLE, Items.DRAGON_BREATH, 40);
        HSAltarRecipes.addBloodRecipe("wool", Item.getItemFromBlock(HSBlocks.bloodweave_cloth), 10);
        HSAltarRecipes.addBloodRecipes(HSConfig.RECIPES.customBloodAltarRecipes);

        // Soul Altar
        HSAltarRecipes.addSoulRecipe(HSItems.dimensional_mirror, HSItems.dimensional_mirror, 5);
        HSAltarRecipes.addSoulRecipe(HSItems.soulweave_hood, HSItems.soulweave_hood, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.soulweave_robe, HSItems.soulweave_robe, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.soulweave_pants, HSItems.soulweave_pants, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.soulweave_shoes, HSItems.soulweave_shoes, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.spectral_potion_flame, HSItems.spectral_potion_affliction, 10);
        HSAltarRecipes.addSoulRecipe(HSItems.spectral_potion_water, HSItems.spectral_potion_purifying, 10);
        HSAltarRecipes.addSoulRecipe(Item.getItemFromBlock(HSBlocks.spectral_glass), Item.getItemFromBlock(HSBlocks.spectral_glass_inverted), 1);
        HSAltarRecipes.addSoulRecipe(Item.getItemFromBlock(HSBlocks.spectral_glass_pane), Item.getItemFromBlock(HSBlocks.spectral_glass_pane_inverted), 1);
        HSAltarRecipes.addSoulRecipe(Item.getItemFromBlock(HSBlocks.spectral_glass_inverted), Item.getItemFromBlock(HSBlocks.spectral_glass), 1);
        HSAltarRecipes.addSoulRecipe(Item.getItemFromBlock(HSBlocks.spectral_glass_pane_inverted), Item.getItemFromBlock(HSBlocks.spectral_glass_pane), 1);
        HSAltarRecipes.addSoulRecipe(Items.CAKE, HSItems.soul_cake, 10);
        HSAltarRecipes.addSoulRecipe(Items.COOKIE, HSItems.soul_cookie, 5);
        HSAltarRecipes.addSoulRecipe(HSItems.unpowered_totem_of_undying, Items.TOTEM_OF_UNDYING, 100);
        //HSAltarRecipes.addSoulRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD).getItem(), Items.EXPERIENCE_BOTTLE, 40); // TODO: Something better for this?
        HSAltarRecipes.addSoulRecipe("ingotIron", HSItems.livingmetal_ingot, 10);
        HSAltarRecipes.addSoulRecipe("sand", Item.getItemFromBlock(Blocks.SOUL_SAND), 10);
        HSAltarRecipes.addSoulRecipe("wool", Item.getItemFromBlock(HSBlocks.soulweave_cloth), 10);
        HSAltarRecipes.addSoulRecipe("blockGlass", Item.getItemFromBlock(HSBlocks.spectral_glass), 5);
        HSAltarRecipes.addSoulRecipes(HSConfig.RECIPES.customSoulAltarRecipes);

        // Ore Dictionary
        OreDictionary.registerOre("essenceHarken", new ItemStack(HSItems.blood_essence));
        OreDictionary.registerOre("essenceHarken", new ItemStack(HSItems.soul_essence));
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event)
    {
        HarkenScythe.LOGGER.info("Registering sound events...");

        for (HSSoundEvents soundEvents : HSSoundEvents.values())
        {
            event.getRegistry().register(soundEvents.getSoundEvent());
        }
    }

    public static void registerTileEntities()
    {
        HarkenScythe.LOGGER.info("Registering tile entities...");

        GameRegistry.registerTileEntity(HSTileEntityBloodAltar.class, new ResourceLocation(HarkenScythe.MOD_ID, "blood_altar"));
        GameRegistry.registerTileEntity(HSTileEntityCrucible.class, new ResourceLocation(HarkenScythe.MOD_ID, "crucible"));
        GameRegistry.registerTileEntity(HSTileEntityLivingmetalCore.class, new ResourceLocation(HarkenScythe.MOD_ID, "livingmetal_core"));
        GameRegistry.registerTileEntity(HSTileEntitySoulAltar.class, new ResourceLocation(HarkenScythe.MOD_ID, "soul_altar"));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(ModelRegistryEvent event)
    {
        HarkenScythe.LOGGER.info("Registering entity renderers...");

        RenderingRegistry.registerEntityRenderingHandler(HSEntityBlood.class, new HSRendererEntityBlood.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityEctoglobin.class, new HSRendererEntityEctoglobin.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityHarbinger.class, new HSRendererEntityHarbinger.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityHemoglobin.class, new HSRendererEntityHemoglobin.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySoul.class, new HSRendererEntitySoul.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySpectralHuman.class, new HSRendererEntitySpectralHuman.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySpectralMiner.class, new HSRendererEntitySpectralMiner.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySpectralPotion.class, new HSRendererEntitySpectralPotion.Factory());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerTESRs(RegistryEvent<Block> event)
    {
        HarkenScythe.LOGGER.info("Registering tile entity special renderers...");

        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntityBloodAltar.class, new HSRendererBlockBloodAltar());
        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntitySoulAltar.class, new HSRendererBlockSoulAltar());
    }
}
