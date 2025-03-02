package mod.emt.harkenscythe.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import mod.emt.harkenscythe.HarkenScythe;

public enum HSSoundEvents
{
    BLOCK_ALTAR_BOOK_CLOSE("block.altar.book_close"),
    BLOCK_ALTAR_BOOK_OPEN("block.altar.book_open"),
    BLOCK_BIOMASS_BREAK("block.biomass.break"),
    BLOCK_BIOMASS_HARVEST("block.biomass.harvest"),
    BLOCK_BIOMASS_STEP("block.biomass.step"),
    BLOCK_BLOOD_ALTAR_LOOP("block.blood_altar.loop"),
    BLOCK_BLOOD_ABSORBER_LOOP("block.blood_absorber.loop"),
    BLOCK_BLOOD_ABSORBER_START("block.blood_absorber.start"),
    BLOCK_BLOOD_ABSORBER_STOP("block.blood_absorber.stop"),
    BLOCK_BLOOD_ALTAR_ENCHANT("block.blood_altar.enchant"),
    BLOCK_BLOOD_ALTAR_ENCHANT_FAIL("block.blood_altar.enchant_fail"),
    BLOCK_BOTTLE_PLACE("block.bottle.place"),
    BLOCK_BOTTLE_REMOVE("block.bottle.remove"),
    BLOCK_LIVINGMETAL_BREAK("block.livingmetal.break"),
    BLOCK_LIVINGMETAL_STEP("block.livingmetal.step"),
    BLOCK_SOUL_ALTAR_LOOP("block.soul_altar.loop"),
    BLOCK_SOUL_ABSORBER_LOOP("block.soul_absorber.loop"),
    BLOCK_SOUL_ABSORBER_START("block.soul_absorber.start"),
    BLOCK_SOUL_ABSORBER_STOP("block.soul_absorber.stop"),
    BLOCK_SOUL_ALTAR_ENCHANT("block.soul_altar.enchant"),
    BLOCK_SOUL_ALTAR_ENCHANT_FAIL("block.soul_altar.enchant_fail"),
    ITEM_ATHAME_CREATE("item.athame.create"),
    ITEM_BLOOD_BUTCHERER_SWING("item.blood_butcherer.swing"),
    ITEM_CREEP_BALL_USE("item.creep_ball.use"),
    ITEM_DEADTIME_WATCH_ACTIVATE("item.deadtime_watch.activate"),
    ITEM_GLAIVE_TILL("item.glaive.till"),
    ITEM_MIRROR_INACTIVE("item.mirror.inactive"),
    ITEM_MIRROR_TELEPORT("item.mirror.teleport"),
    ITEM_NECRONOMICON_ACTIVE("item.necronomicon.activate"),
    ITEM_POTION_BREAK("item.potion.break"),
    ITEM_POTION_THROW("item.potion.throw"),
    ITEM_SCYTHE_ACTIVATE("item.scythe.activate"),
    ITEM_VAMPIRE_KNIFE_THROW("item.vampire_knife.throw"),
    ESSENCE_BLOOD_DESPAWN("essence.blood.despawn"),
    ESSENCE_BLOOD_SPAWN("essence.blood.spawn"),
    ESSENCE_SOUL_DESPAWN("essence.soul.despawn"),
    ESSENCE_SOUL_SPAWN("essence.soul.spawn"),
    ESSENCE_SOUL_SUMMON("essence.soul.summon"),
    ENTITY_SPECTRAL_HUMAN_HURT("entity.spectral_human.hurt"),
    ENTITY_SPECTRAL_MINER_HURT("entity.spectral_miner.hurt"),
    ENTITY_SPECTRAL_MINER_DEATH("entity.spectral_miner.death"),
    ENTITY_SPECTRAL_MINER_STEP("entity.spectral_miner.step"),
    ENTITY_SPECTRAL_MINER_RUN("entity.spectral_miner.run"),
    ENTITY_HARBINGER_HURT("entity.harbinger.hurt"),
    ENTITY_HARBINGER_DEATH("entity.harbinger.death"),
    ENTITY_HARBINGER_IDLE("entity.harbinger.idle"),
    ENTITY_EXOSPIDER_HURT("entity.exospider.hurt"),
    ENTITY_EXOSPIDER_DEATH("entity.exospider.death"),
    ENTITY_EXOSPIDER_STEP("entity.exospider.step"),
    GUIDE_FLIP("guide.flip"),
    GUIDE_OPEN("guide.open"),
    GUIDE_CLOSE("guide.close");

    private final SoundEvent soundEvent;

    HSSoundEvents(String path)
    {
        ResourceLocation resourceLocation = new ResourceLocation(HarkenScythe.MOD_ID, path);
        this.soundEvent = new SoundEvent(resourceLocation);
        this.soundEvent.setRegistryName(resourceLocation);
    }

    public SoundEvent getSoundEvent()
    {
        return this.soundEvent;
    }
}
