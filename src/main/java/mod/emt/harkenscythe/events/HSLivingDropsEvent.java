package mod.emt.harkenscythe.events;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSLivingDropsEvent
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDrops(LivingDropsEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        // TODO: Set entity data to determine spectral variant
        if (entity.getCustomNameTag().contains("Spectral"))
        {
            event.getDrops().clear();
        }
    }
}
