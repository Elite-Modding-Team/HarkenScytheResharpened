package mod.emt.harkenscythe.entity;

import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSSoundEvents;
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

public abstract class HSEntityEssence extends EntityLivingBase
{
    private static final int DESPAWN_TIME = 6000;
    private int innerRotation;

    protected HSEntityEssence(World worldIn)
    {
        super(worldIn);
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
        if (this.ticksExisted % 20 == 19)
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
                        this.setDead();
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
        if (this.ticksExisted >= DESPAWN_TIME)
        {
            this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.NEUTRAL, 1.0F, 1.5F / (this.world.rand.nextFloat() * 0.4F + 1.2F));
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            if (!this.world.isRemote)
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
            this.setDead();
        }
        ++this.innerRotation;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList()
    {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {}

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

    public List<ItemStack> getDamagedEntityEquipment(EntityLivingBase entity)
    {
        List<ItemStack> list = Lists.newArrayList();
        for (EntityEquipmentSlot equipmentSlot : EntityEquipmentSlot.values())
        {
            ItemStack stack = entity.getItemStackFromSlot(equipmentSlot);
            if (!stack.isEmpty() && stack.isItemDamaged())
            {
                list.add(stack);
            }
        }
        return list;
    }

    public void repairEquipment(ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            stack.setItemDamage(stack.getItemDamage() - 2); // TODO: Respect essence quality once implemented
        }
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public void applyEntityCollision(Entity entity) {}
}
