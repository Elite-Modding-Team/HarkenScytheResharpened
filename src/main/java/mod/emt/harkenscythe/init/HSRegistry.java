package mod.emt.harkenscythe.init;

import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.render.HSSoulAltarRender;
import mod.emt.harkenscythe.entities.HSSoul;
import mod.emt.harkenscythe.entities.render.HSSoulRender;
import mod.emt.harkenscythe.tileentities.HSSoulAltarTE;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
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
            EntityEntryBuilder.create()
                .entity(HSSoul.class)
                .id(new ResourceLocation(HarkenScythe.MOD_ID, "soul"), id++)
                .name("soul")
                .tracker(64, 1, true)
                .build()
        );
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(HSSoulAltarTE.class, new ResourceLocation(HarkenScythe.MOD_ID, "soul_altar"));
    }

    public static void registerRecipes()
    {
        HSSoulAltarRecipes.addRecipe(Items.CAKE, HSItems.soul_cake, 10);
        HSSoulAltarRecipes.addRecipe(Items.COOKIE, HSItems.soul_cookie, 10);
        HSSoulAltarRecipes.addRecipe(Items.GLASS_BOTTLE, Items.EXPERIENCE_BOTTLE, 20);
        HSSoulAltarRecipes.addRecipe(Items.IRON_INGOT, HSItems.livingmetal_ingot, 10);
        HSSoulAltarRecipes.addRecipe(new ItemStack(Blocks.SAND).getItem(), new ItemStack(Blocks.SOUL_SAND).getItem(), 10); // TODO: OreDictionary
        HSSoulAltarRecipes.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER).getItem(), Items.DRAGON_BREATH, 30); // TODO: Move this to blood
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(HSSoul.class, new HSSoulRender.Factory());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerTESRs(RegistryEvent<Block> event)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(HSSoulAltarTE.class, new HSSoulAltarRender());
    }
}
