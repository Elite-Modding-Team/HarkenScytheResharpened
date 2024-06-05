package mod.emt.harkenscythe.entities;

import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.items.HSEssenceKeeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class HSSoul extends Entity
{
    public HSSoul(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 2.0F);
    }

    @Override
    protected void entityInit() {}

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {}

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();
        if (item instanceof HSEssenceKeeper)
        {
            if (!player.capabilities.isCreativeMode)
            {
                if (item == HSItems.essence_keeper || item == HSItems.essence_vessel)
                {
                    stack.shrink(1);
                    ItemStack newStack = item == HSItems.essence_keeper ? new ItemStack(HSItems.essence_keeper_soul) : new ItemStack(HSItems.essence_vessel_soul);
                    newStack.setItemDamage(newStack.getMaxDamage() - 1);
                    player.setHeldItem(hand, newStack);
                }
                else if (item == HSItems.essence_keeper_soul || item == HSItems.essence_vessel_soul)
                {
                    if (stack.getItemDamage() == 0) return false;
                    if (stack.getItemDamage() > 0)
                    {
                        stack.setItemDamage(stack.getItemDamage() - 1);
                    }
                    if (stack.getItemDamage() <= 0)
                    {
                        stack.shrink(1);
                        ItemStack newStack = item == HSItems.essence_keeper_soul ? new ItemStack(HSItems.essence_keeper_soul) : new ItemStack(HSItems.essence_vessel_soul);
                        player.setHeldItem(hand, newStack);
                    }
                }
            }
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            this.setDead();
        }
        return super.processInitialInteract(player, hand);
    }
}
