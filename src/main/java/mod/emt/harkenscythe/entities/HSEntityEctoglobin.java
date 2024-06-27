package mod.emt.harkenscythe.entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class HSEntityEctoglobin extends HSEntityGlobin
{
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(HSEntityHemoglobin.class, DataSerializers.VARINT);
	
    public HSEntityEctoglobin(World world)
    {
        super(world);
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextInt(3)));
    }
    
    public int getSkin()
    {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType)
    {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("Variant", getSkin());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        setSkin(nbt.getInteger("Variant"));
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
