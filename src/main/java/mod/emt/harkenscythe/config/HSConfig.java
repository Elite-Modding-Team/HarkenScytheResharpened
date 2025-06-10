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
    
    @Config.LangKey("cfg.harkenscythe.enchantments")
    @Config.Name("Enchantments")
    public static final EnchantmentSettings ENCHANTMENTS = new EnchantmentSettings();

    @Config.LangKey("cfg.harkenscythe.entities")
    @Config.Name("Entities")
    public static final EntitySettings ENTITIES = new EntitySettings();

    @Config.LangKey("cfg.harkenscythe.items")
    @Config.Name("Items")
    public static final ItemSettings ITEMS = new ItemSettings();
    
    @Config.LangKey("cfg.harkenscythe.mod_integration")
    @Config.Name("Mod Integration")
    public static final ModIntegrationSettings MOD_INTEGRATION = new ModIntegrationSettings();

    @Config.LangKey("cfg.harkenscythe.recipes")
    @Config.Name("Recipes")
    public static final RecipeSettings RECIPES = new RecipeSettings();

    public static class GeneralSettings
    {
        @Config.Name("Debug Mode")
        @Config.Comment("Prints debug values to console")
        public boolean debugMode = false;

        @Config.Name("Disable All Enchantments")
        @Config.Comment("Disables all enchantments")
        public boolean disableEnchantments = false;
        
        @Config.Name("Disable Guidebook")
        @Config.Comment("Disables the guidebook that is provided when Patchouli is installed")
        public boolean disableGuidebook = false;

        @Config.Name("Disable All Potions")
        @Config.Comment("Disables all potions")
        public boolean disablePotions = false;

        @Config.Name("New Moon Strength Effect")
        @Config.Comment("Mobs that spawn during the New Moon will gain the Strength effect")
        public boolean newMoonStrengthEffect = true;
    }

    public static class BlockSettings
    {
        @Config.Name("Crucible Detection Range")
        @Config.Comment
            ({
                "The radius in blocks crucibles can be detected by altars",
                "Warning: Performance sensitive"
            })
        @Config.RangeInt(min = 1)
        public int crucibleDetectionRange = 4;

        @Config.Name("Crucible Max Amount")
        @Config.Comment("The maximum amount of essence to be stored in crucibles")
        @Config.RangeInt(min = 1)
        public int crucibleMaxAmount = 100;

        @Config.Name("Livingmetal Core Regeneration Level")
        @Config.Comment("The level of the regeneration effect provided by active livingmetal cores")
        @Config.RangeInt(min = 1)
        public int livingmetalCoreRegenLevel = 1;
    }
    
    public static class EnchantmentSettings
    {
        @Config.Name("Bloodletting Max Level")
        @Config.Comment("Max level that the Bloodletting enchantment can be")
        @Config.RangeInt(min = 1)
        public int bloodlettingMaxLevel = 3;
        
        @Config.Name("Bloodletting Chance Per Level")
        @Config.Comment("The default chance (multiplied by level) for the Bloodletting enchantment to spawn essence")
        public double bloodlettingChancePerLevel = 0.2D;
        
        @Config.Name("Reaping Frenzy Anvil Support")
        @Config.Comment("Using Abyssal Fragments with scythes or glaives at an anvil will apply Reaping Frenzy")
        public boolean reapingFrenzyAnvil = true;
        
        @Config.Name("Reaping Frenzy Anvil Cost")
        @Config.Comment("The cost in XP levels when applying Reaping Frenzy with an Abyssal Fragment")
        @Config.RangeInt(min = 1)
        public int reapingFrenzyAnvilCost = 15;
        
        @Config.Name("Reaping Frenzy Enchanting")
        @Config.Comment("Makes Reaping Frenzy available in all enchantment methods")
        public boolean reapingFrenzyEnchanting = false;
        
        @Config.Name("Soul Tether Max Level")
        @Config.Comment("Max level that the Soul Tether enchantment can be")
        @Config.RangeInt(min = 1)
        public int soulTetherMaxLevel = 3;
        
        @Config.Name("Soul Tether Chance Per Level")
        @Config.Comment("The default chance (multiplied by level) for the Soul Tether enchantment to spawn essence")
        public double soulTetherChancePerLevel = 0.25D;

        @Config.Name("Enable Blight Enchantment")
        @Config.Comment("Enables the Blight enchantment")
        public boolean enchantmentBlight = true;

        @Config.Name("Enable Bloodletting Enchantment")
        @Config.Comment("Enables the Bloodletting enchantment")
        public boolean enchantmentBloodletting = true;

        @Config.Name("Enable Exude Enchantment")
        @Config.Comment("Enables the Exude enchantment")
        public boolean enchantmentExude = true;

        @Config.Name("Enable Hemorrhage Enchantment")
        @Config.Comment("Enables the Hemorrhage enchantment")
        public boolean enchantmentHemorrhage = true;

        @Config.Name("Enable Nourishment Enchantment")
        @Config.Comment("Enables the Nourishment enchantment")
        public boolean enchantmentNourishment = true;

        @Config.Name("Enable Reaping Frenzy Enchantment")
        @Config.Comment("Enables the Reaping Frenzy enchantment")
        public boolean enchantmentReapingFrenzy = true;

        @Config.Name("Enable Soul Tether Enchantment")
        @Config.Comment("Enables the Soul Tether enchantment")
        public boolean enchantmentSoulTether = true;

        @Config.Name("Enable Willingness Enchantment")
        @Config.Comment("Enables the Willingness enchantment")
        public boolean enchantmentWillingness = true;
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
        @Config.RangeInt(min = 1)
        public int essenceDespawnTime = 6000;

        @Config.Name("Essence Max Health Limit")
        @Config.Comment("Mobs with this much max health or less will spawn no blood/soul essence (leaving it at 0 will not enable this feature)")
        public double essenceMaxHealthLimit = 0.0D;

        @Config.Name("Essence Mob Spawning")
        @Config.Comment("Spawns globins (or spectral miners from spectral souls) after a blood/soul essence entity despawns")
        public boolean essenceMobSpawning = true;
        
        @Config.Name("Essence Reaping Affected by Tool Damage")
        @Config.Comment("The chance for blood/soul essence to appear from reaping is affected by the tool damage (wood has a low chance while diamond has a high chance), otherwise blood/soul essence will always spawn from reaping instead")
        public boolean essenceDamageReaping = false;

        @Config.Name("Blood Essence Common Value")
        @Config.Comment("How much souls common soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceBloodCommonValue = 1;

        @Config.Name("Blood Essence Sickly Value")
        @Config.Comment("How much souls common soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceBloodSicklyValue = 2;

        @Config.Name("Blood Essence Sickly Max Health")
        @Config.Comment("Mobs with this much max health or more will spawn sickly blood essence")
        public double essenceBloodSicklyMaxHealth = 40.0D;

        @Config.Name("Blood Essence Intoxicated Value")
        @Config.Comment("How much souls common soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceBloodIntoxicatedValue = 5;

        @Config.Name("Blood Essence Intoxicated Max Health")
        @Config.Comment("Mobs with this much max health or more will spawn intoxicated blood essence")
        public double essenceBloodIntoxicatedMaxHealth = 100.0D;

        @Config.Name("Blood Essence Warped Value")
        @Config.Comment("How much souls common soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceBloodWarpedValue = 40;

        @Config.Name("Blood Essence Warped Max Health")
        @Config.Comment("Bosses with this much max health or more will spawn warped blood essence, otherwise intoxicated blood essence is spawned instead")
        public double essenceBloodWarpedMaxHealth = 300.0D;

        @Config.Name("Soul Essence Culled Max Health")
        @Config.Comment("Mobs with this much max health or more will spawn culled soul essence")
        public double essenceSoulCulledMaxHealth = 100.0D;

        @Config.Name("Soul Essence Common Value")
        @Config.Comment("How much souls common soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceSoulCommonValue = 1;

        @Config.Name("Soul Essence Culled Value")
        @Config.Comment("How much souls culled soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceSoulCulledValue = 5;

        @Config.Name("Soul Essence Grieving Max Health")
        @Config.Comment("Mobs with this much max health or more will spawn grieving soul essence")
        public double essenceSoulGrievingMaxHealth = 40.0D;

        @Config.Name("Soul Essence Grieving Value")
        @Config.Comment("How much souls grieving soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceSoulGrievingValue = 2;

        @Config.Name("Soul Essence Wrathful Max Health")
        @Config.Comment("Bosses with this much max health or more will spawn wrathful soul essence, otherwise culled soul essence is spawned instead")
        public double essenceSoulWrathfulMaxHealth = 300.0D;

        @Config.Name("Soul Essence Spectral Value")
        @Config.Comment("How much souls spectral soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceSoulSpectralValue = 20;

        @Config.Name("Soul Essence Wrathful Value")
        @Config.Comment("How much souls wrathful soul essence is worth")
        @Config.RangeInt(min = 1)
        public int essenceSoulWrathfulValue = 40;

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
        @Config.Name("Blood Butcherer Blood Cost")
        @Config.Comment
            ({
                "The amount of blood essence blood butcherers consume each hit",
                "Set to 0 to disable essence consumption"
            })
        public int bloodButchererBloodCost = 1;

        @Config.Name("Blood Butcherer Max Charges")
        @Config.Comment
            ({
                "The maximum amount of blood charges blood butcherers can take",
                "When changing this, you may want to modify the recipe data value accordingly"
            })
        @Config.RangeInt(min = 1)
        public int bloodButchererMaxCharges = 2000;

        @Config.Name("Basic Blood Keeper Essence Capacity")
        @Config.Comment("The amount of essence basic blood keepers can hold")
        @Config.RangeInt(min = 1)
        public int bloodKeeperBasicEssenceCapacity = 40;

        @Config.Name("Blood Keeper Essence Capacity")
        @Config.Comment("The amount of essence blood keepers can hold")
        @Config.RangeInt(min = 1)
        public int bloodKeeperEssenceCapacity = 80;

        @Config.Name("Blood Vessel Essence Capacity")
        @Config.Comment("The amount of essence blood vessels can hold")
        @Config.RangeInt(min = 1)
        public int bloodVesselEssenceCapacity = 160;

        @Config.Name("Blood Trinket Essence Capacity")
        @Config.Comment("The amount of essence blood trinkets can hold")
        @Config.RangeInt(min = 1)
        public int bloodTrinketEssenceCapacity = 400;

        @Config.Name("Ethereal Blood Trinket Essence Capacity")
        @Config.Comment("The amount of essence ethereal blood trinkets can hold")
        @Config.RangeInt(min = 1)
        public int bloodTrinketEtherealEssenceCapacity = 1600;

        @Config.Name("Deadtime Watch Durability")
        @Config.Comment
            ({
                "The amount of durability deadtime watches have",
                "When changing this, you may want to modify the recipe data value accordingly"
            })
        @Config.RangeInt(min = 1)
        public int deadtimeWatchDurability = 40;

        @Config.Name("Deadtime Watch Duration")
        @Config.Comment("The time stop duration of deadtime watches in ticks")
        @Config.RangeInt(min = 1)
        public int deadtimeWatchDuration = 300;

        @Config.Name("Deadtime Watch Radius")
        @Config.Comment("The AoE radius of deadtime watches")
        public double deadtimeWatchRadius = 16.0D;

        @Config.Name("Deadtime Watch Uses")
        @Config.Comment("The amount of uses fully charged deadtime watches have")
        @Config.RangeInt(min = 1)
        public int deadtimeWatchUses = 20;

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
        @Config.RangeInt(min = 1)
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
        @Config.RangeInt(min = 1)
        public int dimensionalMirrorUses = 4;

        @Config.Name("Necronomicon Summon Blood Cost")
        @Config.Comment("The amount of blood essence the Necronomicon requires to summon spectrals")
        public int necronomiconSummonBloodCost = 10;

        @Config.Name("Reaper's Guidebook Provision")
        @Config.Comment("Provides the player with the Reaper's Guidebook on first spawn")
        public boolean reaperGuidebookProvision = true;

        @Config.Name("Basic Soul Keeper Essence Capacity")
        @Config.Comment("The amount of essence basic soul keepers can hold")
        @Config.RangeInt(min = 1)
        public int soulKeeperBasicEssenceCapacity = 40;

        @Config.Name("Soul Keeper Essence Capacity")
        @Config.Comment("The amount of essence soul keepers can hold")
        @Config.RangeInt(min = 1)
        public int soulKeeperEssenceCapacity = 80;

        @Config.Name("Soul Vessel Essence Capacity")
        @Config.Comment("The amount of essence soul vessels can hold")
        @Config.RangeInt(min = 1)
        public int soulVesselEssenceCapacity = 160;

        @Config.Name("Soul Trinket Essence Capacity")
        @Config.Comment("The amount of essence soul trinkets can hold")
        @Config.RangeInt(min = 1)
        public int soulTrinketEssenceCapacity = 400;

        @Config.Name("Ethereal Soul Trinket Essence Capacity")
        @Config.Comment("The amount of essence ethereal soul trinkets can hold")
        @Config.RangeInt(min = 1)
        public int soulTrinketEtherealEssenceCapacity = 1600;

        @Config.Name("Tome of Refreshing Durability")
        @Config.Comment
            ({
                "The amount of durability a Tome of Refreshing has",
                "When changing this, you may want to modify the recipe data value accordingly"
            })
        @Config.RangeInt(min = 1)
        public int refreshTomeDurability = 20;

        @Config.Name("Tome of Refreshing Uses")
        @Config.Comment("The amount of uses a fully charged Tome of Refreshing has")
        @Config.RangeInt(min = 1)
        public int refreshTomeUses = 20;

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

        @Config.Name("Vampire Knife Despawn Time")
        @Config.Comment("The amount of ticks after a vampire knife entity despawns")
        @Config.RangeInt(min = 1)
        public int vampireKnifeDespawnTime = 40;

        @Config.Name("Vampire Knife Drop Chance")
        @Config.Comment("The chance for vampire knives to drop under the right circumstances in percentage")
        public double vampireKnifeDropChance = 0.001D;

        @Config.Name("Vampire Knife Max Charges")
        @Config.Comment
            ({
                "The maximum amount of blood charges vampire knifes can take",
                "When changing this, you may want to modify the recipe data value accordingly"
            })
        @Config.RangeInt(min = 1)
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

        @Config.Name("Hoes Till Creep Blocks")
        @Config.Comment("Hoes are able to till creep blocks like glaives")
        public boolean hoesTillCreepBlocks = false;
    }
    
    public static class ModIntegrationSettings
    {
        @Config.Name("Blood Magic Integration")
        @Config.Comment("Enables Blood Magic integration")
        public boolean bloodMagicIntegration = true;
        
        @Config.Name("Construct's Armory Integration")
        @Config.Comment("Enables Construct's Armory integration (requires Tinkers' Construct integration to be enabled!)")
        public boolean constructsArmoryIntegration = true;
        
        @Config.Name("Just Enough Resources Integration")
        @Config.Comment("Enables Just Enough Resources integration")
        public boolean jerIntegration = true;
        
        @Config.Name("Tinkers' Construct Integration")
        @Config.Comment("Enables Tinkers' Construct integration")
        public boolean tinkersConstructIntegration = true;
        
        @Config.Name("Tinkers' Construct Integration: Blood Conjuration Max Level")
        @Config.Comment("Max level that the Blood Conjuration trait can be")
        @Config.RangeInt(min = 1)
        public int bloodConjurationTraitMaxLevel = 3;
        
        @Config.Name("Tinkers' Construct Integration: Blood Conjuration Chance Per Level")
        @Config.Comment("The default chance (multiplied by level) for the Blood Conjuration trait to spawn essence")
        public double bloodConjurationChancePerLevel = 0.2D;
        
        @Config.Name("Tinkers' Construct Integration: Soul Conjuration Max Level")
        @Config.Comment("Max level that the Soul Conjuration trait can be")
        @Config.RangeInt(min = 1)
        public int soulConjurationTraitMaxLevel = 3;
        
        @Config.Name("Tinkers' Construct Integration: Soul Conjuration Chance Per Level")
        @Config.Comment("The default chance (multiplied by level) for the Soul Conjuration trait to spawn essence")
        public double soulConjurationChancePerLevel = 0.25D;
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
