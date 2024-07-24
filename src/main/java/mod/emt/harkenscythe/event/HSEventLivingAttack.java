package mod.emt.harkenscythe.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.item.HSItemDimensionalMirror;
import mod.emt.harkenscythe.item.HSItemNecronomicon;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSEventLivingAttack
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingAttack(LivingAttackEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack stack = player.getActiveItemStack();
            if (stack.getItem() instanceof HSItemDimensionalMirror || stack.getItem() instanceof HSItemNecronomicon)
            {
                player.resetActiveHand();
            }
        }
    }
}
