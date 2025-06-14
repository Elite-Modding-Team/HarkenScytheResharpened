package mod.emt.harkenscythe.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import mod.emt.harkenscythe.block.HSBlockBiomassCrop;
import mod.emt.harkenscythe.init.*;
import mod.emt.harkenscythe.init.HSAttributes;
import mod.emt.harkenscythe.util.HSDamageSource;

@SuppressWarnings("deprecation")
public class HSToolScythe extends ItemSword implements IHSTool
{
    public AttributeModifier alteration;
    private final float attackSpeed;
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolScythe(ToolMaterial material, float attackSpeed, EnumRarity rarity, float alterationRate)
    {
        super(material);
        this.attackSpeed = attackSpeed;
        this.rarity = rarity;
        this.material = material;

        this.alteration = new AttributeModifier(HSAttributes.ESSENCE_ALTERATION_ID, "essence alteration scythe", alterationRate, 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        int level = EnchantmentHelper.getEnchantmentLevel(HSEnchantments.WILLINGNESS, stack);

        // Slightly pitch up the sound per level of Willingness
        player.playSound(SoundEvents.ENTITY_PLAYER_BREATH, 0.8F, 0.9F + (0.1F * level));
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (stack.isItemDamaged() && entity != null && this.getToolMaterial() == HSMaterials.TOOL_BIOMASS && entity.ticksExisted % 40 == 0)
        {
            stack.attemptDamageItem(-1, world.rand, entity instanceof EntityPlayerMP ? (EntityPlayerMP) entity : null);
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
        ItemStack heldItem = entityLiving.getHeldItemMainhand() == stack ? entityLiving.getHeldItemMainhand() : entityLiving.getHeldItemOffhand();
        int level = EnchantmentHelper.getEnchantmentLevel(HSEnchantments.WILLINGNESS, heldItem);

        if (!world.isRemote && entityLiving instanceof EntityPlayer)
        {
            RayTraceResult rayTraceResult = rayTrace(world, (EntityPlayer) entityLiving, false);
            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos cropPos = rayTraceResult.getBlockPos().up();
                IBlockState cropState = world.getBlockState(cropPos);
                if (cropState.getBlock() == HSBlocks.biomass_crop && cropState.getValue(HSBlockBiomassCrop.AGE) >= 3)
                {
                    world.destroyBlock(cropPos, true);
                    int damage = stack.getMaxDamage() - stack.getItemDamage();
                    double chance = (double) damage / 1000;
                    if (world.rand.nextDouble() < chance)
                    {
                        Block.spawnAsEntity(world, cropPos, new ItemStack(HSItems.biomass));
                    }
                }
            }
        }

        float range = (float) EntityPlayer.REACH_DISTANCE.getDefaultValue() - 1.0F; // 4 by default without reach distance boosts
        AxisAlignedBB bb = new AxisAlignedBB(entityLiving.posX - range, entityLiving.posY - range, entityLiving.posZ - range, entityLiving.posX + range, entityLiving.posY + range, entityLiving.posZ + range);

        for (int i = 0; i < world.getEntitiesWithinAABB(EntityLivingBase.class, bb).size(); i++)
        {
            EntityLivingBase entityInAABB = world.getEntitiesWithinAABB(EntityLivingBase.class, bb).get(i);

            // Skip self
            if (entityInAABB == entityLiving)
            {
                continue;
            }

            // Original speed at 20. Willingness I at 16. Divide 25 by level in subsequent Willingness levels (12.5 at II and 8.3 at III)
            if (Math.min(1.0F, (getMaxItemUseDuration(stack) - timeLeft) / (level <= 0 ? 20.0F : level == 1 ? 16.0F : 25.0F / level)) >= 1.0F)
            {
                entityInAABB.attackEntityFrom(new HSDamageSource("hs_reap", entityLiving).setDamageBypassesArmor(), getDamage(entityInAABB));
            }
        }

        if (Math.min(1.0F, (getMaxItemUseDuration(stack) - timeLeft) / (level <= 0 ? 20.0F : level == 1 ? 16.0F : 25.0F / level)) >= 1.0F && entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;

            player.swingArm(player.getActiveHand());
            player.spawnSweepParticles();
            player.playSound(HSSoundEvents.ITEM_SCYTHE_SWING.getSoundEvent(), 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));

            if (level >= 1 && itemRand.nextDouble() < 0.15D * level)
            {
                return;
            }
            else
            {
                stack.damageItem(2, player);
            }

            player.addStat(StatList.getObjectUseStats(this));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        ItemStack heldItem = player.getHeldItemMainhand() == stack ? player.getHeldItemMainhand() : player.getHeldItemOffhand();
        int reapingFrenzyLevel = EnchantmentHelper.getEnchantmentLevel(HSEnchantments.REAPING_FRENZY, heldItem);
        int willingnessLevel = EnchantmentHelper.getEnchantmentLevel(HSEnchantments.WILLINGNESS, heldItem);

        // With the Auto-Reap enchantment, automatically reap at full charge. Also supports Willingness charge speeds.
        if (reapingFrenzyLevel > 0)
        {
            // Original speed at 20. Willingness I at 16. Divide 25 by level in subsequent Willingness levels (12.5 at II and 8.3 at III)
            if (Math.min(1.0F, (getMaxItemUseDuration(stack) - count) / (willingnessLevel <= 0 ? 20.0F : willingnessLevel == 1 ? 16.0F : 25.0F / willingnessLevel)) >= 1.0F)
            {
                player.playSound(HSSoundEvents.ENCHANTMENT_AUTO_REAP.getSoundEvent(), 0.8F, 1.5F + (0.1F * willingnessLevel));
                player.stopActiveHand();
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
    {
        return true;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker)
    {
        return stack.getItem() == HSItems.reaper_scythe || stack.getItem() == HSItems.lady_harken_scythe;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", this.getAttackDamage() + 3.0D, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", this.attackSpeed - 4.0D, 0));
            multimap.put(HSAttributes.ESSENCE_ALTERATION.getName(), alteration);
        }

        return multimap;
    }

    @Override
    public ToolMaterial getToolMaterial()
    {
        return this.material;
    }

    private float getDamage(EntityLivingBase entity)
    {
        if (this == HSItems.reaper_scythe && itemRand.nextDouble() < (entity.isNonBoss() ? 0.1D : 0.05D))
        {
            return entity.getMaxHealth();
        }
        float damage = this.getAttackDamage() + 4.0F; // Has to be done like this otherwise it'll calculate wrong
        return damage * 2; // Damage x 2 (Mojang is very strange with damage values...)
    }
}
