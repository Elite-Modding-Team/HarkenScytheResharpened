package mod.emt.harkenscythe.entities;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.World;

public abstract class HSEntityGlobin extends EntitySlime
{
    public HSEntityGlobin(World world)
    {
        super(world);
        this.setSlimeSize(1 + world.rand.nextInt(3), true);
    }
}