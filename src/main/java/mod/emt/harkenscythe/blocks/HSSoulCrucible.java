package mod.emt.harkenscythe.blocks;

import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.Item;

public class HSSoulCrucible extends HSCrucible
{
    public HSSoulCrucible()
    {
        super(MapColor.BLACK);
    }

    @Override
    protected Item getEssenceKeeper()
    {
        return HSItems.essence_keeper_soul;
    }

    @Override
    protected Item getEssenceVessel()
    {
        return HSItems.essence_vessel_soul;
    }
}
