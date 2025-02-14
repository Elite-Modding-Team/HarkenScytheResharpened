package mod.emt.harkenscythe.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
import mod.emt.harkenscythe.entity.HSEntityBlood;
import mod.emt.harkenscythe.entity.HSEntitySoul;

// As essence entities are solely created on the server-sided world via LivingDeathEvent, their essence types need to be synced to the client for rendering
public class HSEssenceTypePacket implements IMessage
{
    private int entityId;
    private int essenceType;

    public HSEssenceTypePacket()
    {
    }

    public HSEssenceTypePacket(int entityId, int essenceType)
    {
        this.entityId = entityId;
        this.essenceType = essenceType;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        entityId = buf.readInt();
        essenceType = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(entityId);
        buf.writeInt(essenceType);
    }

    public static class Handler implements IMessageHandler<HSEssenceTypePacket, IMessage>
    {
        @Override
        public IMessage onMessage(HSEssenceTypePacket message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if (world != null)
                {
                    Entity entity = world.getEntityByID(message.entityId);
                    if (entity instanceof HSEntitySoul)
                    {
                        HSEntitySoul soulEntity = (HSEntitySoul) entity;
                        soulEntity.getDataManager().set(HSEntitySoul.SOUL_TYPE, message.essenceType);
                    }
                    else if (entity instanceof HSEntityBlood)
                    {
                        HSEntityBlood bloodEntity = (HSEntityBlood) entity;
                        bloodEntity.getDataManager().set(HSEntityBlood.BLOOD_TYPE, message.essenceType);
                    }
                }
            });
            return null;
        }
    }
}
