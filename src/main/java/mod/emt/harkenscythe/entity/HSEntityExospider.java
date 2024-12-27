package mod.emt.harkenscythe.entity;

import mod.emt.harkenscythe.init.HSSoundEvents;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TODO: Disable fall damage
public class HSEntityExospider extends EntitySpider {
    public HSEntityExospider(World world) {
        super(world);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }
    
    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return null; 
    }
    
    @Nonnull
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return HSSoundEvents.ENTITY_EXOSPIDER_HURT.getSoundEvent();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return HSSoundEvents.ENTITY_EXOSPIDER_DEATH.getSoundEvent();
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        this.playSound(HSSoundEvents.ENTITY_EXOSPIDER_STEP.getSoundEvent(), 0.15F, 1.0F);
    }
}