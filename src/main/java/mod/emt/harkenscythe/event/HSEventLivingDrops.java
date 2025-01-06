package mod.emt.harkenscythe.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.config.HSConfig;
import mod.emt.harkenscythe.init.HSItems;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLivingDrops
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDrops(LivingDropsEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();

        if (entity.getEntityData().getBoolean("IsSpectral"))
        {
            event.getDrops().clear();
        }
        // Configurable chance (defaults to 1/1000 = 0.001%) to get a Damaged Vampire Knife drop from any mob in the Nether.
        // Entity must be killed by a player, must be a monster, must not be too weak, and must not be spectral for this to happen.
        else if (!entity.world.isRemote && entity.world.provider.isNether() && entity.getRNG().nextDouble() <= HSConfig.ITEMS.vampireKnifeDropChance && entity.getMaxHealth() >= 16.0F && entity instanceof EntityMob && entity.getAttackingEntity() instanceof EntityPlayer)
        {
            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(HSItems.damaged_vampire_knife)));
        }
    }
}
