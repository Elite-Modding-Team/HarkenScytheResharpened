package mod.emt.harkenscythe.event;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSEventRenderLiving
{
    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre event)
    {
        // TODO: Replace with entity data instead of name tags
        if (!event.getEntity().getCustomNameTag().contains("Spectral")) return;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(0.5F, 0.5F, 1.0F, 0.5F);
    }

    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post event)
    {
        // TODO: Replace with entity data instead of name tags
        if (!event.getEntity().getCustomNameTag().contains("Spectral")) return;
        GlStateManager.disableBlend();
    }
}
