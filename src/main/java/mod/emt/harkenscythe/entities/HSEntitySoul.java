package mod.emt.harkenscythe.entities;

import mod.emt.harkenscythe.events.HSLivingDeathEvent;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class HSEntitySoul extends HSEntityEssence
{
    private EntityLivingBase originalEntity;

    public HSEntitySoul(World world)
    {
        super(world);
        this.setSize(1.2F, 1.2F);
        if (getOriginalEntity() == null) setOriginalEntity(new HSEntityEctoglobin(world));
    }

    public HSEntitySoul(World world, EntityLivingBase entityLivingBase)
    {
        this(world);
        this.setOriginalEntity(entityLivingBase);
    }

    public EntityLivingBase getOriginalEntity()
    {
        return originalEntity;
    }

    public void setOriginalEntity(EntityLivingBase originalEntity)
    {
        this.originalEntity = originalEntity;
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source.getTrueSource() instanceof HSEntityHarbinger)
        {
            this.setDead();
            HSLivingDeathEvent.spawnSpectralEntity(this.world, this.getOriginalEntity(), this.getPosition());
            return true;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();
        if (item == HSItems.essence_keeper || item == HSItems.essence_vessel)
        {
            stack.shrink(1);
            ItemStack newStack = item == HSItems.essence_keeper ? new ItemStack(HSItems.essence_keeper_soul) : new ItemStack(HSItems.essence_vessel_soul);
            newStack.setItemDamage(newStack.getMaxDamage() - 1);
            player.setHeldItem(hand, newStack);
            float pitch = newStack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) newStack.getItemDamage() / newStack.getMaxDamage() * 0.5F);
            if (newStack.getItem() == HSItems.essence_keeper_soul) pitch += 0.5F;
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, pitch);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            this.setDead();
        }
        else if (item == HSItems.essence_keeper_soul || item == HSItems.essence_vessel_soul)
        {
            if (stack.getItemDamage() == 0) return false;
            if (stack.getItemDamage() > 0)
            {
                stack.setItemDamage(stack.getItemDamage() - 1);
            }
            if (stack.getItemDamage() <= 0)
            {
                stack.shrink(1);
                ItemStack newStack = item == HSItems.essence_keeper_soul ? new ItemStack(HSItems.essence_keeper_soul) : new ItemStack(HSItems.essence_vessel_soul);
                player.setHeldItem(hand, newStack);
            }
            float pitch = stack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) stack.getItemDamage() / stack.getMaxDamage() * 0.5F);
            if (stack.getItem() == HSItems.essence_keeper_soul) pitch += 0.5F;
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, pitch);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            this.setDead();
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        if (getOriginalEntity() != null)
        {
            NBTTagCompound originalEntityNBT = new NBTTagCompound();
            this.getOriginalEntity().writeToNBT(originalEntityNBT);
            originalEntityNBT.setString("id", EntityList.getKey(this.getOriginalEntity().getClass()).toString());
            originalEntityNBT.setString("name", this.getOriginalEntity().getName());
            compound.setTag("OriginalEntity", originalEntityNBT);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("OriginalEntity"))
        {
            NBTTagCompound originalEntityNBT = compound.getCompoundTag("OriginalEntity");
            Entity originalEntity = EntityList.createEntityFromNBT(originalEntityNBT, this.world);
            if (originalEntity instanceof EntityLivingBase) setOriginalEntity((EntityLivingBase) originalEntity);
        }
    }
}
