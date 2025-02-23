package mod.emt.harkenscythe.item.tool;

import java.awt.*;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSPotions;
import mod.emt.harkenscythe.init.HSRegistry;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSToolBloodButcherer extends HSToolSword implements IHSTool
{
    private final float attackSpeed;
    private int currentDamage;

    public HSToolBloodButcherer(ToolMaterial material)
    {
        super(material, EnumRarity.COMMON);
        this.setMaxDamage(HSConfig.ITEMS.bloodButchererMaxCharges);
        this.attackSpeed = 0.8F;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
        this.currentDamage = stack.getItemDamage();
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (hasCharges() && attacker instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) attacker;
            World world = player.getEntityWorld();
            if (!player.capabilities.isCreativeMode)
            {
                if (stack.getItemDamage() <= stack.getMaxDamage() - HSConfig.ITEMS.bloodButchererBloodCost)
                {
                    stack.setItemDamage(stack.getItemDamage() + HSConfig.ITEMS.bloodButchererBloodCost);
                }
                else
                {
                    return true;
                }
            }
            if (!world.isRemote)
            {
                int duration = 200;
                int amplifier = 0;
                if (target.isPotionActive(HSPotions.BLEEDING))
                {
                    duration = Math.max(20, target.getActivePotionEffect(HSPotions.BLEEDING).getDuration());
                    amplifier = Math.min(4, target.getActivePotionEffect(HSPotions.BLEEDING).getAmplifier() + 1);
                }
                target.addPotionEffect(new PotionEffect(HSPotions.BLEEDING, duration, amplifier));
            }
            if (FMLLaunchHandler.side().isClient())
            {
                createHitParticles(target);
            }
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        double attackDamageMod = this.getAttackDamage() + 3.0D;
        attackDamageMod = hasCharges() ? attackDamageMod : attackDamageMod * 0.5D;
        double attackSpeedMod = this.attackSpeed - 4.0D;

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", attackDamageMod, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", attackSpeedMod, 0));
        }

        return multimap;
    }

    @Override
    public boolean isDamageable()
    {
        return false;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        if (entityLiving.swingProgress == 0 && hasCharges())
        {
            entityLiving.world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, HSSoundEvents.ITEM_BLOOD_BUTCHERER_SWING.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.5F / (entityLiving.world.rand.nextFloat() * 0.4F + 1.2F));
        }
        return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return 9443858;
    }

    // TODO: Add unique enchantments in the future. Enchanting disabled temporarily for now.
    @Override
    public int getItemEnchantability(ItemStack stack)
    {
        return 0;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return false;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack)
    {
        return HSRegistry.RARITY_BLOODY;
    }

    public boolean hasCharges()
    {
        return this.currentDamage < HSConfig.ITEMS.bloodButchererMaxCharges;
    }

    public void createHitParticles(EntityLivingBase target)
    {
        double centeredTargetY = target.posY + (target.height * 0.5F);
        for (double offset = -0.5; offset <= 0.5; offset += 0.1)
        {
            HSParticleHandler.spawnColoredParticle(EnumParticleTypes.REDSTONE, target.posX + offset, centeredTargetY + offset, target.posZ, Color.getColor("Blood Red", 12124160), 0.0D, 0.0D, 0.0D);
            HSParticleHandler.spawnColoredParticle(EnumParticleTypes.REDSTONE, target.posX + offset, centeredTargetY - offset, target.posZ, Color.getColor("Blood Red", 12124160), 0.0D, 0.0D, 0.0D);
        }
    }
}
