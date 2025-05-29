package mod.emt.harkenscythe.util;

import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;
import mod.emt.harkenscythe.HarkenScythe;

@Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
public class HSAttributeModifier
{
    // Attribute UUIDs
    public static final UUID ARMOR_ID = UUID.fromString("AC5A5C93-3817-4616-9291-A88D582E52C6");
    public static final UUID ARMOR_TOUGHNESS_ID = UUID.fromString("7D600623-4498-4214-887B-7A65228A07F4");
    public static final UUID ATTACK_DAMAGE_ID = UUID.fromString("26CA5F48-A789-490D-83C2-FA240B5DEFF6");
    public static final UUID ATTACK_SPEED_ID = UUID.fromString("46D8135F-8FF5-47FB-8F21-B1CE09F9F2FC");
    public static final UUID KNOCKBACK_RESISTANCE_ID = UUID.fromString("E1005A2A-5E6C-4805-ADE8-DBC718976040");
    public static final UUID LIFESTEAL_ID = UUID.fromString("1A24C14B-292E-448D-9015-140337CD9BDB");
    public static final UUID LUCK_ID = UUID.fromString("F69CFA96-6422-4F5C-AA21-A0E4569AF8D5");
    public static final UUID MAX_HEALTH_ID = UUID.fromString("5E9197FE-F701-4866-8EF2-AB3648708DE6");
    public static final UUID MOVEMENT_SPEED_ID = UUID.fromString("A1376F2D-2AE4-47CF-9B39-10BBE852ACD9");
    public static final UUID REACH_DISTANCE_ID = UUID.fromString("14D707B8-D3E8-4BDA-8C54-F196F80B5C94");

    // Attributes
    public static final IAttribute LIFESTEAL = new RangedAttribute(null, HarkenScythe.MOD_ID + ".lifesteal", 0.0D, 0.0D, Double.MAX_VALUE).setShouldWatch(true);

    // Registers attributes to all entities
    @SubscribeEvent
    public static void onEntityConstructEvent(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            registerAttribute(player.getAttributeMap(), LIFESTEAL);
        }
    }

    private static void registerAttribute(AbstractAttributeMap attributeMap, IAttribute attribute)
    {
        if (attributeMap.getAttributeInstance(attribute) == null)
        {
            attributeMap.registerAttribute(attribute);
        }
    }
}
