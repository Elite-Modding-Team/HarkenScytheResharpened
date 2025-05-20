package mod.emt.harkenscythe.compat.tinkers;

import net.minecraft.item.ItemStack;

import mod.emt.harkenscythe.compat.tinkers.traits.TraitBloodConjuration;
import mod.emt.harkenscythe.compat.tinkers.traits.TraitBloodIntervention;
import mod.emt.harkenscythe.compat.tinkers.traits.TraitSoulConjuration;
import mod.emt.harkenscythe.compat.tinkers.traits.TraitSoulIntervention;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class TinkersConstruct
{
    // These materials are used universally between tools and armor
    public static final Material BIOMASS = new Material("biomass", 0x87201B);
    public static final Material BLOODWEAVE_CLOTH = new Material("bloodweave_cloth", 0x79141D);
    public static final Material LIVINGMETAL = new Material("livingmetal", 0x006B9F);
    public static final Material SOULWEAVE_CLOTH = new Material("soulweave_cloth", 0x1E8CA7);

    // These traits are for tools and not armor
    public static final AbstractTraitLeveled BLOOD_CONJURATION = new TraitBloodConjuration(1);
    public static final AbstractTrait BLOOD_INTERVENTION = new TraitBloodIntervention();
    public static final AbstractTraitLeveled SOUL_CONJURATION = new TraitSoulConjuration(1);
    public static final AbstractTrait SOUL_INTERVENTION = new TraitSoulIntervention();
    
    // Unused but modpack and addon developers might want these?
    public static final AbstractTrait BLOOD_INTERVENTION2 = new TraitBloodConjuration(2);
    public static final AbstractTraitLeveled SOUL_CONJURATION2 = new TraitSoulConjuration(2);

    public static void preInit()
    {
        TinkerRegistry.addMaterialStats(BIOMASS,
            new HeadMaterialStats(500, 7.5F, 5.5F, HarvestLevels.OBSIDIAN),
            new HandleMaterialStats(1.3F, 50),
            new ExtraMaterialStats(100),
            new BowMaterialStats(0.675F, 1.6F, 7.5F));
        BIOMASS.addTrait(BLOOD_CONJURATION, MaterialTypes.HEAD);
        BIOMASS.addTrait(BLOOD_INTERVENTION, MaterialTypes.HEAD);
        BIOMASS.addTrait(BLOOD_INTERVENTION);
        TinkerRegistry.integrate(BIOMASS, null, "Biomass").preInit();

        TinkerRegistry.addMaterialStats(LIVINGMETAL,
            new HeadMaterialStats(500, 7.5F, 5.5F, HarvestLevels.OBSIDIAN),
            new HandleMaterialStats(1.3F, 50),
            new ExtraMaterialStats(100),
            new BowMaterialStats(0.675F, 1.6F, 7.5F));
        LIVINGMETAL.addTrait(SOUL_CONJURATION, MaterialTypes.HEAD);
        LIVINGMETAL.addTrait(SOUL_INTERVENTION, MaterialTypes.HEAD);
        LIVINGMETAL.addTrait(SOUL_INTERVENTION);
        TinkerRegistry.integrate(LIVINGMETAL, null, "Livingmetal").preInit();

        TinkerRegistry.addMaterialStats(BLOODWEAVE_CLOTH,
            new BowStringMaterialStats(2.0F));
        TinkerRegistry.integrate(BLOODWEAVE_CLOTH).preInit();

        TinkerRegistry.addMaterialStats(SOULWEAVE_CLOTH,
            new BowStringMaterialStats(2.0F));
        TinkerRegistry.integrate(SOULWEAVE_CLOTH).preInit();
    }

    public static void init()
    {
        BIOMASS.addCommonItems("Biomass");
        BIOMASS.setRepresentativeItem(HSItems.biomass);
        BIOMASS.setCraftable(true).setCastable(false);

        LIVINGMETAL.addCommonItems("Livingmetal");
        LIVINGMETAL.setRepresentativeItem(HSItems.livingmetal_ingot);
        LIVINGMETAL.setCraftable(true).setCastable(false);

        BLOODWEAVE_CLOTH.addItem(new ItemStack(HSBlocks.bloodweave_cloth), 1, Material.VALUE_Ingot);
        BLOODWEAVE_CLOTH.setRepresentativeItem(HSBlocks.bloodweave_cloth);
        BLOODWEAVE_CLOTH.setCraftable(true).setCastable(false);

        SOULWEAVE_CLOTH.addItem(new ItemStack(HSBlocks.soulweave_cloth), 1, Material.VALUE_Ingot);
        SOULWEAVE_CLOTH.setRepresentativeItem(HSBlocks.soulweave_cloth);
        SOULWEAVE_CLOTH.setCraftable(true).setCastable(false);
    }
}
