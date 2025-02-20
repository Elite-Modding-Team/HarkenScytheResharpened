package mod.emt.harkenscythe.event.client;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.client.particle.HSParticleGlow;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID, value = Side.CLIENT)
public class HSEventTextureStitch
{
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event)
    {
        event.getMap().registerSprite(HSParticleGlow.TEXTURE_BLOOD);
        event.getMap().registerSprite(HSParticleGlow.TEXTURE_SOUL);
    }
}
