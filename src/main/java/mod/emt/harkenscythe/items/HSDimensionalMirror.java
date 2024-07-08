package mod.emt.harkenscythe.items;

import javax.annotation.Nullable;
import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HSDimensionalMirror extends HSItem
{
    private BlockPos bedPosition;
    private float status;

    public HSDimensionalMirror(EnumRarity rarity)
    {
        super(rarity);
        this.status = 0.0F;
        setMaxDamage(20);
        setMaxStackSize(1);
        addPropertyOverride(new ResourceLocation(HarkenScythe.MOD_ID + ":dimensional_mirror_status"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
            {
                if (stack.getItem() instanceof HSDimensionalMirror)
                {
                    return ((HSDimensionalMirror) stack.getItem()).status;
                }
                else return 0.0F;
            }
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItemDamage() <= 15)
        {
            if (this.bedPosition != null)
            {
                player.setActiveHand(hand);
                return new ActionResult<>(EnumActionResult.SUCCESS, stack);
            }
            else world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 0.5F, 0.5F);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            if (this.bedPosition != null)
            {
                // TODO: Make dimension configurable
                if (player.dimension != 0)
                {
                    player.changeDimension(0);
                }
                player.setPositionAndUpdate(this.bedPosition.getX(), this.bedPosition.getY(), this.bedPosition.getZ());
                if (!player.capabilities.isCreativeMode)
                {
                    stack.setItemDamage(stack.getItemDamage() + 5);
                }
                world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (isSelected && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            if (!world.isRemote) this.bedPosition = player.getBedLocation(0);
            if (stack.getItemDamage() > 15 || this.bedPosition == null)
            {
                this.status = 0.0F;
            }
            else if (!player.isActiveItemStackBlocking()) this.status = 1.0F;
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BLOCK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 120;
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
            this.status = 2.0F;
            int duration = stack.getMaxItemUseDuration() - time;
            float progress = duration * 1.0f / stack.getMaxItemUseDuration();
            if (player.world.isRemote)
            {
                if (player instanceof EntityPlayerSP) ((EntityPlayerSP) player).timeInPortal = Math.max(progress * 0.95f, ((EntityPlayerSP) player).timeInPortal);
                double theta = Math.PI * 6 * progress;
                double r = 2 * (1 - progress);
                for (int i = 0; i < 3; i++)
                {
                    player.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, player.posX + Math.cos(theta) * r, player.posY, player.posZ + Math.sin(theta) * r, 0, 2, 0);
                    theta += Math.PI * 2 / 3;
                }
            }
            int soundInterval = 32;
            if (duration % soundInterval == 0)
            {
                player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_PLAYER_BREATH, SoundCategory.PLAYERS, 0.6F, 0.2F * (2 + (float) duration / soundInterval));
            }
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false;
    }
}
