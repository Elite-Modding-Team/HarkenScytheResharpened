package mod.emt.harkenscythe.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.emt.harkenscythe.HarkenScythe;
import mod.emt.harkenscythe.util.HSDimensionBlacklist;
import mod.emt.harkenscythe.util.HSEntityBlacklists;

@Config(modid = HarkenScythe.MOD_ID, name = "HarkenScythe")
public class HSConfig
{
    @Config.LangKey("cfg.harkenscythe.general")
    @Config.Name("General")
    public static final GeneralSettings GENERAL = new GeneralSettings();

    @Config.LangKey("cfg.harkenscythe.blocks")
    @Config.Name("Blocks")
    public static final BlockSettings BLOCKS = new BlockSettings();

    @Config.LangKey("cfg.harkenscythe.entities")
    @Config.Name("Entities")
    public static final EntitySettings ENTITIES = new EntitySettings();

    @Config.LangKey("cfg.harkenscythe.items")
    @Config.Name("Items")
    public static final ItemSettings ITEMS = new ItemSettings();

    @Config.LangKey("cfg.harkenscythe.recipes")
    @Config.Name("Recipes")
    public static final RecipeSettings RECIPES = new RecipeSettings();

    public static class GeneralSettings
    {
        @Config.Name("Debug Mode")
        @Config.Comment("Prints debug values to console")
        public boolean debugMode = false;

        @Config.Name("Disable Enchantments")
        @Config.Comment("Disables all enchantments")
        public boolean disableEnchantments = false;

        @Config.Name("Disable Potions")
        @Config.Comment("Disables all potions")
        public boolean disablePotions = false;
    }

    public static class BlockSettings
    {
        @Config.Name("Crucible Detection Range")
        @Config.Comment
            ({
                "The radius in blocks crucibles can be detected by altars",
                "Warning: Performance sensitive"
            })
        public int crucibleDetectionRange = 4;

        @Config.Name("Crucible Max Amount")
        @Config.Comment("The maximum amount of essence to be stored in crucibles")
        public int crucibleMaxAmount = 100;

        @Config.Name("Livingmetal Core Regeneration Level")
        @Config.Comment("The level of the regeneration effect provided by active livingmetal cores")
        @Config.RangeInt(min = 1)
        public int livingmetalCoreRegenLevel = 1;
    }

    public static class EntitySettings
    {
        @Config.Name("Blood Reaping Blacklist")
        @Config.Comment
            ({
                "Entities excluded from dropping blood essence",
                "Syntax:  modid:entity",
                "Example: minecraft:cow"
            })
        public String[] bloodReapingBlacklist = new String[] {};

        @Config.Name("Essence Despawn Time")
        @Config.Comment("The amount of ticks after a blood/soul essence entity despawns")
        public int essenceDespawnTime = 6000;

        @Config.Name("Essence Mob Spawning")
        @Config.Comment("Spawns globins (or spectral miners from spectral souls) after a blood/soul essence entity despawns")
        public boolean essenceMobSpawning = true;

        @Config.RequiresMcRestart
        @Config.Name("Exospider Spawn Probability")
        @Config.Comment
            ({
                "The weighted probability for exospiders to spawn",
                "Set to 0 to disable"
            })
        public int exospiderSpawnProbability = 10;

        @Config.Name("Globin Crucible Spawning")
        @Config.Comment("Spawns globins when filled crucibles are broken")
        public boolean globinCrucibleSpawning = true;

        @Config.Name("Harbinger Armor Value")
        @Config.Comment("The amount of armor protection the Harbinger has")
        public double harbingerArmorValue = 10.0D;

        @Config.Name("Harbinger Attack Damage")
        @Config.Comment("The amount of damage dealt by the Harbinger")
        public double harbingerAttackDamage = 4.0D;

        @Config.Name("Harbinger Follow Range")
        @Config.Comment("The amount of blocks the Harbinger follows entities")
        public double harbingerFollowRange = 40.0D;

        @Config.Name("Harbinger Max Health")
        @Config.Comment("The amount of maximum health the Harbinger has")
        public double harbingerMaxHealth = 200.0D;

