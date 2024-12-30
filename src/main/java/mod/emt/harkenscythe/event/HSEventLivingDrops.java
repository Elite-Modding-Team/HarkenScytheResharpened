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

import java.util.List;

import mod.emt.harkenscythe.HarkenScythe;
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
    }

    @SubscribeEvent
    public static void onVampireKnifeDrop(LivingDropsEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        List<EntityItem> drops = event.getDrops();

        // 1/2000 (0.05%) chance to get a Damaged Vampire Knife drop from any mob in the Nether.
        // Entity must be killed by a player, must be a monster, must not be too weak, and must not be spectral for this to happen.
        if (event.getEntity().world.provider.isNether())
        {
            if (entity.world.rand.nextDouble() <= 0.0005D && entity.getMaxHealth() >= 16.0F && entity instanceof EntityMob &&
                    !entity.world.isRemote && entity.getAttackingEntity() instanceof EntityPlayer && !entity.getEntityData().getBoolean("IsSpectral"))
            {
                drops.add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(HSItems.damaged_vampire_knife)));
            }
        }
    }
}
