package mod.emt.harkenscythe.events;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entities.HSEntityBlood;
import mod.emt.harkenscythe.items.tools.HSGlaive;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSLivingHurtEvent
{
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        DamageSource damageSource = event.getSource();
        if (!world.isRemote && damageSource.getTrueSource() instanceof EntityPlayer && damageSource.getDamageType().equals("hs_reap"))
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            if (player.getHeldItemMainhand().getItem() instanceof HSGlaive)
            {
                HSEntityBlood blood = new HSEntityBlood(world);
                blood.setPosition(entity.posX, entity.posY, entity.posZ);
                world.spawnEntity(blood);
            }
        }
    }
}
