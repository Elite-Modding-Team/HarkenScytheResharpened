package mod.emt.harkenscythe.init;

import net.minecraft.block.SoundType;

public class HSSoundTypes
{
    public static final SoundType BIOMASS = new SoundType(1.0F, 1.0F, HSSoundEvents.BLOCK_BIOMASS_BREAK.getSoundEvent(), HSSoundEvents.BLOCK_BIOMASS_STEP.getSoundEvent(), HSSoundEvents.BLOCK_BIOMASS_BREAK.getSoundEvent(), HSSoundEvents.BLOCK_BIOMASS_HARVEST.getSoundEvent(), HSSoundEvents.BLOCK_BIOMASS_STEP.getSoundEvent());
    public static final SoundType BIOMASS_PLANT = new SoundType(1.0F, 1.0F, HSSoundEvents.BLOCK_BIOMASS_HARVEST.getSoundEvent(), HSSoundEvents.BLOCK_BIOMASS_HARVEST.getSoundEvent(), HSSoundEvents.BLOCK_BIOMASS_HARVEST.getSoundEvent(), HSSoundEvents.BLOCK_BIOMASS_HARVEST.getSoundEvent(), HSSoundEvents.BLOCK_BIOMASS_HARVEST.getSoundEvent());
    public static final SoundType LIVINGMETAL = new SoundType(1.0F, 1.0F, HSSoundEvents.BLOCK_LIVINGMETAL_BREAK.getSoundEvent(), HSSoundEvents.BLOCK_LIVINGMETAL_STEP.getSoundEvent(), HSSoundEvents.BLOCK_LIVINGMETAL_BREAK.getSoundEvent(), HSSoundEvents.BLOCK_LIVINGMETAL_STEP.getSoundEvent(), HSSoundEvents.BLOCK_LIVINGMETAL_STEP.getSoundEvent());
}
