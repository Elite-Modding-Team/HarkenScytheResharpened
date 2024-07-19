package mod.emt.harkenscythe.network;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.network.packet.HSSoulTypePacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class HSNetworkHandler
{
    public static SimpleNetworkWrapper instance;

    public static void registerPackets()
    {
        int id = 0;
        instance = NetworkRegistry.INSTANCE.newSimpleChannel(HarkenScythe.MOD_ID);
        instance.registerMessage(HSSoulTypePacket.Handler.class, HSSoulTypePacket.class, id++, Side.CLIENT);
    }
}
