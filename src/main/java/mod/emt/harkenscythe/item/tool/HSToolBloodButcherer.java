package mod.emt.harkenscythe.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.awt.*;
import mod.emt.harkenscythe.client.particle.HSParticleHandler;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSMaterials;
import mod.emt.harkenscythe.init.HSPotions;
import mod.emt.harkenscythe.init.HSRegistry;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.util.HSDamageSource;

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
        if (isSelected)
        {
            this.currentDamage = stack.getItemDamage();
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (stack.getItemDamage() < stack.getMaxDamage() && attacker instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) attacker;

            target.attackEntityFrom(new HSDamageSource("hs_butcher", attacker), this.getAttackDamage());

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

            this.doBleedEffect(target);
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
        attackDamageMod = this.currentDamage < HSConfig.ITEMS.bloodButchererMaxCharges ? attackDamageMod : attackDamageMod * 0.5D;
        double attackSpeedMod = this.attackSpeed - 4.0D;

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", attackDamageMod, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", attackSpeedMod, 0));
        }

        return multimap;
    }

    public void doBleedEffect(EntityLivingBase target)
    {
        World world = target.getEntityWorld();

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
            target.playSound(HSSoundEvents.BLOCK_BLOOD_ABSORBER_STOP.getSoundEvent(), 0.5F, 1.5F / (target.world.rand.nextFloat() * 0.4F + 0.8F));
        }

        if (FMLLaunchHandler.side().isClient())
        {
            createHitParticles(target);
        }
    }

    @Override
    public boolean isDamageable()
    {
        return false;
    }

    @Override
    public boolean isRepairable()
    {
        return false;
    }

    @Override
    public float getXpRepairRatio(ItemStack stack)
    {
        return 0;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (stack.getItemDamage() < stack.getMaxDamage())
        {
            if (player.getCooledAttackStrength(0) == 1.0F)
            {
                for (EntityLivingBase target : player.world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().grow(2.0D, 0.25D, 2.0D)))
                {
                    float attribute = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                    float sweepCalculation = (HSMaterials.TOOL_BLOOD_BUTCHERER.getAttackDamage() + 4.0F) + EnchantmentHelper.getSweepingDamageRatio(player) * attribute;

                    if (target != player && target != entity && !player.isOnSameTeam(target))
                    {
                        target.knockBack(player, 0.5F, MathHelper.sin(player.rotationYaw * 0.02F), -MathHelper.cos(player.rotationYaw * 0.02F));
                        target.attackEntityFrom(new HSDamageSource("hs_butcher", player), sweepCalculation);
                        this.doBleedEffect(target);
                    }
                    player.world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.ITEM_BLOOD_BUTCHERER_SWING.getSoundEvent(), player.getSoundCategory(), 1.0F, 0.5F / (player.world.rand.nextFloat() * 0.4F + 1.2F));
                }
            }
            else
            {
                player.world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.ITEM_BLOOD_BUTCHERER_SWING.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.5F / (player.world.rand.nextFloat() * 0.4F + 1.2F));
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        if (stack.getItemDamage() < stack.getMaxDamage() && entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).getCooledAttackStrength(0) > 0.1F)
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

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        if (enchantment == Enchantments.MENDING || enchantment == Enchantments.UNBREAKING) return false;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack)
    {
        return HSRegistry.RARITY_BLOODY;
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
