package mod.emt.harkenscythe.item;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.compat.bloodmagic.HSBloodMagicPlugin;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.HSEntityBlood;
import mod.emt.harkenscythe.entity.HSEntityEssence;
import mod.emt.harkenscythe.entity.HSEntitySoul;
import mod.emt.harkenscythe.init.HSEnumContainerType;
import mod.emt.harkenscythe.init.HSEnumFaction;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.util.HSContainerHelper;

public class HSItemEssenceContainer extends HSItem
{
    private HSEnumContainerType containerType;
    private HSEnumFaction faction;

    public HSItemEssenceContainer(HSEnumContainerType containerType, HSEnumFaction faction)
    {
        super(EnumRarity.COMMON);
        setContainerType(containerType);
        setFaction(faction);
        setMaxStackSize(1);
        setNoRepair();
        if (getFaction() != HSEnumFaction.NEUTRAL)
        {
            switch (getContainerType())
            {
                case BASIC_KEEPER:
                    setMaxDamage(faction == HSEnumFaction.BLOOD ? HSConfig.ITEMS.bloodKeeperBasicEssenceCapacity : HSConfig.ITEMS.soulKeeperBasicEssenceCapacity);
                    break;
                case KEEPER:
                    setMaxDamage(faction == HSEnumFaction.BLOOD ? HSConfig.ITEMS.bloodKeeperEssenceCapacity : HSConfig.ITEMS.soulKeeperEssenceCapacity);
                    break;
                case VESSEL:
                    setMaxDamage(faction == HSEnumFaction.BLOOD ? HSConfig.ITEMS.bloodVesselEssenceCapacity : HSConfig.ITEMS.soulVesselEssenceCapacity);
                    break;
                case TRINKET:
                    setMaxDamage(faction == HSEnumFaction.BLOOD ? HSConfig.ITEMS.bloodTrinketEssenceCapacity : HSConfig.ITEMS.soulTrinketEssenceCapacity);
                    break;
                case ETHEREAL_TRINKET:
                    setMaxDamage(faction == HSEnumFaction.BLOOD ? HSConfig.ITEMS.bloodTrinketEtherealEssenceCapacity : HSConfig.ITEMS.soulTrinketEtherealEssenceCapacity);
                    break;
            }
            addPropertyOverride(new ResourceLocation(HarkenScythe.MOD_ID, "level"), new IItemPropertyGetter()
            {
                @SideOnly(Side.CLIENT)
                public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
                {
                    return 1.0F - ((float) getDamage(stack) / getMaxDamage(stack));
                }
            });
        }
    }

    public HSEnumContainerType getContainerType()
    {
        return containerType;
    }

    public void setContainerType(HSEnumContainerType containerType)
    {
        this.containerType = containerType;
    }

    public HSEnumFaction getFaction()
    {
        return faction;
    }

    public void setFaction(HSEnumFaction faction)
    {
        this.faction = faction;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        return Loader.isModLoaded("bloodmagic") && HSConfig.MOD_INTEGRATION.bloodMagicIntegration && getFaction() == HSEnumFaction.BLOOD ? HSBloodMagicPlugin.onBloodContainerRightClick(world, player, hand) : super.onItemRightClick(world, player, hand);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
        if (!HSContainerHelper.isAnyTrinket(stack)) return;
        if (!world.isRemote && entity instanceof EntityPlayer && entity.ticksExisted % 10 == 0 && stack.getItemDamage() > 0 && InventoryPlayer.isHotbar(itemSlot))
        {
            EntityPlayer player = (EntityPlayer) entity;
            List<HSEntityEssence> list = world.getEntitiesWithinAABB(getFaction() == HSEnumFaction.BLOOD ? HSEntityBlood.class : HSEntitySoul.class, player.getEntityBoundingBox());
            if (!list.isEmpty())
            {
                for (HSEntityEssence essence : list)
                {
                    if (essence.isDead || essence.getHealth() <= 0)
                    {
                        continue;
                    }
                    if (getContainerType() == HSEnumContainerType.ETHEREAL_TRINKET)
                    {
                        double d1 = (entity.posX - essence.posX) / 8.0D;
                        double d2 = (entity.posY + (double) entity.getEyeHeight() / 2.0D - essence.posY) / 8.0D;
                        double d3 = (entity.posZ - essence.posZ) / 8.0D;
                        double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                        double d5 = 1.0D - d4;
                        if (d5 > 0.0D)
                        {
                            d5 = d5 * d5;
                            essence.motionX += d1 / d4 * d5 * 0.1D;
                            essence.motionY += d2 / d4 * d5 * 0.1D;
                            essence.motionZ += d3 / d4 * d5 * 0.1D;
                        }
                        essence.move(MoverType.SELF, essence.motionX, essence.motionY, essence.motionZ);
                    }
                    if (stack.getItemDamage() > 0)
                    {
                        if (essence instanceof HSEntityBlood)
                        {
                            stack.setItemDamage(stack.getItemDamage() - ((HSEntityBlood) essence).getBloodQuantity());
                        }
                        else if (essence instanceof HSEntitySoul)
                        {
                            stack.setItemDamage(stack.getItemDamage() - ((HSEntitySoul) essence).getSoulQuantity());
                        }
                    }
                    float pitch = stack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) stack.getItemDamage() / stack.getMaxDamage() * 0.5F);
                    essence.world.playSound(null, player.getPosition(), HSSoundEvents.ITEM_BOTTLE_ESSENCE.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, pitch);
                    essence.world.spawnParticle(EnumParticleTypes.CLOUD, essence.posX, essence.posY + 1.5D, essence.posZ, 0.0D, 0.1D, 0.0D);
                    essence.recentlyHit = 60;
                    essence.setHealth(0);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.getItemDamage() == 0 && !HSContainerHelper.isNeutralFaction(stack);
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        return hasContainerItem(stack) ? HSContainerHelper.getEmptyContainer(stack) : ItemStack.EMPTY;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return !HSContainerHelper.isAnyTrinket(stack);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return getFaction() == HSEnumFaction.BLOOD ? 9443858 : 1872873;
    }
}