        @Config.Name("Harbinger Movement Speed")
        @Config.Comment("The amount of movement speed the Harbinger has")
        public double harbingerMovementSpeed = 0.3D;

        @Config.Name("Harbinger Reaping Blacklist")
        @Config.Comment
            ({
                "Entities excluded from being reapable by the Harbinger",
                "Syntax:  modid:entity",
                "Example: minecraft:cow"
            })
        public String[] harbingerReapingBlacklist = new String[] {};

        @Config.Name("Harbinger Search Distance")
        @Config.Comment("The distance in blocks the Harbinger will search for entities to reap")
        public double harbingerSearchDistance = 20.0D;

        @Config.RequiresMcRestart
        @Config.Name("Harbinger Spawn Probability")
        @Config.Comment
            ({
                "The weighted probability for the Harbinger to spawn",
                "Set to 0 to disable"
            })
        public int harbingerSpawnProbability = 5;

        @Config.Name("Soul Reaping Blacklist")
        @Config.Comment
            ({
                "Entities excluded from dropping soul essence",
                "Syntax:  modid:entity",
                "Example: minecraft:cow"
            })
        public String[] soulReapingBlacklist = new String[] {};

        @Config.Name("Spectral Entity Attack Damage")
        @Config.Comment("The amount of damage dealt by spectral entities, summoned by the Harbinger")
        public double spectralEntityAttackDamage = 6.0D;

        @Config.Name("Spectral Entity Blacklist")
        @Config.Comment
            ({
                "Entities excluded from being summoned as spectrals",
                "Syntax:  modid:entity",
                "Example: minecraft:cow"
            })
        public String[] spectralEntityBlacklist = new String[] {};

        @Config.Name("Spectral Human Attack Damage")
        @Config.Comment("The amount of damage dealt by spectral humans")
        public double spectralHumanAttackDamage = 4.0D;

        @Config.Name("Spectral Human Max Health")
        @Config.Comment("The amount of maximum health spectral humans have")
        public double spectralHumanMaxHealth = 40.0D;

        @Config.Name("Spectral Human Movement Speed")
        @Config.Comment("The amount of movement speed spectral humans have")
        public double spectralHumanMovementSpeed = 0.23D;

        @Config.RequiresMcRestart
        @Config.Name("Spectral Human Spawn Probability")
        @Config.Comment
            ({
                "The weighted probability for spectral humans to spawn",
                "Set to 0 to disable"
            })
        public int spectralHumanSpawnProbability = 5;

        @Config.Name("Spectral Miner Attack Damage")
        @Config.Comment("The amount of damage dealt by spectral miners")
        public double spectralMinerAttackDamage = 5.0D;

        @Config.Name("Spectral Miner Max Health")
        @Config.Comment("The amount of maximum health spectral miners have")
        public double spectralMinerMaxHealth = 150.0D;

        @Config.Name("Spectral Miner Movement Speed")
        @Config.Comment("The amount of movement speed spectral miners have")
        public double spectralMinerMovementSpeed = 0.15D;

        @Config.RequiresMcRestart
        @Config.Name("Spectral Miner Spawn Probability")
        @Config.Comment
            ({
                "The weighted probability for spectral miners to spawn",
                "Set to 0 to disable"
            })
        public int spectralMinerSpawnProbability = 2;
    }

    public static class ItemSettings
    {
        @Config.Name("Blood Keeper Essence Capacity")
        @Config.Comment("The amount of essence blood keepers can hold")
        public int bloodKeeperEssenceCapacity = 40;

        @Config.Name("Blood Vessel Essence Capacity")
        @Config.Comment("The amount of essence blood vessels can hold")
        public int bloodVesselEssenceCapacity = 80;

        @Config.Name("Dimensional Mirror Dimension Blacklist")
        @Config.Comment
            ({
                "The numeric dimension ID dimensional mirrors are not allowed to be used in",
                "Example: 1 = The End",
                "Defaults: 426 = Dungeon of Arcana, 427 = Vethea, 801 = Ancient Cavern, 812 = Immortallis"
            })
        public int[] dimensionalMirrorDimensionBlacklist = new int[] {426, 427, 801, 812};

