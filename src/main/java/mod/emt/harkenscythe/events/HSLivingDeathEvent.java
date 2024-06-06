package mod.emt.harkenscythe.events;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entities.HSSoul;
import mod.emt.harkenscythe.items.HSScythe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSLivingDeathEvent
{
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        DamageSource damageSource = event.getSource();
        if (!world.isRemote && damageSource.getTrueSource() instanceof EntityPlayer && damageSource.getDamageType().equals("hs_reap"))
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            if (player.getHeldItemMainhand().getItem() instanceof HSScythe)
            {
                HSSoul soul = new HSSoul(world);
                soul.setPosition(entity.posX, entity.posY, entity.posZ);
                world.spawnEntity(soul);
            }
        }
    }
}
