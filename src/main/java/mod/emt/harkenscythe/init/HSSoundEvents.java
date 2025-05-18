package mod.emt.harkenscythe.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import mod.emt.harkenscythe.HarkenScythe;

public enum HSSoundEvents
{
    BLOCK_ABSORBER_ITEM_INSERT("block.absorber.item_insert"),
    BLOCK_ABSORBER_ITEM_REMOVE("block.absorber.item_remove"),
    BLOCK_ALTAR_BOOK_CLOSE("block.altar.book_close"),
    BLOCK_ALTAR_BOOK_OPEN("block.altar.book_open"),
    BLOCK_ALTAR_CRAFT("block.altar.craft"),
    BLOCK_ALTAR_ITEM_INSERT("block.altar.item_insert"),
    BLOCK_ALTAR_ITEM_REMOVE("block.altar.item_remove"),
    BLOCK_BIOMASS_BREAK("block.biomass.break"),
    BLOCK_BIOMASS_HARVEST("block.biomass.harvest"),
    BLOCK_BIOMASS_STEP("block.biomass.step"),
    BLOCK_BLOOD_ABSORBER_LOOP("block.blood_absorber.loop"),
    BLOCK_BLOOD_ABSORBER_START("block.blood_absorber.start"),
    BLOCK_BLOOD_ABSORBER_STOP("block.blood_absorber.stop"),
    BLOCK_BLOOD_ALTAR_FAIL("block.blood_altar.fail"),
    BLOCK_BLOOD_ALTAR_LOOP("block.blood_altar.loop"),
    BLOCK_BLOOD_ALTAR_SUCCESS("block.blood_altar.success"),
    BLOCK_LIVINGMETAL_BREAK("block.livingmetal.break"),
    BLOCK_LIVINGMETAL_STEP("block.livingmetal.step"),
    BLOCK_SOUL_ABSORBER_LOOP("block.soul_absorber.loop"),
    BLOCK_SOUL_ABSORBER_START("block.soul_absorber.start"),
    BLOCK_SOUL_ABSORBER_STOP("block.soul_absorber.stop"),
    BLOCK_SOUL_ALTAR_FAIL("block.soul_altar.fail"),
    BLOCK_SOUL_ALTAR_LOOP("block.soul_altar.loop"),
    BLOCK_SOUL_ALTAR_SUCCESS("block.soul_altar.success"),
    ENCHANTMENT_AUTO_REAP("enchantment.auto_reap"),
    ENTITY_ECTOGLOBIN_IDLE("entity.ectoglobin.idle"),
    ENTITY_ESSENCE_BLOOD_DESPAWN("entity.essence.blood.despawn"),
    ENTITY_ESSENCE_BLOOD_SPAWN("entity.essence.blood.spawn"),
    ENTITY_ESSENCE_SOUL_DESPAWN("entity.essence.soul.despawn"),
    ENTITY_ESSENCE_SOUL_SPAWN("entity.essence.soul.spawn"),
    ENTITY_EXOSPIDER_DEATH("entity.exospider.death"),
    ENTITY_EXOSPIDER_HURT("entity.exospider.hurt"),
    ENTITY_EXOSPIDER_STEP("entity.exospider.step"),
    ENTITY_HARBINGER_DEATH("entity.harbinger.death"),
    ENTITY_HARBINGER_HURT("entity.harbinger.hurt"),
    ENTITY_HARBINGER_IDLE("entity.harbinger.idle"),
    ENTITY_HEMOGLOBIN_IDLE("entity.hemoglobin.idle"),
    ENTITY_SPECTRAL_HUMAN_HURT("entity.spectral_human.hurt"),
    ENTITY_SPECTRAL_MINER_DEATH("entity.spectral_miner.death"),
    ENTITY_SPECTRAL_MINER_HURT("entity.spectral_miner.hurt"),
    ENTITY_SPECTRAL_MINER_RUN("entity.spectral_miner.run"),
    ENTITY_SPECTRAL_MINER_STEP("entity.spectral_miner.step"),
    ENTITY_WITHERS_FURY_DEATH("entity.withers_fury.death"),
    ENTITY_WITHERS_FURY_HURT("entity.withers_fury.hurt"),
    ENTITY_WITHERS_FURY_IDLE("entity.withers_fury.idle"),
    EQUIP_BIOMASS("equip.biomass"),
    EQUIP_LIVINGMETAL("equip.livingmetal"),
    EQUIP_ROBE("equip.robe"),
    GUIDE_CLOSE("guide.close"),
    GUIDE_FLIP("guide.flip"),
    GUIDE_OPEN("guide.open"),
    ITEM_ATHAME_CREATE("item.athame.create"),
    ITEM_BLOOD_BUTCHERER_SWING("item.blood_butcherer.swing"),
    ITEM_BOTTLE_ESSENCE("item.bottle.essence"),
    ITEM_BOTTLE_INSERT("item.bottle.insert"),
    ITEM_BOTTLE_REMOVE("item.bottle.remove"),
    ITEM_CREEP_BALL_USE("item.creep_ball.use"),
    ITEM_DEADTIME_WATCH_ACTIVATE("item.deadtime_watch.activate"),
    ITEM_DIMENSIONAL_MIRROR_INACTIVE("item.dimensional_mirror.inactive"),
    ITEM_DIMENSIONAL_MIRROR_TELEPORT("item.dimensional_mirror.teleport"),
    ITEM_GLAIVE_STAB("item.glaive.stab"),
    ITEM_GLAIVE_TILL("item.glaive.till"),
    ITEM_HEARKENGRIM_HIT("item.hearkengrim.hit"),
    ITEM_HEARKENGRIM_SLASH("item.hearkengrim.slash"),
    ITEM_LADY_HARKEN_HIT("item.lady_harken.hit"),
    ITEM_NECRONOMICON_ACTIVE("item.necronomicon.activate"),
    ITEM_POTION_BREAK("item.potion.break"),
    ITEM_POTION_THROW("item.potion.throw"),
    ITEM_SCYTHE_SWING("item.scythe.swing"),
    ITEM_VAMPIRE_KNIFE_THROW("item.vampire_knife.throw"),
    RANDOM_BLOOD("random.blood"),
    RANDOM_SOUL("random.soul"),
    RANDOM_SUMMON("random.summon");

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
