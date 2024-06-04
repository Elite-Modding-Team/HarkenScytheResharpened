package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.items.HSEssenceKeeper;
import mod.emt.harkenscythe.items.HSEssenceKeeperBlood;
import mod.emt.harkenscythe.items.HSEssenceKeeperSoul;
import mod.emt.harkenscythe.items.HSEssenceVessel;
import mod.emt.harkenscythe.items.HSEssenceVesselBlood;
import mod.emt.harkenscythe.items.HSEssenceVesselSoul;
import mod.emt.harkenscythe.items.HSScythe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
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

    @SubscribeEvent
    public static void onRegisterItemsEvent(@Nonnull final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();

        // ITEMS
        registry.registerAll
                (
                        HSRegistry.setup(new HSEssenceKeeper(), "essence_keeper"),
                        HSRegistry.setup(new HSEssenceKeeperBlood(), "essence_keeper_blood"),
                        HSRegistry.setup(new HSEssenceKeeperSoul(), "essence_keeper_soul"),
                        HSRegistry.setup(new HSEssenceVessel(), "essence_vessel"),
                        HSRegistry.setup(new HSEssenceVesselBlood(), "essence_vessel_blood"),
                        HSRegistry.setup(new HSEssenceVesselSoul(), "essence_vessel_soul"),
                        HSRegistry.setup(new HSScythe(ToolMaterial.WOOD, 1.4F), "wooden_scythe"),
                        HSRegistry.setup(new HSScythe(ToolMaterial.STONE, 1.4F), "stone_scythe"),
                        HSRegistry.setup(new HSScythe(ToolMaterial.IRON, 1.4F), "iron_scythe"),
                        HSRegistry.setup(new HSScythe(ToolMaterial.GOLD, 1.4F), "golden_scythe"),
                        HSRegistry.setup(new HSScythe(ToolMaterial.DIAMOND, 1.4F), "diamond_scythe")
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
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }
}
