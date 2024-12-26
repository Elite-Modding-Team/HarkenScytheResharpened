mods.harkenscythe.bloodAltar.recipeBuilder()
        .input(item('minecraft:cobblestone'))
        .output(item('minecraft:gravel'))
        .requiredBlood(42)
        .register()

mods.harkenscythe.bloodAltar.removeByInput(item('minecraft:glass_bottle'))
mods.harkenscythe.bloodAltar.removeByOutput(item('harkenscythe:bloodweave_cloth'))
//mods.harkenscythe.bloodAltar.removeAll()

mods.harkenscythe.soulAltar.recipeBuilder()
        .input(item('minecraft:gravel'))
        .output(item('minecraft:sand'))
        .requiredSouls(69)
        .register()

mods.harkenscythe.soulAltar.removeByInput(item('minecraft:cookie'))
mods.harkenscythe.soulAltar.removeByOutput(item('harkenscythe:soul_cake'))
//mods.harkenscythe.soulAltar.removeAll()
