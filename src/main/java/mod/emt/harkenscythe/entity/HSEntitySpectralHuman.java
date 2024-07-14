package mod.emt.harkenscythe.entity;

import java.util.UUID;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class HSEntitySpectralHuman extends EntityMob implements IEntityAdditionalSpawnData
{
    private GameProfile playerGameProfile;
    private NetworkPlayerInfo networkPlayerInfo;

    public HSEntitySpectralHuman(World world)
    {
        super(world);
        this.setSize(0.6F, 1.95F);
        this.setCustomNameTag("Spectral Human");
    }

    public HSEntitySpectralHuman(World world, GameProfile playerGameProfile)
    {
        super(world);
        this.setSize(0.6F, 1.95F);
        this.setCustomNameTag("Spectral " + playerGameProfile.getName());
        this.setPlayerGameProfile(playerGameProfile);
        this.setNetworkPlayerInfo(new NetworkPlayerInfo(playerGameProfile));
    }

    public GameProfile getPlayerGameProfile()
    {
        return this.playerGameProfile;
    }

    public void setPlayerGameProfile(GameProfile playerGameProfile)
    {
        this.playerGameProfile = playerGameProfile;
    }

    public NetworkPlayerInfo getNetworkPlayerInfo()
    {
        return this.networkPlayerInfo;
    }

    public void setNetworkPlayerInfo(NetworkPlayerInfo networkPlayerInfo)
    {
        this.networkPlayerInfo = networkPlayerInfo;
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        if (this.getPlayerGameProfile() != null)
        {
            ByteBufUtils.writeUTF8String(data, this.getPlayerGameProfile().getId().toString());
            ByteBufUtils.writeUTF8String(data, this.getPlayerGameProfile().getName());
        }
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        if (this.getPlayerGameProfile() != null)
        {
            UUID uuid = UUID.fromString(ByteBufUtils.readUTF8String(data));
            String name = ByteBufUtils.readUTF8String(data);
            this.setPlayerGameProfile(new GameProfile(uuid, name));
            this.setNetworkPlayerInfo(new NetworkPlayerInfo(this.getPlayerGameProfile()));
        }
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        NBTTagCompound tagCompound = new NBTTagCompound();
        NBTUtil.writeGameProfile(tagCompound, this.getPlayerGameProfile());
        compound.setTag("OriginalPlayer", tagCompound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("OriginalPlayer", 10))
        {
            this.setPlayerGameProfile(NBTUtil.readGameProfileFromNBT(compound.getCompoundTag("OriginalPlayer")));
            this.setNetworkPlayerInfo(new NetworkPlayerInfo(this.getPlayerGameProfile()));
            this.setCustomNameTag("Spectral " + this.getPlayerGameProfile().getName());
        }
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }
}
