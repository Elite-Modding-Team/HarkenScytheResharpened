package mod.emt.harkenscythe.item.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.util.HSDamageSource;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class HSToolGlaive extends ItemSword implements IHSTool
{
    private final float attackSpeed;
    private final EnumRarity rarity;
    private final ToolMaterial material;

    public HSToolGlaive(ToolMaterial material, float attackSpeed, EnumRarity rarity)
    {
        super(material);
        this.attackSpeed = attackSpeed;
        this.rarity = rarity;
        this.material = material;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        player.playSound(SoundEvents.ENTITY_PLAYER_BREATH, 0.8F, 0.9F);
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
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

    // TODO: The glaive does not use an AoE attack, it will only be able to attack one entity at a time but does armor piercing damage
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
        if (!world.isRemote && entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;
            RayTraceResult rayTraceResult = rayTrace(world, player, false);
            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos creepPos = rayTraceResult.getBlockPos();
                IBlockState creepState = world.getBlockState(creepPos);
                if (creepState.getBlock() == HSBlocks.creep_block)
                {
                    world.setBlockState(creepPos, HSBlocks.creep_block_tilled.getDefaultState());
                    if (!player.capabilities.isCreativeMode)
                    {
                        stack.damageItem(1, player);
                    }
                    world.playSound(null, creepPos, HSSoundEvents.ITEM_GLAIVE_TILL, SoundCategory.BLOCKS, 0.8F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
                    player.addStat(StatList.getObjectUseStats(stack.getItem()));
                }
            }
        }

        float damage = this.getAttackDamage() + 4.0F; // Has to be done like this otherwise it'll calculate wrong
        float range = 3.0F;
        AxisAlignedBB bb = new AxisAlignedBB(entityLiving.posX - range, entityLiving.posY - range, entityLiving.posZ - range, entityLiving.posX + range, entityLiving.posY + range, entityLiving.posZ + range);

        for (int i = 0; i < world.getEntitiesWithinAABB(EntityLivingBase.class, bb).size(); i++)
        {
            EntityLivingBase entityInAABB = world.getEntitiesWithinAABB(EntityLivingBase.class, bb).get(i);

            // Skip self
            if (entityInAABB == entityLiving)
            {
                continue;
            }

            if (Math.min(1.0F, (getMaxItemUseDuration(stack) - timeLeft) / 20.0F) >= 1.0F)
            {
                // Damage x 2 (Mojang is very strange with damage values...)
                entityInAABB.attackEntityFrom(new HSDamageSource("hs_reap", entityLiving), damage * 2);
            }
        }

        if (Math.min(1.0F, (getMaxItemUseDuration(stack) - timeLeft) / 20.0F) >= 1.0F && entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;
            player.swingArm(EnumHand.MAIN_HAND);
            player.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            stack.damageItem(2, player);
            player.addStat(StatList.getObjectUseStats(this));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", this.getAttackDamage() + 3.0D, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", this.attackSpeed - 4.0D, 0));
        }

        return multimap;
    }

    @Override
    public ToolMaterial getToolMaterial()
    {
        return this.material;
    }
}
