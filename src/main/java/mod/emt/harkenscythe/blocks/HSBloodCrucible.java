package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.Item;

public class HSBloodCrucible extends HSCrucible
{
    public HSBloodCrucible()
    {
        super(MapColor.RED);
    }

    @Override
    protected Item getEssenceKeeper()
    {
        return HSItems.essence_keeper_blood;
    }

    @Override
    protected Item getEssenceVessel()
    {
        return HSItems.essence_vessel_blood;
    }
}
