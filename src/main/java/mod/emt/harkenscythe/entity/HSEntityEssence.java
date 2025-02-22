package mod.emt.harkenscythe.entity;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
                        entityItem.setDead();
                        this.createAthame();
                        break;
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
                    int size;
                    switch (this.getDataManager().get(HSEntityBlood.BLOOD_TYPE))
                    {
                        case 1: // Sickly (2)
                            size = 2;
                            break;
                        case 2: // Intoxicated (5)
                            size = 2;
                            break;
                        case 3: // Warped (40)
                            size = 3;
                            break;
                        default: // Common (1)
                            size = 1;
                    }
                    hemoglobin.setSize(size, true);
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
                        int size;
                        switch (this.getDataManager().get(HSEntitySoul.SOUL_TYPE))
                        {
                            case 1: // Grieving (2)
                                size = 2;
                                break;
                            case 2: // Culled (5)
                                size = 2;
                                break;
                            case 3: // Wrathful (40)
                                size = 3;
                                break;
                            default: // Common (1)
                                size = 1;
                        }
                        ectoglobin.setSize(size, true);
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

    public void createAthame()
    {
        this.world.playSound(null, this.getPosition(), HSSoundEvents.ITEM_ATHAME_CREATE.getSoundEvent(), SoundCategory.NEUTRAL, 1.0F, 1.5F / (this.world.rand.nextFloat() * 0.4F + 1.2F));
        this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
        this.setHealth(0);
        if (!this.world.isRemote)
        {
            this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(HSItems.harken_athame)));
        }
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
    public float getBrightness()
    {
        return 1.0F;
    }

    @Override
    public void applyEntityCollision(Entity entity) {}

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == HSItems.blunt_harken_blade)
        {
            stack.shrink(1);
            this.createAthame();
            return true;
        }
        return super.processInitialInteract(player, hand);
    }
}
