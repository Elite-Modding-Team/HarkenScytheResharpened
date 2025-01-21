package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.block.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSBlocks
{
    @GameRegistry.ObjectHolder("biomass_block")
    public static HSBlockMaterial biomass_block;
    @GameRegistry.ObjectHolder("biomass_crop")
    public static HSBlockBiomassCrop biomass_crop;

    @GameRegistry.ObjectHolder("livingmetal_block")
    public static HSBlockMaterial livingmetal_block;
    @GameRegistry.ObjectHolder("livingmetal_core")
    public static HSBlockLivingmetalCore livingmetal_core;

    @GameRegistry.ObjectHolder("spectral_glass")
    public static HSBlockSpectralGlass spectral_glass;
    @GameRegistry.ObjectHolder("spectral_glass_inverted")
    public static HSBlockSpectralGlass spectral_glass_inverted;
    @GameRegistry.ObjectHolder("spectral_glass_pane")
    public static HSBlockSpectralGlassPane spectral_glass_pane;
    @GameRegistry.ObjectHolder("spectral_glass_pane_inverted")
    public static HSBlockSpectralGlassPane spectral_glass_pane_inverted;

    @GameRegistry.ObjectHolder("creep_block")
    public static HSBlockCreep creep_block;
    @GameRegistry.ObjectHolder("creep_block_tilled")
    public static HSBlockCreep creep_block_tilled;
    @GameRegistry.ObjectHolder("creep_block_tilled_bloodied")
    public static HSBlockCreep creep_block_tilled_bloodied;

    @GameRegistry.ObjectHolder("bloodweave_cloth")
    public static HSBlockCloth bloodweave_cloth;
    @GameRegistry.ObjectHolder("soulweave_cloth")
    public static HSBlockCloth soulweave_cloth;

    @GameRegistry.ObjectHolder("blood_altar")
    public static HSBlockBloodAltar blood_altar;
    @GameRegistry.ObjectHolder("soul_altar")
    public static HSBlockSoulAltar soul_altar;
    @GameRegistry.ObjectHolder("blood_crucible")
    public static HSBlockBloodCrucible blood_crucible;
    @GameRegistry.ObjectHolder("soul_crucible")
    public static HSBlockSoulCrucible soul_crucible;

    @GameRegistry.ObjectHolder("soul_cake")
    public static HSBlockSoulCake soul_cake;

    @SubscribeEvent
    public static void onRegisterBlocksEvent(@Nonnull final RegistryEvent.Register<Block> event)
    {
        HarkenScythe.LOGGER.info("Registering blocks...");

        final IForgeRegistry<Block> registry = event.getRegistry();

        // BLOCKS
        registry.registerAll
            (
                HSRegistry.setup(new HSBlockMaterial(Material.ROCK, MapColor.NETHERRACK, 5.0F, 5.0F, HSSoundTypes.BIOMASS), "biomass_block").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockMaterial(Material.IRON, MapColor.DIAMOND, 5.0F, 10.0F, HSSoundTypes.LIVINGMETAL), "livingmetal_block").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSpectralGlass(false), "spectral_glass").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSpectralGlass(true), "spectral_glass_inverted").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSpectralGlassPane(false), "spectral_glass_pane").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSpectralGlassPane(true), "spectral_glass_pane_inverted").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCreep(), "creep_block").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCreep(), "creep_block_tilled").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCreep(), "creep_block_tilled_bloodied").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCloth(MapColor.RED), "bloodweave_cloth").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCloth(MapColor.LIGHT_BLUE), "soulweave_cloth").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockBloodCrucible(), "blood_crucible").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSoulCrucible(), "soul_crucible").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockBloodAltar(), "blood_altar").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSoulAltar(), "soul_altar").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSoulCake(), "soul_cake"),
                HSRegistry.setup(new HSBlockBiomassCrop(), "biomass_crop").setCreativeTab(null),
                HSRegistry.setup(new HSBlockLivingmetalCore(), "livingmetal_core").setCreativeTab(HarkenScythe.TAB)
            );
    }
}
