package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.blocks.HSBiomassCrop;
import mod.emt.harkenscythe.blocks.HSBlockCloth;
import mod.emt.harkenscythe.blocks.HSBlockCreep;
import mod.emt.harkenscythe.blocks.HSBlockMaterial;
import mod.emt.harkenscythe.blocks.HSBloodAltar;
import mod.emt.harkenscythe.blocks.HSBloodCrucible;
import mod.emt.harkenscythe.blocks.HSSoulAltar;
import mod.emt.harkenscythe.blocks.HSSoulCake;
import mod.emt.harkenscythe.blocks.HSSoulCrucible;
import mod.emt.harkenscythe.blocks.HSSpectralGlass;
import mod.emt.harkenscythe.blocks.HSSpectralGlassPane;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSBlocks
{
    @GameRegistry.ObjectHolder("biomass_block")
    public static HSBlockMaterial biomass_block;
    @GameRegistry.ObjectHolder("livingmetal_block")
    public static HSBlockMaterial livingmetal_block;

    @GameRegistry.ObjectHolder("spectral_glass")
    public static HSSpectralGlass spectral_glass;
    @GameRegistry.ObjectHolder("spectral_glass_inverted")
    public static HSSpectralGlass spectral_glass_inverted;
    @GameRegistry.ObjectHolder("spectral_glass_pane")
    public static HSSpectralGlassPane spectral_glass_pane;
    @GameRegistry.ObjectHolder("spectral_glass_pane_inverted")
    public static HSSpectralGlassPane spectral_glass_pane_inverted;

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
    public static HSBloodAltar blood_altar;
    @GameRegistry.ObjectHolder("soul_altar")
    public static HSSoulAltar soul_altar;
    @GameRegistry.ObjectHolder("blood_crucible")
    public static HSBloodCrucible blood_crucible;
    @GameRegistry.ObjectHolder("soul_crucible")
    public static HSSoulCrucible soul_crucible;

    @GameRegistry.ObjectHolder("soul_cake")
    public static HSSoulCake soul_cake;

    @GameRegistry.ObjectHolder("biomass_crop")
    public static HSBiomassCrop biomass_crop;

    @SubscribeEvent
    public static void onRegisterBlocksEvent(@Nonnull final RegistryEvent.Register<Block> event)
    {
        final IForgeRegistry<Block> registry = event.getRegistry();

        // BLOCKS
        registry.registerAll
            (
                HSRegistry.setup(new HSBlockMaterial(Material.ROCK, MapColor.NETHERRACK, 5.0F, 5.0F, HSSoundTypes.BIOMASS), "biomass_block").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockMaterial(Material.IRON, MapColor.DIAMOND, 5.0F, 10.0F, HSSoundTypes.LIVINGMETAL), "livingmetal_block").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSpectralGlass(false), "spectral_glass").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSpectralGlass(true), "spectral_glass_inverted").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSpectralGlassPane(false), "spectral_glass_pane").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSpectralGlassPane(true), "spectral_glass_pane_inverted").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCreep(), "creep_block").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCreep(), "creep_block_tilled").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCreep(), "creep_block_tilled_bloodied").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCloth(MapColor.RED), "bloodweave_cloth").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBlockCloth(MapColor.LIGHT_BLUE), "soulweave_cloth").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBloodCrucible(), "blood_crucible").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSoulCrucible(), "soul_crucible").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSBloodAltar(), "blood_altar").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSoulAltar(), "soul_altar").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSoulCake(), "soul_cake"),
                HSRegistry.setup(new HSBiomassCrop(), "biomass_crop")
            );
    }
}
