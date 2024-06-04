package mod.emt.harkenscythe.init;

import javax.annotation.Nonnull;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.blocks.HSBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSBlocks
{
    @GameRegistry.ObjectHolder("block")
    public static HSBlock block;

    @SubscribeEvent
    public static void onRegisterBlocksEvent(@Nonnull final RegistryEvent.Register<Block> event)
    {
        final IForgeRegistry<Block> registry = event.getRegistry();

        // BLOCKS
        registry.registerAll
            (
                HSRegistry.setup(new HSBlock(), "block")
            );
    }
}
