package mod.emt.harkenscythe.init;

import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.renderers.RenderHSBloodAltar;
import mod.emt.harkenscythe.client.renderers.RenderHSEctoglobin;
import mod.emt.harkenscythe.client.renderers.RenderHSEntityBlood;
import mod.emt.harkenscythe.client.renderers.RenderHSEntityHarbinger;
import mod.emt.harkenscythe.client.renderers.RenderHSEntitySoul;
import mod.emt.harkenscythe.client.renderers.RenderHSHemoglobin;
import mod.emt.harkenscythe.client.renderers.RenderHSSoulAltar;
import mod.emt.harkenscythe.entities.HSEntityBlood;
import mod.emt.harkenscythe.entities.HSEntityEctoglobin;
import mod.emt.harkenscythe.entities.HSEntityHarbinger;
import mod.emt.harkenscythe.entities.HSEntityHemoglobin;
import mod.emt.harkenscythe.entities.HSEntitySoul;
import mod.emt.harkenscythe.entities.HSEntitySpectralPotion;
import mod.emt.harkenscythe.tileentities.HSTileEntityBloodAltar;
import mod.emt.harkenscythe.tileentities.HSTileEntityCrucible;
import mod.emt.harkenscythe.tileentities.HSTileEntityLivingmetalCore;
import mod.emt.harkenscythe.tileentities.HSTileEntitySoulAltar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSRegistry
{
    private static int id = 1;

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

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        registerEntity("harbinger", HSEntityHarbinger.class, 2829099, 14079702);

        registerEntity("blood", HSEntityBlood.class);
        registerEntity("soul", HSEntitySoul.class);

        registerEntity("ectoglobin", HSEntityEctoglobin.class, 2304558, 14870762);
        registerEntity("hemoglobin", HSEntityHemoglobin.class, 3084561, 15455958);

        registerEntity("spectral_potion", HSEntitySpectralPotion.class);
    }

    public static void registerEntity(String name, Class<? extends Entity> clazz, int eggColor1, int eggColor2)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(HarkenScythe.MOD_ID, name), clazz, HarkenScythe.MOD_ID + "." + name, id++, HarkenScythe.instance, 64,
            1, true, eggColor1, eggColor2);
    }

    public static void registerEntity(String name, Class<? extends Entity> clazz)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(HarkenScythe.MOD_ID, name), clazz, HarkenScythe.MOD_ID + "." + name, id++, HarkenScythe.instance, 64,
            1, true);
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(HSTileEntityBloodAltar.class, new ResourceLocation(HarkenScythe.MOD_ID, "blood_altar"));
        GameRegistry.registerTileEntity(HSTileEntityCrucible.class, new ResourceLocation(HarkenScythe.MOD_ID, "crucible"));
        GameRegistry.registerTileEntity(HSTileEntityLivingmetalCore.class, new ResourceLocation(HarkenScythe.MOD_ID, "livingmetal_core"));
        GameRegistry.registerTileEntity(HSTileEntitySoulAltar.class, new ResourceLocation(HarkenScythe.MOD_ID, "soul_altar"));
    }

    public static void registerRecipes()
    {
        HSAltarRecipes.addBloodRecipe(HSItems.biomass_seed, HSItems.germinated_biomass_seed, 20);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_hood, HSItems.bloodweave_hood, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_robe, HSItems.bloodweave_robe, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_pants, HSItems.bloodweave_pants, 10);
        HSAltarRecipes.addBloodRecipe(HSItems.bloodweave_shoes, HSItems.bloodweave_shoes, 10);
        HSAltarRecipes.addBloodRecipe(Items.GLASS_BOTTLE, Items.DRAGON_BREATH, 40);
        HSAltarRecipes.addBloodRecipe("wool", Item.getItemFromBlock(HSBlocks.bloodweave_cloth), 10);

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
        //HSAltarRecipes.addSoulRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD).getItem(), Items.EXPERIENCE_BOTTLE, 40); // TODO: Something better for this?
        HSAltarRecipes.addSoulRecipe("ingotIron", HSItems.livingmetal_ingot, 10);
        HSAltarRecipes.addSoulRecipe("sand", Item.getItemFromBlock(Blocks.SOUL_SAND), 10);
        HSAltarRecipes.addSoulRecipe("wool", Item.getItemFromBlock(HSBlocks.soulweave_cloth), 10);
        HSAltarRecipes.addSoulRecipe("blockGlass", Item.getItemFromBlock(HSBlocks.spectral_glass), 5);
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event)
    {
        event.getRegistry().registerAll(
            HSEnchantments.BLOODLETTING,
            HSEnchantments.EXUDE,
            HSEnchantments.NOURISHMENT,
            HSEnchantments.SOULSTEAL
        );
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().registerAll(
            HSPotions.AFFLICTION,
            HSPotions.FLAME,
            HSPotions.PURIFYING,
            HSPotions.WATER
        );
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(HSEntityBlood.class, new RenderHSEntityBlood.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityEctoglobin.class, new RenderHSEctoglobin.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityHarbinger.class, new RenderHSEntityHarbinger.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntityHemoglobin.class, new RenderHSHemoglobin.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySoul.class, new RenderHSEntitySoul.Factory());
        // TODO: Render respective potions instead of bottle
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySpectralPotion.class, manager -> new RenderSnowball<>(manager, HSItems.spectral_glass_bottle, Minecraft.getMinecraft().getRenderItem()));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerTESRs(RegistryEvent<Block> event)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntityBloodAltar.class, new RenderHSBloodAltar());
        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntitySoulAltar.class, new RenderHSSoulAltar());
    }
}
