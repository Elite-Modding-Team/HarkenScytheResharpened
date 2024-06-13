package mod.emt.harkenscythe.init;

import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.renderers.RenderHSBloodAltar;
import mod.emt.harkenscythe.client.renderers.RenderHSEntityBlood;
import mod.emt.harkenscythe.client.renderers.RenderHSEntitySoul;
import mod.emt.harkenscythe.client.renderers.RenderHSSoulAltar;
import mod.emt.harkenscythe.entities.HSEntityBlood;
import mod.emt.harkenscythe.entities.HSEntitySoul;
import mod.emt.harkenscythe.tileentities.HSTileEntityBloodAltar;
import mod.emt.harkenscythe.tileentities.HSTileEntitySoulAltar;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSRegistry
{
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
        int id = 1;
        event.getRegistry().registerAll(
            EntityEntryBuilder.create().entity(HSEntityBlood.class).id(new ResourceLocation(HarkenScythe.MOD_ID, "blood"), id++).name(HarkenScythe.MOD_ID + ".blood").tracker(64, 1, true).build(),
            EntityEntryBuilder.create().entity(HSEntitySoul.class).id(new ResourceLocation(HarkenScythe.MOD_ID, "soul"), id++).name(HarkenScythe.MOD_ID + ".soul").tracker(64, 1, true).build()
        );
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(HSTileEntityBloodAltar.class, new ResourceLocation(HarkenScythe.MOD_ID, "blood_altar"));
        GameRegistry.registerTileEntity(HSTileEntitySoulAltar.class, new ResourceLocation(HarkenScythe.MOD_ID, "soul_altar"));
    }

    public static void registerRecipes()
    {
        HSAltarRecipes.addBloodRecipe(HSItems.biomass_seed, HSItems.germinated_biomass_seed, 20);
        HSAltarRecipes.addBloodRecipe(Items.GLASS_BOTTLE, Items.DRAGON_BREATH, 40);
        HSAltarRecipes.addBloodRecipe("wool", Item.getItemFromBlock(HSBlocks.soulweave_cloth), 10);

        HSAltarRecipes.addSoulRecipe(Items.CAKE, HSItems.soul_cake, 10);
        HSAltarRecipes.addSoulRecipe(Items.COOKIE, HSItems.soul_cookie, 10);
        //HSAltarRecipes.addSoulRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD).getItem(), Items.EXPERIENCE_BOTTLE, 40); // TODO: Something better for this?
        HSAltarRecipes.addSoulRecipe("ingotIron", HSItems.livingmetal_ingot, 10);
        HSAltarRecipes.addSoulRecipe("sand", Item.getItemFromBlock(Blocks.SOUL_SAND), 10);
        HSAltarRecipes.addSoulRecipe("wool", Item.getItemFromBlock(HSBlocks.soulweave_cloth), 10);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(HSEntityBlood.class, new RenderHSEntityBlood.Factory());
        RenderingRegistry.registerEntityRenderingHandler(HSEntitySoul.class, new RenderHSEntitySoul.Factory());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerTESRs(RegistryEvent<Block> event)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntityBloodAltar.class, new RenderHSBloodAltar());
        ClientRegistry.bindTileEntitySpecialRenderer(HSTileEntitySoulAltar.class, new RenderHSSoulAltar());
    }
}
