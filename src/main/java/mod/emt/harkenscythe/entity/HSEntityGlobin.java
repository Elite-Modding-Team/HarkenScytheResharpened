package mod.emt.harkenscythe.entity;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public abstract class HSEntityGlobin extends EntitySlime
{
    protected HSEntityGlobin(World world)
    {
        super(world);
        this.setSlimeSize(1 + world.rand.nextInt(3), true);
    }

    public void setSize(int size, boolean resetHealth)
    {
        this.setSlimeSize(size, resetHealth);
    }

    @Override
    protected void alterSquishAmount()
    {
        this.squishAmount *= 0.9F;
    }

    // Remove hardcoded slimeball drop
    @Override
    protected Item getDropItem()
    {
        return this.getSlimeSize() == 1 ? null : null;
    }
}
