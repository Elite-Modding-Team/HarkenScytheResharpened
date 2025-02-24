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
import mod.emt.harkenscythe.entity.HSEntityBlood;

public class HSItemEssenceTrinketBloodEthereal extends HSItemEssenceTrinketBlood
{
    public HSItemEssenceTrinketBloodEthereal()
    {
        super();
        setMaxDamage(HSConfig.ITEMS.bloodTrinketEtherealEssenceCapacity);
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
            List<HSEntityBlood> list = world.getEntitiesWithinAABB(HSEntityBlood.class, player.getEntityBoundingBox().grow(8.0D));
            if (!list.isEmpty())
            {
                for (HSEntityBlood bloodEssence : list)
                {
                    double d1 = (entity.posX - bloodEssence.posX) / 8.0D;
                    double d2 = (entity.posY + (double) entity.getEyeHeight() / 2.0D - bloodEssence.posY) / 8.0D;
                    double d3 = (entity.posZ - bloodEssence.posZ) / 8.0D;
                    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                    double d5 = 1.0D - d4;
                    if (d5 > 0.0D)
                    {
                        d5 = d5 * d5;
                        bloodEssence.motionX += d1 / d4 * d5 * 0.1D;
                        bloodEssence.motionY += d2 / d4 * d5 * 0.1D;
                        bloodEssence.motionZ += d3 / d4 * d5 * 0.1D;
                    }
                    bloodEssence.move(MoverType.SELF, bloodEssence.motionX, bloodEssence.motionY, bloodEssence.motionZ);
                }
            }
        }
    }
}
