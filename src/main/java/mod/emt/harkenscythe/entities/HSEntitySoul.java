package mod.emt.harkenscythe.entities;

import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
            world.playSound(null, this.getPosition(), HSSoundEvents.ESSENCE_SOUL_SPAWN, SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            if (this.getOriginalEntity() instanceof EntityCreature)
            {
                EntityCreature creature = (EntityCreature) this.getOriginalEntity();
                // TODO: Set entity data to determine spectral variant
                creature.setCustomNameTag("Spectral " + creature.getName());
                creature.setPosition(this.posX, this.posY, this.posZ);
                creature.setHealth(creature.getMaxHealth());
                creature.isDead = false;
                if (!this.world.isRemote) world.spawnEntity(creature);
            }
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
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, 1.0F);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            this.setDead();
        }
        return super.processInitialInteract(player, hand);
    }
}
