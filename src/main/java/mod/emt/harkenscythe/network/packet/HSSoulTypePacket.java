package mod.emt.harkenscythe.network.packet;

import io.netty.buffer.ByteBuf;
import mod.emt.harkenscythe.entity.HSEntitySoul;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// As soul entities are solely created on the server-sided world via LivingDeathEvent, their soul types need to be synced to the client for rendering
public class HSSoulTypePacket implements IMessage
{
    private int entityId;
    private int soulType;

    public HSSoulTypePacket()
    {
    }

    public HSSoulTypePacket(int entityId, int soulType)
    {
        this.entityId = entityId;
        this.soulType = soulType;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        entityId = buf.readInt();
        soulType = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(entityId);
        buf.writeInt(soulType);
    }

    public static class Handler implements IMessageHandler<HSSoulTypePacket, IMessage>
    {
        @Override
        public IMessage onMessage(HSSoulTypePacket message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if (world != null)
                {
                    Entity entity = world.getEntityByID(message.entityId);
                    if (entity instanceof HSEntitySoul)
                    {
                        HSEntitySoul soulEntity = (HSEntitySoul) entity;
                        soulEntity.getDataManager().set(HSEntitySoul.SOUL_TYPE, message.soulType);
                    }
                }
            });
            return null;
        }
    }
}
