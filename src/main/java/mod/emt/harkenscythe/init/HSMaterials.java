package mod.emt.harkenscythe.init;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import mod.emt.harkenscythe.config.HSConfig;

@SuppressWarnings("DataFlowIssue")
public class HSMaterials
{
    public static final ItemArmor.ArmorMaterial ARMOR_BIOMASS = EnumHelper.addArmorMaterial("hs_armor_biomass", "biomass", 24, new int[] {2, 5, 6, 2}, 20, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, 1.0F).setRepairItem(new ItemStack(HSItems.biomass));
    public static final ItemArmor.ArmorMaterial ARMOR_LIVINGMETAL = EnumHelper.addArmorMaterial("hs_armor_livingmetal", "livingmetal", 24, new int[] {2, 5, 6, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F).setRepairItem(new ItemStack(HSItems.livingmetal_ingot));
    public static final ItemArmor.ArmorMaterial ARMOR_BLOODWEAVE = EnumHelper.addArmorMaterial("hs_armor_bloodweave", "bloodweave", 15, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F).setRepairItem(new ItemStack(Item.getItemFromBlock(HSBlocks.bloodweave_cloth)));
    public static final ItemArmor.ArmorMaterial ARMOR_SOULWEAVE = EnumHelper.addArmorMaterial("hs_armor_soulweave", "soulweave", 15, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F).setRepairItem(new ItemStack(Item.getItemFromBlock(HSBlocks.soulweave_cloth)));

    public static final Item.ToolMaterial TOOL_ATHAME_BASIC = EnumHelper.addToolMaterial("hs_tool_athame_basic", 2, 250, 6.0F, -1.5F, 14).setRepairItem(new ItemStack(Items.IRON_INGOT));
    public static final Item.ToolMaterial TOOL_BIOMASS = EnumHelper.addToolMaterial("hs_tool_biomass", 3, 481, 7.5F, 2.5F, 20).setRepairItem(new ItemStack(HSItems.biomass));
    public static final Item.ToolMaterial TOOL_LIVINGMETAL = EnumHelper.addToolMaterial("hs_tool_livingmetal", 3, 481, 7.5F, 2.5F, 20).setRepairItem(new ItemStack(HSItems.livingmetal_ingot));
    public static final Item.ToolMaterial TOOL_REAPER = EnumHelper.addToolMaterial("hs_tool_reaper", 4, 2466, 9.0F, 6.0F, 25).setRepairItem(new ItemStack(Items.BONE));
    public static final Item.ToolMaterial TOOL_SPECTRAL = EnumHelper.addToolMaterial("hs_tool_spectral", 4, 2466, 9.0F, 6.0F, 25).setRepairItem(new ItemStack(HSItems.soul_essence));
    public static final Item.ToolMaterial TOOL_LADY_HARKEN = EnumHelper.addToolMaterial("hs_tool_lady_harken", 4, 2466, 9.0F, 6.0F, 25).setRepairItem(new ItemStack(HSItems.soul_essence));

    // Non-Repairable
    public static final Item.ToolMaterial TOOL_BLOOD_BUTCHERER = EnumHelper.addToolMaterial("hs_tool_blood_butcherer", 4, HSConfig.ITEMS.bloodButchererMaxCharges, 9.0F, 11.0F, 25);
    public static final Item.ToolMaterial TOOL_VAMPIRE_KNIFE = EnumHelper.addToolMaterial("hs_tool_vampire_knife", 4, HSConfig.ITEMS.vampireKnifeMaxCharges, 9.0F, 0.0F, 25);
}
