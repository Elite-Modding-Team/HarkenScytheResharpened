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

@SuppressWarnings({"DataFlowIssue", "unused"})
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSBlocks
{
    public static final HSBlockMaterial biomass_block = null;
    public static final HSBlockMaterial livingmetal_block = null;

    public static final HSBlockSpectralGlass spectral_glass = null;
    public static final HSBlockSpectralGlass spectral_glass_inverted = null;
    public static final HSBlockSpectralGlassPane spectral_glass_pane = null;
    public static final HSBlockSpectralGlassPane spectral_glass_pane_inverted = null;

    public static final HSBlockCreep creep_block = null;
    public static final HSBlockCreep creep_block_tilled = null;
    public static final HSBlockCreep creep_block_tilled_bloodied = null;

    public static final HSBlockCloth bloodweave_cloth = null;
    public static final HSBlockCloth soulweave_cloth = null;

    public static final HSBlockBloodCrucible blood_crucible = null;
    public static final HSBlockSoulCrucible soul_crucible = null;

    public static final HSBlockBloodAltar blood_altar = null;
    public static final HSBlockBloodAbsorber blood_absorber = null;

    public static final HSBlockSoulAltar soul_altar = null;
    public static final HSBlockSoulAbsorber soul_absorber = null;

    public static final HSBlockSoulCake soul_cake = null;

    public static final HSBlockBiomassCrop biomass_crop = null;

    public static final HSBlockLivingmetalCore livingmetal_core = null;

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
                HSRegistry.setup(new HSBlockBloodAbsorber(), "blood_absorber").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSoulCrucible(), "soul_crucible").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSoulAbsorber(), "soul_absorber").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockBloodAltar(), "blood_altar").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSoulAltar(), "soul_altar").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockSoulCake(), "soul_cake"),
                HSRegistry.setup(new HSBlockBiomassCrop(), "biomass_crop").setCreativeTab(null),
                HSRegistry.setup(new HSBlockLivingmetalCore(), "livingmetal_core").setCreativeTab(HarkenScythe.TAB)
            );
    }
}
