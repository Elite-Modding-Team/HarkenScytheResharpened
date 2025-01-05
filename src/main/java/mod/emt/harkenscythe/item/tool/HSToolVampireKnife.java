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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

import mod.emt.harkenscythe.entity.HSEntityVampireKnife;
import mod.emt.harkenscythe.init.HSItems;
import mod.emt.harkenscythe.init.HSRegistry;
import mod.emt.harkenscythe.init.HSSoundEvents;
import mod.emt.harkenscythe.item.HSItemEssenceKeeperBlood;

public class HSToolVampireKnife extends HSToolSword implements IHSTool
{
    private final float attackSpeed;

    public HSToolVampireKnife(ToolMaterial material, float attackSpeed)
    {
        super(material, EnumRarity.COMMON);
        this.attackSpeed = attackSpeed;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (!player.capabilities.isCreativeMode && !drainBloodContainer(player))
        {
            return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
        }
        if (!world.isRemote)
        {
            // Throw five knives at once!
            for (int i = -2; i < 3; i++)
            {
                HSEntityVampireKnife sword = new HSEntityVampireKnife(world, player);
                ItemStack stack = player.getHeldItem(hand);
                sword.shoot(player, player.rotationPitch, player.rotationYaw + (i * 15.0F), 0.0F, 1.5F, 3.0F);
                sword.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack));
                sword.setGlowing(true);
                world.spawnEntity(sword);
            }

            player.getCooldownTracker().setCooldown(this, 5);
            world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.ITEM_VAMPIRE_KNIFE_THROW.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 0.6F + world.rand.nextFloat());
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

    private boolean drainBloodContainer(EntityPlayer player)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof HSItemEssenceKeeperBlood)
            {
                Item item = stack.getItem();
                if (stack.getItemDamage() + 1 < stack.getMaxDamage())
                {
                    stack.setItemDamage(stack.getItemDamage() + 1);
                }
                else
                {
                    stack.shrink(1);
                    if (item == HSItems.essence_keeper_blood)
                    {
                        player.inventory.addItemStackToInventory(new ItemStack(HSItems.essence_keeper));
                    }
                    else if (item == HSItems.essence_vessel_blood)
                    {
                        player.inventory.addItemStackToInventory(new ItemStack(HSItems.essence_vessel));
                    }
                }
                return true;
            }
        }
        return false;
    }
}
