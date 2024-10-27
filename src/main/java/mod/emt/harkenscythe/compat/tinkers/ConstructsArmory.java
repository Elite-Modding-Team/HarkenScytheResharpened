package mod.emt.harkenscythe.compat.tinkers;

import c4.conarm.lib.materials.*;
import slimeknights.tconstruct.library.TinkerRegistry;

public class ConstructsArmory
{
    // These traits are for armor and not tools
    //public static final AbstractTrait BLOOD_INTERVENTION = new TraitBloodIntervention();
    //public static final AbstractTrait SOUL_INTERVENTION = new TraitSoulIntervention();

    // Materials are already registered in the tools class, we are just registering support for armor sets here
    public static void preInit()
    {
        TinkerRegistry.addMaterialStats(TinkersConstruct.BIOMASS,
            new CoreMaterialStats(20, 8.0F),
            new PlatesMaterialStats(1.0F, 3, 0.0F),
            new TrimMaterialStats(15));
        ArmorMaterials.addArmorTrait(TinkersConstruct.BIOMASS, TinkersConstruct.BLOOD_INTERVENTION, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.BIOMASS, TinkersConstruct.BLOOD_INTERVENTION, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.BIOMASS, TinkersConstruct.BLOOD_INTERVENTION, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstruct.LIVINGMETAL,
            new CoreMaterialStats(20, 8.0F),
            new PlatesMaterialStats(1.0F, 3, 0.0F),
            new TrimMaterialStats(15));
        ArmorMaterials.addArmorTrait(TinkersConstruct.LIVINGMETAL, TinkersConstruct.SOUL_INTERVENTION, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.LIVINGMETAL, TinkersConstruct.SOUL_INTERVENTION, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.LIVINGMETAL, TinkersConstruct.SOUL_INTERVENTION, ArmorMaterialType.TRIM);
    }
}
