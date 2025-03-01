package mod.emt.harkenscythe.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.entity.HSEntityVampireKnife;
import mod.emt.harkenscythe.init.HSRegistry;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSToolVampireKnife extends HSToolSword implements IHSTool
{
    private final float attackSpeed;

    public HSToolVampireKnife(ToolMaterial material, float attackSpeed)
    {
        super(material, EnumRarity.COMMON);
        this.attackSpeed = attackSpeed;
        this.setMaxDamage(HSConfig.ITEMS.vampireKnifeMaxCharges);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode)
        {
            if (stack.getItemDamage() <= stack.getMaxDamage() - HSConfig.ITEMS.vampireKnifeBloodCost)
            {
                stack.setItemDamage(stack.getItemDamage() + HSConfig.ITEMS.vampireKnifeBloodCost);
            }
            else
            {
                player.sendStatusMessage(new TextComponentTranslation("message.harkenscythe.vampire_knife.no_blood"), true);
                return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
            }
        }
        if (!world.isRemote)
        {
            // Throw five knives at once!
            for (int i = -2; i < 3; i++)
            {
                HSEntityVampireKnife sword = new HSEntityVampireKnife(world, player);
                sword.shoot(player, player.rotationPitch, player.rotationYaw + (i * 15.0F), 0.0F, 1.5F, 3.0F);
                sword.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack));
                sword.setGlowing(true);
                world.spawnEntity(sword);
            }

            player.getCooldownTracker().setCooldown(this, HSConfig.ITEMS.vampireKnifeCooldown);
            world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.ITEM_VAMPIRE_KNIFE_THROW.getSoundEvent(), SoundCategory.PLAYERS, 0.7F, 0.6F + world.rand.nextFloat());
        }
        else
        {
            player.swingArm(hand);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
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
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
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
    public IRarity getForgeRarity(ItemStack stack)
    {
        return HSRegistry.RARITY_BLOODY;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        return true;
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
}
