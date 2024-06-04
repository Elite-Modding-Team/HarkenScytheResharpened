package mod.emt.harkenscythe.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class HSScythe extends ItemSword
{
    private float AttackSpeed;

    public HSScythe(ToolMaterial material, float attackSpeedIn)
    {
        super(material);
        setCreativeTab(HarkenScythe.TAB);
        AttackSpeed = attackSpeedIn;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        float damage = this.getAttackDamage() + 4.0F; // Has to be done like this otherwise it'll calculate wrong
        float range = 3.0F;
        AxisAlignedBB bb = new AxisAlignedBB(entityLiving.posX - range, entityLiving.posY - range, entityLiving.posZ - range, entityLiving.posX + range, entityLiving.posY + range, entityLiving.posZ + range);

        for (int i = 0; i < worldIn.getEntitiesWithinAABB(EntityLiving.class, bb).size(); i++)
        {
            EntityLiving entityInAABB = worldIn.getEntitiesWithinAABB(EntityLiving.class, bb).get(i);

            if (Math.min(1.0F, (getMaxItemUseDuration(stack) - timeLeft) / 20.0F) >= 1.0F)
            {
                // Damage x 2 (Mojang is very strange with damage values...)
                entityInAABB.attackEntityFrom(DamageSource.causeMobDamage(entityLiving), damage * 2);
            }
        }

        if (Math.min(1.0F, (getMaxItemUseDuration(stack) - timeLeft) / 20.0F) >= 1.0F && entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;
            player.swingArm(EnumHand.MAIN_HAND);
            player.spawnSweepParticles();
            player.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
            stack.damageItem(2, player);
            player.addStat(StatList.getObjectUseStats(this));
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
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
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", (double) this.getAttackDamage() + 3.0D, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", (double) this.AttackSpeed - 4.0D, 0));
        }

        return multimap;
    }
}