        @Config.Name("Dimensional Mirror Durability")
        @Config.Comment
            ({
                "The amount of durability dimensional mirrors have",
                "When changing this, you may want to modify the recipe data value accordingly"
            })
        public int dimensionalMirrorDurability = 20;

        @Config.Name("Dimensional Mirror Home Dimension")
        @Config.Comment
            ({
                "The numeric dimension ID dimensional mirrors teleport players to",
                "Example: 0 = Overworld"
            })
        public int dimensionalMirrorHomeDimension = 0;

        @Config.Name("Dimensional Mirror Uses")
        @Config.Comment("The amount of uses fully charged dimensional mirrors have")
        public int dimensionalMirrorUses = 4;

        @Config.Name("Necronomicon Summon Blood Cost")
        @Config.Comment("The amount of blood essence the Necronomicon requires to summon spectrals")
        public int necronomiconSummonBloodCost = 10;

        @Config.Name("Reaper's Guidebook Provision")
        @Config.Comment("Provides the player with the Reaper's Guidebook on first spawn")
        public boolean reaperGuidebookProvision = true;

        @Config.Name("Soul Keeper Essence Capacity")
        @Config.Comment("The amount of essence soul keepers can hold")
        public int soulKeeperEssenceCapacity = 20;

        @Config.Name("Soul Vessel Essence Capacity")
        @Config.Comment("The amount of essence soul vessels can hold")
        public int soulVesselEssenceCapacity = 40;

        @Config.Name("Vampire Knife Blood Cost")
        @Config.Comment
            ({
                "The amount of blood essence firing vampire knife projectiles consumes",
                "Set to 0 to disable essence consumption"
            })
        public int vampireKnifeBloodCost = 1;

        @Config.Name("Vampire Knife Cooldown")
        @Config.Comment("The cooldown of firing vampire knife projectiles in ticks")
        public int vampireKnifeCooldown = 5;

        @Config.Name("Vampire Knife Drop Chance")
        @Config.Comment("The chance for vampire knives to drop under the right circumstances in percent")
        public double vampireKnifeDropChance = 0.0005D;

        @Config.Name("Vampire Knife Max Charges")
        @Config.Comment
            ({
                "The maximum amount of blood charges vampire knifes can take",
                "When changing this, you may want to modify the recipe data value accordingly"
            })
        public int vampireKnifeMaxCharges = 2000;

        @Config.Name("Vampire Knife Projectile Damage")
        @Config.Comment("The amount of damage vampire knife projectiles deal")
        public double vampireKnifeProjectileDamage = 10.0D;

        @Config.Name("Vampire Knife Projectile Healing")
        @Config.Comment("The amount of health vampire knife projectiles heal in percent")
        public double vampireKnifeProjectileHealing = 0.075D;

        @Config.Name("Vampire Knife Projectile Ignore I-Frames")
        @Config.Comment("Lets targets of vampire knife projectiles receive damage continuously without brief invulnerability phases")
        public boolean vampireKnifeProjectileIgnoreIFrames = true;
    }

    public static class RecipeSettings
    {
        @Config.Name("Custom Blood Altar Recipes")
        @Config.Comment
            ({
                "Defines custom recipes for blood altars by input, output and essence cost",
                "Syntax: modid:input;modid:output;cost",
                "For removal and advanced addition of recipes, please refer to the CraftTweaker and GroovyScript mod integrations"
            })
        public String[] customBloodAltarRecipes = new String[] {};

        @Config.Name("Custom Soul Altar Recipes")
        @Config.Comment
            ({
                "Defines custom recipes for soul altars by input, output and essence cost",
                "Syntax: modid:input;modid:output;cost",
                "For removal and advanced addition of recipes, please refer to the CraftTweaker and GroovyScript mod integrations"
            })
        public String[] customSoulAltarRecipes = new String[] {};
    }

    @Mod.EventBusSubscriber(modid = HarkenScythe.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(HarkenScythe.MOD_ID))
            {
                ConfigManager.sync(HarkenScythe.MOD_ID, Config.Type.INSTANCE);
                HSDimensionBlacklist.initBlacklistedDimensions();
                HSEntityBlacklists.initBlacklistedEntityEntries();
            }
        }
    }
}
