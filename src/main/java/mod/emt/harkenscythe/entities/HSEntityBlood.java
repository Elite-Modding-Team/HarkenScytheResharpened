package mod.emt.harkenscythe.entities;

import java.util.List;
import java.util.stream.Collectors;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.items.HSArmor;
import mod.emt.harkenscythe.items.tools.IHSTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class HSEntityBlood extends HSEntityEssence
{
    public HSEntityBlood(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.8F);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();
        if (item == HSItems.essence_keeper || item == HSItems.essence_vessel)
        {
            stack.shrink(1);
            ItemStack newStack = item == HSItems.essence_keeper ? new ItemStack(HSItems.essence_keeper_blood) : new ItemStack(HSItems.essence_vessel_blood);
            newStack.setItemDamage(newStack.getMaxDamage() - 1);
            player.setHeldItem(hand, newStack);
            if (this.isWearingFullBiomassSet(player)) this.repairEquipment(this.getRandomDamagedBiomassEquipment(player));
            float pitch = newStack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) newStack.getItemDamage() / newStack.getMaxDamage() * 0.5F);
            if (newStack.getItem() == HSItems.essence_keeper_blood) pitch += 0.5F;
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, pitch);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            this.setDead();
        }
        else if (item == HSItems.essence_keeper_blood || item == HSItems.essence_vessel_blood)
        {
            if (stack.getItemDamage() == 0) return false;
            if (stack.getItemDamage() > 0)
            {
                stack.setItemDamage(stack.getItemDamage() - 1);
            }
            if (stack.getItemDamage() <= 0)
            {
                stack.shrink(1);
                ItemStack newStack = item == HSItems.essence_keeper_blood ? new ItemStack(HSItems.essence_keeper_blood) : new ItemStack(HSItems.essence_vessel_blood);
                player.setHeldItem(hand, newStack);
            }
            if (this.isWearingFullBiomassSet(player)) this.repairEquipment(this.getRandomDamagedBiomassEquipment(player));
            float pitch = stack.getItemDamage() == 0 ? 1.0F : 1.0F - ((float) stack.getItemDamage() / stack.getMaxDamage() * 0.5F);
            if (stack.getItem() == HSItems.essence_keeper_blood) pitch += 0.5F;
            this.world.playSound(null, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, pitch);
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.1D, 0.0D);
            this.setDead();
        }
        return super.processInitialInteract(player, hand);
    }

    private boolean isWearingFullBiomassSet(EntityPlayer player)
    {
        Item boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
        Item leggings = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
        Item chestplate = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
        Item helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
        return boots == HSItems.biomass_boots && leggings == HSItems.biomass_leggings && chestplate == HSItems.biomass_chestplate && helmet == HSItems.biomass_helmet;
    }

    private ItemStack getRandomDamagedBiomassEquipment(EntityPlayer player)
    {
        List<ItemStack> equipmentList = getDamagedEntityEquipment(player);
        equipmentList = equipmentList.stream().filter(stack -> isBiomassItem(stack.getItem())).collect(Collectors.toList());
        return equipmentList.isEmpty() ? ItemStack.EMPTY : equipmentList.get(player.getRNG().nextInt(equipmentList.size()));
    }

    private boolean isBiomassItem(Item item)
    {
        return (item instanceof HSArmor && ((HSArmor) item).getArmorMaterial() == HSItems.ARMOR_BIOMASS) || (item instanceof IHSTool && ((IHSTool) item).getToolMaterial() == HSItems.TOOL_BIOMASS);
    }
}
