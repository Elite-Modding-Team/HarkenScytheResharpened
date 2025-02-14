package mod.emt.harkenscythe.compat.thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

// TODO: Entities are not registering aspects for some reason...
@SuppressWarnings("deprecation")
public class HSThaumcraftPlugin
{
    @SubscribeEvent
    public static void registerAspects(AspectRegistryEvent event)
    {
        AspectEventProxy proxy = event.register;

        // Blocks
        proxy.registerObjectTag(new ItemStack(HSBlocks.biomass_crop), new AspectList().add(Aspect.LIFE, 10).add(Aspect.FLUX, 5));
        proxy.registerObjectTag(new ItemStack(HSBlocks.bloodweave_cloth), new AspectList().add(Aspect.AVERSION, 10).add(Aspect.BEAST, 10).add(Aspect.CRAFT, 5));
        proxy.registerObjectTag(new ItemStack(HSBlocks.creep_block), new AspectList().add(Aspect.FLUX, 3).add(Aspect.SOUL, 3).add(Aspect.TRAP, 1));
        proxy.registerObjectTag(new ItemStack(HSBlocks.creep_block_tilled), new AspectList().add(Aspect.FLUX, 3).add(Aspect.SOUL, 3).add(Aspect.TRAP, 1).add(Aspect.AVERSION, 1));
        proxy.registerObjectTag(new ItemStack(HSBlocks.creep_block_tilled_bloodied), new AspectList().add(Aspect.FLUX, 3).add(Aspect.SOUL, 3).add(Aspect.AVERSION, 3).add(Aspect.TRAP, 1));
        proxy.registerObjectTag(new ItemStack(HSBlocks.soul_cake), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 15).add(Aspect.SOUL, 10));
        proxy.registerObjectTag(new ItemStack(HSBlocks.soulweave_cloth), new AspectList().add(Aspect.SOUL, 10).add(Aspect.BEAST, 10).add(Aspect.CRAFT, 5));
        proxy.registerObjectTag(new ItemStack(HSBlocks.spectral_glass), new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.SOUL, 3));
        proxy.registerObjectTag(new ItemStack(HSBlocks.spectral_glass_inverted), new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.SOUL, 3));
        proxy.registerObjectTag(new ItemStack(HSBlocks.spectral_glass_pane), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.SOUL, 1));
        proxy.registerObjectTag(new ItemStack(HSBlocks.spectral_glass_pane_inverted), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.SOUL, 1));

        // Items
        proxy.registerObjectTag(new ItemStack(HSItems.abyssal_fragment), new AspectList().add(Aspect.ELDRITCH, 15).add(Aspect.AURA, 10).add(Aspect.SOUL, 10).add(Aspect.MIND, 10));
        proxy.registerObjectTag(new ItemStack(HSItems.ancient_necronomicon_page), new AspectList().add(Aspect.ELDRITCH, 15).add(Aspect.DEATH, 15).add(Aspect.MAGIC, 15));
        proxy.registerObjectTag(new ItemStack(HSItems.biomass), new AspectList().add(Aspect.LIFE, 10).add(Aspect.FLUX, 10));
        proxy.registerObjectTag(new ItemStack(HSItems.blood_essence), new AspectList().add(Aspect.AVERSION, 1));
        proxy.registerObjectTag(new ItemStack(HSItems.blood_essence_sickly), new AspectList().add(Aspect.AVERSION, 2));
        proxy.registerObjectTag(new ItemStack(HSItems.blood_essence_intoxicated), new AspectList().add(Aspect.AVERSION, 5));
        proxy.registerObjectTag(new ItemStack(HSItems.blood_essence_warped), new AspectList().add(Aspect.AVERSION, 40));
        proxy.registerObjectTag(new ItemStack(HSItems.bloodweave_hood, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 30).add(Aspect.AVERSION, 30));
        proxy.registerObjectTag(new ItemStack(HSItems.bloodweave_robe, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 50).add(Aspect.AVERSION, 50));
        proxy.registerObjectTag(new ItemStack(HSItems.bloodweave_pants, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 40).add(Aspect.AVERSION, 40));
        proxy.registerObjectTag(new ItemStack(HSItems.bloodweave_shoes, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 20).add(Aspect.AVERSION, 20));
        proxy.registerObjectTag(new ItemStack(HSItems.carnage_book), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.MIND, 10).add(Aspect.AVERSION, 10));
        proxy.registerObjectTag(new ItemStack(HSItems.damaged_vampire_knife), new AspectList().add(Aspect.METAL, 20).add(Aspect.CRAFT, 20).add(Aspect.VOID, 20).add(Aspect.EXCHANGE, 20));
        proxy.registerObjectTag(new ItemStack(HSItems.dimensional_mirror, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRYSTAL, 15).add(Aspect.ELDRITCH, 10).add(Aspect.MOTION, 10));
        proxy.registerObjectTag(new ItemStack(HSItems.essence_keeper), new AspectList().add(Aspect.VOID, 20).add(Aspect.ELDRITCH, 20));
        proxy.registerObjectTag(new ItemStack(HSItems.essence_keeper_blood), new AspectList().add(Aspect.AVERSION, 10).add(Aspect.ELDRITCH, 20));
        proxy.registerObjectTag(new ItemStack(HSItems.essence_keeper_soul), new AspectList().add(Aspect.SOUL, 10).add(Aspect.ELDRITCH, 20));
        proxy.registerObjectTag(new ItemStack(HSItems.essence_vessel), new AspectList().add(Aspect.VOID, 40).add(Aspect.ELDRITCH, 40));
        proxy.registerObjectTag(new ItemStack(HSItems.essence_vessel_blood), new AspectList().add(Aspect.AVERSION, 20).add(Aspect.ELDRITCH, 40));
        proxy.registerObjectTag(new ItemStack(HSItems.essence_vessel_soul), new AspectList().add(Aspect.SOUL, 20).add(Aspect.ELDRITCH, 40));
        proxy.registerObjectTag(new ItemStack(HSItems.germinated_biomass_seed), new AspectList().add(Aspect.LIFE, 10).add(Aspect.PLANT, 10).add(Aspect.FLUX, 5));
        proxy.registerObjectTag(new ItemStack(HSItems.harken_athame), new AspectList().add(Aspect.METAL, 12).add(Aspect.AVERSION, 3).add(Aspect.SOUL, 3));
        proxy.registerObjectTag(new ItemStack(HSItems.lady_harken_scythe, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.AURA, 32).add(Aspect.ORDER, 32).add(Aspect.PROTECT, 32));
        proxy.registerObjectTag(new ItemStack(HSItems.livingmetal_ingot), new AspectList().add(Aspect.METAL, 10).add(Aspect.SOUL, 10));
        proxy.registerObjectTag(new ItemStack(HSItems.reaper_guidebook), new AspectList().add(Aspect.MIND, 10).add(Aspect.AVERSION, 3).add(Aspect.SOUL, 3));
        proxy.registerObjectTag(new ItemStack(HSItems.reaper_scythe, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.DEATH, 32).add(Aspect.ENTROPY, 32).add(Aspect.SOUL, 32));
        proxy.registerObjectTag(new ItemStack(HSItems.shadow_book), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.MIND, 10).add(Aspect.SOUL, 10));
        proxy.registerObjectTag(new ItemStack(HSItems.soul_cake), new AspectList().add(Aspect.BEAST, 30).add(Aspect.PROTECT, 8).add(Aspect.CRAFT, 3));
        proxy.registerObjectTag(new ItemStack(HSItems.soul_cookie), new AspectList().add(Aspect.SOUL, 1).add(Aspect.PLANT, 1).add(Aspect.LIFE, 1));
        proxy.registerObjectTag(new ItemStack(HSItems.soul_essence), new AspectList().add(Aspect.SOUL, 1));
        proxy.registerObjectTag(new ItemStack(HSItems.soul_essence_grieving), new AspectList().add(Aspect.SOUL, 2));
        proxy.registerObjectTag(new ItemStack(HSItems.soul_essence_culled), new AspectList().add(Aspect.SOUL, 5));
        proxy.registerObjectTag(new ItemStack(HSItems.soul_essence_wrathful), new AspectList().add(Aspect.SOUL, 40));
        proxy.registerObjectTag(new ItemStack(HSItems.soul_essence_spectral), new AspectList().add(Aspect.SOUL, 20));
        proxy.registerObjectTag(new ItemStack(HSItems.soulweave_hood, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 30).add(Aspect.SOUL, 30));
        proxy.registerObjectTag(new ItemStack(HSItems.soulweave_robe, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 50).add(Aspect.SOUL, 50));
        proxy.registerObjectTag(new ItemStack(HSItems.soulweave_pants, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 40).add(Aspect.SOUL, 40));
        proxy.registerObjectTag(new ItemStack(HSItems.soulweave_shoes, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 20).add(Aspect.SOUL, 20));
        proxy.registerObjectTag(new ItemStack(HSItems.spectral_glass_bottle), new AspectList().add(Aspect.VOID, 5).add(Aspect.CRYSTAL, 3).add(Aspect.SOUL, 3));
        proxy.registerObjectTag(new ItemStack(HSItems.spectral_pickaxe, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.METAL, 44).add(Aspect.SOUL, 44).add(Aspect.TOOL, 32));
        proxy.registerObjectTag(new ItemStack(HSItems.spectral_potion_affliction), new AspectList().add(Aspect.ALCHEMY, 10).add(Aspect.FLUX, 5).add(Aspect.TRAP, 5).add(Aspect.SOUL, 5));
        proxy.registerObjectTag(new ItemStack(HSItems.spectral_potion_flame), new AspectList().add(Aspect.ALCHEMY, 10).add(Aspect.FIRE, 10).add(Aspect.SOUL, 5));
        proxy.registerObjectTag(new ItemStack(HSItems.spectral_potion_purifying), new AspectList().add(Aspect.ALCHEMY, 10).add(Aspect.AURA, 5).add(Aspect.TRAP, 5).add(Aspect.SOUL, 5));
        proxy.registerObjectTag(new ItemStack(HSItems.spectral_potion_water), new AspectList().add(Aspect.WATER, 5).add(Aspect.ENERGY, 3).add(Aspect.SOUL, 3));
        proxy.registerObjectTag(new ItemStack(HSItems.vampire_knife, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.LIFE, 40).add(Aspect.MOTION, 40).add(Aspect.ENERGY, 40).add(Aspect.AVERSION, 40));

        // Entities
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "ectoglobin", new AspectList().add(Aspect.ALCHEMY, 10).add(Aspect.WATER, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "exospider", new AspectList().add(Aspect.PROTECT, 10).add(Aspect.SOUL, 10).add(Aspect.TRAP, 10));
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "harbinger", new AspectList().add(Aspect.EXCHANGE, 20).add(Aspect.TRAP, 20).add(Aspect.SENSES, 20).add(Aspect.SOUL, 20));
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "hemoglobin", new AspectList().add(Aspect.ALCHEMY, 10).add(Aspect.WATER, 15).add(Aspect.SOUL, 15));
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "spectral_human", new AspectList().add(Aspect.SOUL, 10).add(Aspect.MAN, 15).add(Aspect.DEATH, 15));
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "spectral_miner", new AspectList().add(Aspect.TOOL, 20).add(Aspect.MAN, 20).add(Aspect.DEATH, 20).add(Aspect.SOUL, 20));

        // Entities - Misc
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "blood", new AspectList().add(Aspect.AURA, 10).add(Aspect.AVERSION, 10));
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "soul", new AspectList().add(Aspect.AURA, 10).add(Aspect.SOUL, 10));
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "spectral_potion", new AspectList().add(Aspect.SOUL, 5).add(Aspect.CRYSTAL, 10).add(Aspect.MOTION, 10));
        ThaumcraftApi.registerEntityTag(HarkenScythe.MOD_PREFIX + "vampire_knife", new AspectList().add(Aspect.AVERSION, 20).add(Aspect.ENERGY, 20).add(Aspect.MOTION, 20).add(Aspect.LIFE, 20));
    }
}
