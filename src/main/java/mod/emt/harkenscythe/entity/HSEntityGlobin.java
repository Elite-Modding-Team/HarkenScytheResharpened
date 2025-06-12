package mod.emt.harkenscythe.entity;

import mod.emt.harkenscythe.util.HSAttributeModifier;
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
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(HSAttributeModifier.LIFESTEAL).setBaseValue(1.0D);
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

    @Override
    protected boolean canDamagePlayer()
    {
        return true;
    }

    // Remove hardcoded slimeball drop
    @Override
    protected Item getDropItem()
    {
        return null;
    }
}
