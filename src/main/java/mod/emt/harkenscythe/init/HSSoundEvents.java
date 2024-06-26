package mod.emt.harkenscythe.init;

import mod.emt.harkenscythe.HarkenScythe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(HarkenScythe.MOD_ID)
public class HSSoundEvents
{
    public static final SoundEvent BLOCK_BLOOD_ALTAR_ENCHANT = new SoundEvent(new ResourceLocation(HarkenScythe.MOD_ID, "block.soul_altar.enchant"));
    public static final SoundEvent BLOCK_SOUL_ALTAR_ENCHANT = new SoundEvent(new ResourceLocation(HarkenScythe.MOD_ID, "block.blood_altar.enchant"));
    public static final SoundEvent ITEM_ATHAME_CREATE = new SoundEvent(new ResourceLocation(HarkenScythe.MOD_ID, "item.athame.create"));
    public static final SoundEvent ITEM_POTION_BREAK = new SoundEvent(new ResourceLocation(HarkenScythe.MOD_ID, "item.potion.break"));
    public static final SoundEvent ITEM_POTION_THROW = new SoundEvent(new ResourceLocation(HarkenScythe.MOD_ID, "item.potion.throw"));
    public static final SoundEvent ESSENCE_BLOOD_SPAWN = new SoundEvent(new ResourceLocation(HarkenScythe.MOD_ID, "essence.blood.spawn"));
    public static final SoundEvent ESSENCE_SOUL_SPAWN = new SoundEvent(new ResourceLocation(HarkenScythe.MOD_ID, "essence.soul.spawn"));
}
