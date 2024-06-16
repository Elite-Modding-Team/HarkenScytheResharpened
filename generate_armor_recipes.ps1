# Define the armor pieces and dye data values
$armorPieces = @(
    "bloodweave_hood", "bloodweave_robe", "bloodweave_pants", "bloodweave_shoes",
    "soulweave_hood", "soulweave_robe", "soulweave_pants", "soulweave_shoes"
)

$dyes = @{
    "black" = 0
    "red" = 1
    "green" = 2
    "brown" = 3
    "blue" = 4
    "purple" = 5
    "cyan" = 6
    "light_gray" = 7
    "gray" = 8
    "pink" = 9
    "lime" = 10
    "yellow" = 11
    "light_blue" = 12
    "magenta" = 13
    "orange" = 14
    "white" = 15
}

$colors = @{
    "black" = 1908001
    "red" = 11546150
    "green" = 6192150
    "brown" = 8606770
    "blue" = 3949738
    "purple" = 8991416
    "cyan" = 1481884
    "light_gray" = 10329495
    "gray" = 4673362
    "pink" = 15961002
    "lime" = 8439583
    "yellow" = 16701501
    "light_blue" = 3847130
    "magenta" = 13061821
    "orange" = 16351261
    "white" = 16383998
}

# Create a directory for the output files
$outputDir = "src\main\resources\assets\harkenscythe\recipes"
if (-Not (Test-Path $outputDir)) {
    New-Item -ItemType Directory -Path $outputDir
}

# Generate the JSON files
foreach ($armor in $armorPieces) {
    foreach ($dye in $dyes.GetEnumerator()) {
        $dyeName = $dye.Key
        $dyeData = $dye.Value
        $dyeColorValue = $colors[$dyeName]

        $jsonContent = @{
            type = "forge:ore_shapeless"
            ingredients = @(
                @{ item = "harkenscythe:$armor" },
                @{ item = "minecraft:dye"; data = $dyeData }
            )
            result = @{
                item = "harkenscythe:$armor"
                nbt = @{ display = @{ color = $dyeColorValue } }
            }
        } | ConvertTo-Json -Depth 10

        $fileName = "$outputDir/${armor}_$dyeName.json"
        $jsonContent | Set-Content -Path $fileName -Encoding UTF8
    }
}
