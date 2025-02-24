package mod.emt.harkenscythe.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.HSEntitySoul;

public class HSItemEssenceTrinketSoulEthereal extends HSItemEssenceTrinketSoul
{
    public HSItemEssenceTrinketSoulEthereal()
    {
        super();
        setMaxDamage(HSConfig.ITEMS.soulTrinketEtherealEssenceCapacity);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
        if (!world.isRemote && entity instanceof EntityPlayer && stack.getItemDamage() > 0 && InventoryPlayer.isHotbar(itemSlot))
        {
            EntityPlayer player = (EntityPlayer) entity;
            List<HSEntitySoul> list = world.getEntitiesWithinAABB(HSEntitySoul.class, player.getEntityBoundingBox().grow(8.0D));
            if (!list.isEmpty())
            {
                for (HSEntitySoul soulEssence : list)
                {
                    double d1 = (entity.posX - soulEssence.posX) / 8.0D;
                    double d2 = (entity.posY + (double) entity.getEyeHeight() / 2.0D - soulEssence.posY) / 8.0D;
                    double d3 = (entity.posZ - soulEssence.posZ) / 8.0D;
                    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                    double d5 = 1.0D - d4;
                    if (d5 > 0.0D)
                    {
                        d5 = d5 * d5;
                        soulEssence.motionX += d1 / d4 * d5 * 0.1D;
                        soulEssence.motionY += d2 / d4 * d5 * 0.1D;
                        soulEssence.motionZ += d3 / d4 * d5 * 0.1D;
                    }
                    soulEssence.move(MoverType.SELF, soulEssence.motionX, soulEssence.motionY, soulEssence.motionZ);
                }
            }
        }
    }
}
