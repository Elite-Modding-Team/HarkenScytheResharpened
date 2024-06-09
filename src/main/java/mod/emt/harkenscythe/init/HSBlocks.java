package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.blocks.HSBloodCrucible;
import mod.emt.harkenscythe.blocks.HSSoulAltar;
import mod.emt.harkenscythe.blocks.HSSoulCake;
import mod.emt.harkenscythe.blocks.HSSoulCrucible;
import net.minecraft.block.Block;
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
    @GameRegistry.ObjectHolder("blood_crucible")
    public static HSBloodCrucible blood_crucible;
    @GameRegistry.ObjectHolder("soul_crucible")
    public static HSSoulCrucible soul_crucible;
    @GameRegistry.ObjectHolder("soul_altar")
    public static HSSoulAltar soul_altar;
    
    @GameRegistry.ObjectHolder("soul_cake")
    public static HSSoulCake soul_cake;

    @SubscribeEvent
    public static void onRegisterBlocksEvent(@Nonnull final RegistryEvent.Register<Block> event)
    {
        final IForgeRegistry<Block> registry = event.getRegistry();

        // BLOCKS
        registry.registerAll
            (
                HSRegistry.setup(new HSBloodCrucible(), "blood_crucible").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSoulCrucible(), "soul_crucible").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSoulAltar(), "soul_altar").setCreativeTab(HarkenScythe.TAB),
                HSRegistry.setup(new HSSoulCake(), "soul_cake")
            );
    }
}
