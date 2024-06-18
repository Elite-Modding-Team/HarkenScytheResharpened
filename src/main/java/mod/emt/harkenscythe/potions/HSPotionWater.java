package mod.emt.harkenscythe.potions;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSPotionWater extends Potion
{
    public HSPotionWater(String name)
    {
        super(false, 0x1E90FF);
        setPotionName("effect." + HarkenScythe.MOD_ID + "." + name);
        setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBase, int amplifier, double health)
    {
        World world = entityLivingBase.world;
        BlockPos pos = entityLivingBase.getPosition();
        for (int x = -1; x <= 1; x++)
        {
            for (int y = 0; y <= 1; y++)
            {
                for (int z = -1; z <= 1; z++)
                {
                    BlockPos waterPos = pos.add(x, y, z);
                    if (world.getBlockState(waterPos).getBlock() == Blocks.FIRE)
                    {
                        world.setBlockToAir(waterPos);
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldRender(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(PotionEffect effect)
    {
        return false;
    }
}
