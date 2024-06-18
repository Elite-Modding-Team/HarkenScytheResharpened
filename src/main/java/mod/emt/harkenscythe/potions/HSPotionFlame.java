package mod.emt.harkenscythe.potions;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HSPotionFlame extends Potion
{
    public HSPotionFlame(String name)
    {
        super(false, 0xFF4500);
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
                    BlockPos firePos = pos.add(x, y, z);
                    if (world.isAirBlock(firePos))
                    {
                        world.setBlockState(firePos, Blocks.FIRE.getDefaultState());
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
