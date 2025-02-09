package mod.emt.harkenscythe.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
import mod.emt.harkenscythe.tileentity.HSTileEntityCrucible;

// As crucible essence count changes are solely handled on the server-sided world to prevent desyncs, the new essence counts need to be synced to the client for rendering
public class HSEssenceSyncPacket implements IMessage
{
    private int essenceCount;
    private BlockPos pos;

    public HSEssenceSyncPacket()
    {
    }

    public HSEssenceSyncPacket(int essenceCount, BlockPos pos)
    {
        this.essenceCount = essenceCount;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.essenceCount = buf.readInt();
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.essenceCount);
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
    }

    public static class Handler implements IMessageHandler<HSEssenceSyncPacket, IMessage>
    {
        @Override
        public IMessage onMessage(HSEssenceSyncPacket message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if (world != null)
                {
                    TileEntity tile = world.getTileEntity(message.pos);
                    if (tile instanceof HSTileEntityCrucible)
                    {
                        HSTileEntityCrucible crucible = ((HSTileEntityCrucible) tile);
                        crucible.setEssenceCountClient(message.essenceCount);
                    }
                }
            });
            return null;
        }
    }
}
