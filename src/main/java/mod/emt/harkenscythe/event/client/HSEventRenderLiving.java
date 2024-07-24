package mod.emt.harkenscythe.event.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.entity.HSEntitySpectralHuman;
import mod.emt.harkenscythe.entity.HSEntitySpectralMiner;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSEventRenderLiving
{
    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre event)
    {
        // TODO: Replace with entity data instead of name tags
        if (event.getEntity().getCustomNameTag().startsWith("Spectral"))
        {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(0.6F, 0.6F, 1.0F, 0.6F);
        }
        else if (event.getEntity() instanceof HSEntitySpectralHuman || event.getEntity() instanceof HSEntitySpectralMiner)
        {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(0.8F, 0.8F, 1.0F, 0.8F);
        }
    }

    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post event)
    {
        // TODO: Replace with entity data instead of name tags
        if (event.getEntity().getCustomNameTag().startsWith("Spectral") || event.getEntity() instanceof HSEntitySpectralHuman || event.getEntity() instanceof HSEntitySpectralMiner)
        {
            GlStateManager.disableBlend();
        }
    }
}
