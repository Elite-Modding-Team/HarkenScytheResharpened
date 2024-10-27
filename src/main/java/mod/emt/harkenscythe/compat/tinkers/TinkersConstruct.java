package mod.emt.harkenscythe.compat.tinkers;

import mod.emt.harkenscythe.compat.tinkers.traits.TraitBloodConjuration;
import mod.emt.harkenscythe.compat.tinkers.traits.TraitBloodIntervention;
import mod.emt.harkenscythe.compat.tinkers.traits.TraitSoulConjuration;
import mod.emt.harkenscythe.compat.tinkers.traits.TraitSoulIntervention;
import mod.emt.harkenscythe.init.HSBlocks;
import mod.emt.harkenscythe.init.HSItems;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.BowStringMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class TinkersConstruct
{
    public static final Material BIOMASS = new Material("biomass", 0x87201B);
    public static final Material BLOODWEAVE_CLOTH = new Material("bloodweave_cloth", 0x79141D);
    public static final Material LIVINGMETAL = new Material("livingmetal", 0x006B9F);
    public static final Material SOULWEAVE_CLOTH = new Material("soulweave_cloth", 0x1E8CA7);

    public static final AbstractTrait BLOOD_CONJURATION = new TraitBloodConjuration();
    public static final AbstractTrait BLOOD_INTERVENTION = new TraitBloodIntervention();
    public static final AbstractTrait SOUL_CONJURATION = new TraitSoulConjuration();
    public static final AbstractTrait SOUL_INTERVENTION = new TraitSoulIntervention();

    public static void registerToolMaterials()
    {
    	TinkerRegistry.addMaterial(BIOMASS);
        TinkerRegistry.addMaterialStats(BIOMASS,
                new HeadMaterialStats(500, 7.5F, 5.5F, HarvestLevels.OBSIDIAN),
                new HandleMaterialStats(1.3F, 50),
                new ExtraMaterialStats(100),
                new BowMaterialStats(0.675F, 1.6F, 7.5F));
        BIOMASS.addTrait(BLOOD_CONJURATION, MaterialTypes.HEAD);
        BIOMASS.addTrait(BLOOD_INTERVENTION, MaterialTypes.HEAD);
        BIOMASS.addTrait(BLOOD_INTERVENTION);

        TinkerRegistry.addMaterial(LIVINGMETAL);
        TinkerRegistry.addMaterialStats(LIVINGMETAL,
                new HeadMaterialStats(500, 7.5F, 5.5F, HarvestLevels.OBSIDIAN),
                new HandleMaterialStats(1.3F, 50),
                new ExtraMaterialStats(100),
                new BowMaterialStats(0.675F, 1.6F, 7.5F));
        LIVINGMETAL.addTrait(SOUL_CONJURATION, MaterialTypes.HEAD);
        LIVINGMETAL.addTrait(SOUL_INTERVENTION, MaterialTypes.HEAD);
        LIVINGMETAL.addTrait(SOUL_INTERVENTION);

        TinkerRegistry.addMaterial(BLOODWEAVE_CLOTH);
        TinkerRegistry.addMaterialStats(BLOODWEAVE_CLOTH,
                new BowStringMaterialStats(2.0F));

        TinkerRegistry.addMaterial(SOULWEAVE_CLOTH);
        TinkerRegistry.addMaterialStats(SOULWEAVE_CLOTH,
                new BowStringMaterialStats(2.0F));
    }

    public static void registerToolRecipes()
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
