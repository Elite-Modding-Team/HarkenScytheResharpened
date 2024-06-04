package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.items.HSItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSItems
{
    @GameRegistry.ObjectHolder("item")
    public static HSItem item;

    @SubscribeEvent
    public static void onRegisterItemsEvent(@Nonnull final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();

        // ITEMS
        registry.registerAll
            (
                HSRegistry.setup(new HSItem(), "item")
            );

        // ITEM BLOCKS
        ForgeRegistries.BLOCKS.getValues().stream()
            .filter(block -> block.getRegistryName().getNamespace().equals(HarkenScythe.MOD_ID))
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
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
            }
        }
    }
}
