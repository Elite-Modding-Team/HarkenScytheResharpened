package mod.emt.harkenscythe.item;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.compat.bloodmagic.HSBloodMagicPlugin;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.HSEntityBlood;
import mod.emt.harkenscythe.entity.HSEntityEssence;
import mod.emt.harkenscythe.entity.HSEntitySoul;
import mod.emt.harkenscythe.init.HSEnumContainerType;
import mod.emt.harkenscythe.init.HSEnumFaction;
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
        if (!world.isRemote && entity instanceof EntityPlayer)
        {
            if (HSContainerHelper.isAnyTrinket(stack) || (isSelected && entity.isSneaking()))
            {
                EntityPlayer player = (EntityPlayer) entity;
                Class<? extends HSEntityEssence> attractedClass;
                if (HSContainerHelper.isBloodFaction(stack)) attractedClass = HSEntityBlood.class;
                else if (HSContainerHelper.isSoulFaction(stack)) attractedClass = HSEntitySoul.class;
                else attractedClass = HSEntityEssence.class;
                List<HSEntityEssence> list = world.getEntitiesWithinAABB(attractedClass, player.getEntityBoundingBox().grow(HSConfig.ENTITIES.essenceAttractionRadius));
                if (!list.isEmpty())
                {
                    for (HSEntityEssence essence : list)
                    {
                        if (essence.getDistanceSq(player.getPosition()) < 1.0D)
                        {
                            HSContainerHelper.collectEssence(stack, essence, player, player.getHeldItemMainhand().equals(stack) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                        }
                        else
                        {
                            essence.moveToEntity(player, 0.15D);
                        }
                    }
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
