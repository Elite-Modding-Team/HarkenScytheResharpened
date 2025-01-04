package mod.emt.harkenscythe.entity;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;

public abstract class HSEntityEssence extends EntityLivingBase
{
    private int innerRotation;

    protected HSEntityEssence(World world)
    {
        super(world);
        this.innerRotation = this.rand.nextInt(100000);
    }

    public int getInnerRotation()
    {
        return innerRotation;
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
        if (this.ticksExisted % 20 == 0)
        {
            List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox());
            if (!list.isEmpty())
            {
                for (EntityItem entityItem : list)
                {
                    if (!entityItem.isDead && entityItem.getItem().getItem() == HSItems.blunt_harken_blade)
                    {
                        this.world.playSound(null, this.getPosition(), HSSoundEvents.ITEM_ATHAME_CREATE.getSoundEvent(), SoundCategory.NEUTRAL, 1.0F, 1.5F / (this.world.rand.nextFloat() * 0.4F + 1.2F));
                        this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
                        entityItem.setDead();
                        this.setHealth(0);
                        if (!this.world.isRemote)
                        {
                            ItemStack athame = new ItemStack(HSItems.harken_athame);
                            athame.setItemDamage(entityItem.getItem().getItemDamage());
                            this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, athame));
                        }
                    }
                }
            }
        }
        if (this.ticksExisted == HSConfig.ENTITIES.essenceDespawnTime)
        {
            this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.NEUTRAL, 1.0F, 1.5F / (this.world.rand.nextFloat() * 0.4F + 1.2F));
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            if (!this.world.isRemote && HSConfig.ENTITIES.essenceMobSpawning)
            {
                if (this instanceof HSEntityBlood)
                {
                    HSEntityHemoglobin hemoglobin = new HSEntityHemoglobin(this.world);
                    hemoglobin.setPosition(this.posX, this.posY, this.posZ);
                    this.world.spawnEntity(hemoglobin);
                }
                else if (this instanceof HSEntitySoul)
                {
                    if (((HSEntitySoul) this).getOriginalEntity() instanceof HSEntitySpectralMiner)
                    {
                        HSEntitySpectralMiner spectralMiner = new HSEntitySpectralMiner(this.world);
                        spectralMiner.setPosition(this.posX, this.posY, this.posZ);
                        this.world.spawnEntity(spectralMiner);
                    }
                    else
                    {
                        HSEntityEctoglobin ectoglobin = new HSEntityEctoglobin(this.world);
                        ectoglobin.setPosition(this.posX, this.posY, this.posZ);
                        this.world.spawnEntity(ectoglobin);
                    }
                }
            }
            this.setHealth(0);
        }
        ++this.innerRotation;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return source == DamageSource.OUT_OF_WORLD && super.attackEntityFrom(source, amount);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList()
    {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slot)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slot, ItemStack stack) {}

    @Override
    protected void collideWithNearbyEntities() {}

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public EnumHandSide getPrimaryHand()
    {
        return EnumHandSide.RIGHT;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public void applyEntityCollision(Entity entity) {}
}
