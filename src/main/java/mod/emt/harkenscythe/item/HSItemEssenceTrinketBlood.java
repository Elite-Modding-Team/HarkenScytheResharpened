package mod.emt.harkenscythe.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.HSEntityBlood;

public class HSItemEssenceTrinketBlood extends HSItemEssenceVesselBlood
{
    public HSItemEssenceTrinketBlood()
    {
        super();
        setMaxDamage(HSConfig.ITEMS.bloodTrinketEssenceCapacity);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (!world.isRemote && entity instanceof EntityPlayer && entity.ticksExisted % 10 == 0 && stack.getItemDamage() > 0 && InventoryPlayer.isHotbar(itemSlot))
        {
            EntityPlayer player = (EntityPlayer) entity;
            List<HSEntityBlood> list = world.getEntitiesWithinAABB(HSEntityBlood.class, player.getEntityBoundingBox());
            if (!list.isEmpty())
            {
                for (HSEntityBlood bloodEssence : list)
                {
                    if (bloodEssence.isDead || bloodEssence.getHealth() <= 0)
                    {
                        continue;
                    }
                    if (stack.getItemDamage() > 0)
                    {
                        stack.setItemDamage(stack.getItemDamage() - bloodEssence.getBloodQuantity());
                    }
                    if (stack.getItemDamage() <= 0)
                    {
                        stack = new ItemStack(this);
                    }
                    float pitch = stack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) stack.getItemDamage() / stack.getMaxDamage() * 0.5F);
                    bloodEssence.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, pitch);
                    bloodEssence.world.spawnParticle(EnumParticleTypes.CLOUD, bloodEssence.posX, bloodEssence.posY + 1.5D, bloodEssence.posZ, 0.0D, 0.1D, 0.0D);
                    bloodEssence.recentlyHit = 60;
                    bloodEssence.setHealth(0);
                }
            }
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return false;
    }
}
