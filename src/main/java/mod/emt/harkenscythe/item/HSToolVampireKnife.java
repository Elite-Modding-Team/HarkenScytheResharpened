package mod.emt.harkenscythe.item.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.world.World;
import mod.emt.harkenscythe.entity.HSEntityVampireKnife;
import mod.emt.harkenscythe.init.HSSoundEvents;

// TODO: Remove durability and make it utilize blood essence instead. The projectiles need a different texture. Disable enchanting for now.
@SuppressWarnings("deprecation")
public class HSToolVampireKnife extends HSToolSword implements IHSTool
{
    private final float attackSpeed;
    private final EnumRarity rarity;

    public HSToolVampireKnife(float attackSpeed, EnumRarity rarity)
    {
        super(ToolMaterial.IRON, rarity); // TODO: Unique material
        this.attackSpeed = attackSpeed;
        this.rarity = rarity;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (!world.isRemote)
        {
            // Throw five knives at once!
            for (int i = -2; i < 3; i++)
            {
                HSEntityVampireKnife sword = new HSEntityVampireKnife(world, player);
                ItemStack stack = player.getHeldItem(hand);
                sword.shoot(player, player.rotationPitch, player.rotationYaw + (i * 15.0F), 0.0F, 1.5F, 3.0F);
                sword.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack));
                world.spawnEntity(sword);
            }

            player.getCooldownTracker().setCooldown(this, 5);
            world.playSound(null, player.posX, player.posY, player.posZ, HSSoundEvents.ITEM_VAMPIRE_KNIFE_THROW.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 0.6F + world.rand.nextFloat());
        } else
        {
            player.swingArm(hand);
        }

        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
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
}
