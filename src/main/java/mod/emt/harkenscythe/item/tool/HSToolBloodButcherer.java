package mod.emt.harkenscythe.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSPotions;
import mod.emt.harkenscythe.init.HSRegistry;
import mod.emt.harkenscythe.init.HSSoundEvents;

public class HSToolBloodButcherer extends HSToolSword implements IHSTool
{
    private final float attackSpeed;

    public HSToolBloodButcherer(ToolMaterial material)
    {
        super(material, EnumRarity.COMMON);
        this.setMaxDamage(HSConfig.ITEMS.bloodButchererMaxCharges);
        this.attackSpeed = 0.6F;
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
        entityLiving.world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, HSSoundEvents.ITEM_BLOOD_BUTCHERER_SWING.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.5F / (entityLiving.world.rand.nextFloat() * 0.4F + 1.2F));
        return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return 9443858;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack)
    {
        return HSRegistry.RARITY_BLOODY;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (attacker instanceof EntityPlayer)
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

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Damage modifier", this.getAttackDamage() + 3.0D, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Speed modifier", this.attackSpeed - 4.0D, 0));
        }

        return multimap;
    }
}
