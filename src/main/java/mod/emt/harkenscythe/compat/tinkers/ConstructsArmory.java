package mod.emt.harkenscythe.compat.tinkers;

import c4.conarm.lib.materials.*;
import c4.conarm.lib.traits.AbstractArmorTrait;
import mod.emt.harkenscythe.compat.tinkers.traits.armor.TraitBloodInterventionArmor;
import mod.emt.harkenscythe.compat.tinkers.traits.armor.TraitSoulInterventionArmor;
import slimeknights.tconstruct.library.TinkerRegistry;

public class ConstructsArmory
{
    // These traits are for armor and not tools
    public static final AbstractArmorTrait BLOOD_INTERVENTION_ARMOR = new TraitBloodInterventionArmor();
    public static final AbstractArmorTrait SOUL_INTERVENTION_ARMOR = new TraitSoulInterventionArmor();

    // Materials are already registered in the tools class, we are just registering support for armor sets here
    public static void preInit()
    {
        TinkerRegistry.addMaterialStats(TinkersConstruct.BIOMASS,
            new CoreMaterialStats(15.0F, 17.0F),
            new PlatesMaterialStats(1.0F, 8.0F, 2.0F),
            new TrimMaterialStats(10.0F));
        ArmorMaterials.addArmorTrait(TinkersConstruct.BIOMASS, BLOOD_INTERVENTION_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.BIOMASS, BLOOD_INTERVENTION_ARMOR, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.BIOMASS, BLOOD_INTERVENTION_ARMOR, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstruct.LIVINGMETAL,
            new CoreMaterialStats(15.0F, 17.0F),
            new PlatesMaterialStats(1.0F, 8.0F, 2.0F),
            new TrimMaterialStats(10.0F));
        ArmorMaterials.addArmorTrait(TinkersConstruct.LIVINGMETAL, SOUL_INTERVENTION_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.LIVINGMETAL, SOUL_INTERVENTION_ARMOR, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.LIVINGMETAL, SOUL_INTERVENTION_ARMOR, ArmorMaterialType.TRIM);
    }
}
