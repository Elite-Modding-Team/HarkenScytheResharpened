package mod.emt.harkenscythe.entities;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class HSEntityHemoglobin extends HSEntityGlobin
{
    public HSEntityHemoglobin(World world)
    {
        super(world);
    }

    @Override
    protected EnumParticleTypes getParticleType()
    {
        // TODO: Replace with more fitting particle
        return EnumParticleTypes.DRIP_LAVA;
    }

    @Override
    protected HSEntityHemoglobin createInstance()
    {
        return new HSEntityHemoglobin(this.world);
    }
}
