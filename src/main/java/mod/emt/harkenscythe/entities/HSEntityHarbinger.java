package mod.emt.harkenscythe.entities;

import mod.emt.harkenscythe.init.HSLootTables;

import javax.annotation.Nonnull;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

// TODO: Lots of stuff
public class HSEntityHarbinger extends EntityMob
{
    public HSEntityHarbinger(World world)
    {
        super(world);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable()
    {
        return HSLootTables.HARBINGER;
    }

    @Nonnull
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED;
    }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_WITHER_SPAWN;
    }
}