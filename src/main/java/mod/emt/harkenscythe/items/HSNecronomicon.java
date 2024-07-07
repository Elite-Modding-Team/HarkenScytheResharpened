package mod.emt.harkenscythe.items;

import java.util.List;
import mod.emt.harkenscythe.entities.HSEntitySoul;
import mod.emt.harkenscythe.events.HSLivingDeathEvent;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HSNecronomicon extends HSItem
{
    public HSNecronomicon()
    {
        super(EnumRarity.RARE);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (player.capabilities.isCreativeMode || getBloodContainer(player) != ItemStack.EMPTY)
        {
            player.setActiveHand(hand);
            world.playSound(null, player.getPosition(), SoundEvents.AMBIENT_CAVE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            List<HSEntitySoul> souls = world.getEntitiesWithinAABB(HSEntitySoul.class, player.getEntityBoundingBox().grow(3.0D));
            for (HSEntitySoul entitySoul : souls)
            {
                HSLivingDeathEvent.spawnSpectralEntity(world, entitySoul.getOriginalEntity(), entitySoul.getPosition());
                entitySoul.setDead();
                ItemStack bloodContainer = getBloodContainer(player);
                if (bloodContainer != ItemStack.EMPTY)
                {
                    bloodContainer.damageItem(5, player);
                }
            }
        }
        return stack;
    }

    @Override
    public boolean isDamageable()
    {
        return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BLOCK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 80;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return false;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entity, int time)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            int duration = stack.getMaxItemUseDuration() - time;
            float progress = duration * 1.0f / stack.getMaxItemUseDuration();
            if (player.world.isRemote)
            {
                double theta = Math.PI * 6 * progress;
                double r = 2 * (1 - progress);
                for (int i = 0; i < 3; i++)
                {
                    player.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, player.posX + Math.cos(theta) * r, player.posY, player.posZ + Math.sin(theta) * r, 0, 2, 0);
                    theta += Math.PI * 2 / 3;
                }
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false;
    }

    public ItemStack getBloodContainer(EntityPlayer player)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if ((stack.getItem() == HSItems.essence_keeper_blood || stack.getItem() == HSItems.essence_vessel_blood) && stack.getItemDamage() <= stack.getMaxDamage() - 10)
            {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
