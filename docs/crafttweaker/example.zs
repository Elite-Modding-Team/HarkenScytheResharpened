// --------------
// --- IMPORT ---
// --------------
import mods.harkenscythe.Altar;

// -----------
// --- ADD ---
// -----------
// Altar.addBloodAltarRecipe(<input>, <output>, <requiredBlood>);
// Altar.addSoulAltarRecipe(<input>, <output>, <requiredSouls>);
// --------------
Altar.addBloodAltarRecipe(<minecraft:cobblestone>, <minecraft:gravel>, 42);
Altar.addSoulAltarRecipe(<minecraft:gravel>, <minecraft:sand>, 69);

// --------------
// --- REMOVE ---
// --------------
// Altar.removeBloodAltarRecipe(<input>, <output>, <requiredBlood>);
// Altar.removeSoulAltarRecipe(<input>, <output>, <requiredSouls>);
// --------------
Altar.removeBloodAltarRecipe(<minecraft:glass_bottle>, <minecraft:dragon_breath>, 40);
Altar.removeSoulAltarRecipe(<minecraft:cake>, <harkenscythe:soul_cake>, 10);
