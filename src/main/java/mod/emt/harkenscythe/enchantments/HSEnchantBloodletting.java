package mod.emt.harkenscythe.enchantments;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entities.HSEntityBlood;
import mod.emt.harkenscythe.init.HSSoundEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class HSEnchantBloodletting extends Enchantment
{
    public HSEnchantBloodletting(String name)
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName(HarkenScythe.MOD_ID + "." + name);
        this.setRegistryName(HarkenScythe.MOD_ID, name);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity entity, int level)
    {
        World world = user.getEntityWorld();
        if (!world.isRemote && entity instanceof EntityLivingBase)
        {
            if (user.getRNG().nextFloat() < 0.1F * level)
            {
                HSEntityBlood blood = new HSEntityBlood(world);
                blood.setPosition(entity.posX, entity.posY, entity.posZ);
                world.spawnEntity(blood);
                world.playSound(null, entity.getPosition(), HSSoundEvents.ESSENCE_BLOOD_SPAWN, SoundCategory.NEUTRAL, 1.0F, 1.5F / (world.rand.nextFloat() * 0.4F + 1.2F));
            }
        }
    }
}
