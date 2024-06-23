package mod.emt.harkenscythe.entities;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class HSEntityEctoglobin extends HSEntityGlobin
{
    public HSEntityEctoglobin(World world)
    {
        super(world);
    }

    @Override
    protected EnumParticleTypes getParticleType()
    {
        // TODO: Replace with more fitting particle
        return EnumParticleTypes.DRIP_WATER;
    }

    @Override
    protected HSEntityEctoglobin createInstance()
    {
        return new HSEntityEctoglobin(this.world);
    }
}
