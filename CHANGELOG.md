# Harken Scythe: Resharpened Changelog
## 2.0.0
### Update Notes
Upcoming overhaul of the entire mod.
### Added
-
### Changed
- Harken Athame is now crafted without requiring essence
- Both altars now only require a regular book to craft
- Slightly tweaked texture of both altars
- Moved advancements around a little
- Buffed Nourishment and Exeude trigger chances
- Blood Butcherer will now properly work with knockback enchantments
- Tweaked glaive and scythe swing sounds
### Fixed
- Fixed ore dictionary registration
- Fixed how enchantments are registered
### Removed
- Removed Blunt Harken Blade
---
## 1.5.1
### Added
- Added Tome of Refreshing, it allows you to reroll the enchantment table or disenchant tools!
### Changed
- Reaper's Guidebook is now always registered first and shows up first on the creative mode tab and JEI
- Tweaked some guidebook pages to be more helpful and informational
- Made advancement requirements much easier on early chapters
- Harken Magician's Trek advancment now only requires an athame to be obtained
- Moved Attributes Go Up! and Enchant Your Day pages into the Getting Started category
- Buffed alteration and parasitica rings
- Crafting altar books no longer require filled keepers
- Normal trinkets can now magnetize essence like ethereal trinkets
- Greatly buffed ethereal trinket storage capacity
- Added MmmMmmMmmMmm to the reaping blacklist by default
- Updated German translation
### Fixed
- Finally fixed Thaumcraft entity aspects not properly registering
- Fixed softlocks in guidebook progression
---
## 1.5.0
### Update Notes
NOTE: Baubles is now a REQUIRED DEPENDENCY as of this version. New attributes are also introduced, these are compatible with Set Bonus, Quality Tools, and other mods that utilize attributes!
### Added
- Added unique sounds when equipping all armor sets (courtesy of PessiMysterio)
- Added unique sound for Reaping Frenzy
- Added Just Enough Resources support
- Added a config option to disable the guidebook completely
- Added sound events for most unused sounds
- Added shapeless recipes for converting spectral glass states
- Added the Lifesteal attribute, damage you deal to mobs will always heal by the percentage of it (e.g. if you have 7.5% lifesteal then you'll always heal 7.5% of damage)
- Added the Essence Alteration attribute, it affects the chance for essences to evolve into the next tier (upwards to a value of 5). With an Essence Alteration of 20%, you'll have a 20% chance for the essence to become its next tier for example
- Added config options to adjust essence enchantments
- Added lore text for all Harken Scythe mobs in the guidebook
- Added Tome of Refreshing, it has the ability to disenchant items and reroll the enchantment table!
- Added Reaper's Ring of Silence, wearing it will prohibit essence from being spawned from reaping
- Added Reaper's Ring of Parasitica, wearing it will give +3.5% Lifesteal and -5% Attack Damage
- Added Reaper's Ring of Alteration, wearing it will give +3.5% Essence Alteration
- Added Reaper's Ring of Role Reversal, wearing it will reverse the types of essence given from reaping (e.g. scythes give blood instead of souls)
### Changed
- Crucibles now require a basic essence keeper to craft instead of a Soumberglass Ingot
- Shortened enchantment descriptions
- Buffed regeneration from Cake of Souls and Cookie of Souls
- Slightly nerfed Blight enchantment
- Scythes and glaives are now affected by reach distance
- Fixed several items like containers being repairable by anvils when they shouldn't be
- Updated guidebook sounds (courtesy of PessiMysterio)
- Essences will now always spawn from reaping by default (old system affected by tool durability is a config option instead)
- Slightly sped up Harbinger sounds
- Gave scythes and glaives more distinct names
- Renamed Soulsteal enchantment to Soul Tether
- Flame potions now destroy cobwebs
- Globins no longer drop essence from enchantments (or traits)
- All mod integration is now configurable
- Reaping Frenzy can now be obtained when combining an Abyssal Fragment with a glaive or scythe at an anvil
- Blood/Soul Conjuration traits are now leveled with new config options to control the levels (Tinkers' Construct Integration)
- Intervention traits are now leveled (Tinkers' Construct/Construct's Armory Integration)
- All enchantments can now be disabled individually in the config
- Necronomicon summons should now work better
- Cake of Souls now regenerate missing slices
- Essences and globins are now immune to fire
- Harbingers and Spectral Miners will now regenerate health quickly when no longer having a target, this prevents easy cheesing methods
### Fixed
- Fixed Exospider sounds not playing due to a typo
- Fixed Spectral Miner loot table
---
## 1.4.1
### Added
- Added Hoes Till Creep Blocks opt-in config setting
### Changed
- Refactored creepball usage
- Streamlined glaive creep block tilling
---
## 1.4.0
### Update Notes
Thanks to invaluable feedback from several players, this update is fully focused on rebalancing the early game progression and making the mod overall less painful to get into.
### Added
- Added Soumberglass and Soul Soot, new materials used for various recipes throughout the mod
- Added basic keepers, these are made with pure Soumberglass and don't require ender pearls
- Added obsidian and extra mineral drops to Spectral miners
- Spectral Miners now have a rare chance to drop a diamond
- Added the Willingness enchantment with four levels, this allows glaives and scythes to reap faster than normal while also providing a chance to not consume durability on reap
- Added the Reaping Frenzy enchantment, this allows glaives and scythes automatically reap as long as right-click is still held
- Added a new page to the guidebook that shows information about Soumberglass
- Implemented HWYLA integration for Biomass crops
### Changed
- Updated sounds (courtesy of PessiMysterio)
	- Essence spawning, collecting, and despawning
	- Athame creation
	- Harbingers
	- Altar Crafting
- Deadtime Watch now applies the Slowness effect on nearby players
- Increased charging costs for durable weapons
- Increased the drop chance for a Damaged Vampire Knife in the Nether (1/2000 -> 1/1000)
- Ripe Biomass crops will now always drop germinated seeds
- Various altar recipes are now cheaper with most costs being halved
- Altars, absorbers, and crucibles no longer require ender pearls and now require Soumberglass
- Made recipes for all essence containers cheaper
- Essence containers will now carry the same amount of soul essence as blood
- Increased the maximum amount of essence carried for keepers and vessels
- Buffed Biomass gear and greatly improved the repairing mechanic
- Harbingers now always drop ender pearls and abyssal fragments
- Harbingers now drop Soumberglass instead of bones
- Increased the chance for the Bloodletting (10% -> 20%) and Soulsteal (15% -> 25%) enchantments to drop essences
- Essence max health limit is now disabled by default, silverfish and small slimes will drop essence again!
- The guidebook now shows more information about essence containers
- Updated Book of Shadows and Book of Carnage recipes to require only basic keepers
- All glaives and scythes now require at least one Soumberglass to craft
- Increased the chance for the Blood Conjuration (10% -> 20%) and Soul Conjuration (20% -> 25%) traits to drop essences
### Fixed
- Fixed glaives and scythes not swinging properly while using the ability on the offhand
- Fixed glaives and scythes being interrupted while being repaired like from Mending
- Fixed the animated texture on soul altars
- Fixed small globins doing no damage
---
## 1.3.2
### Changed
- Unbreaking can no longer be applied with anvils to all Harken Scythe: Resharpened items that already don't accept it in enchantment tables
- Athame tools no longer accept Mending and Unbreaking enchantments because they do not lose durability or break
### Fixed
- Fixed a crash with trinkets that can lock players out of their worlds permanently
---
## 1.3.1
### Added
- Added ore dictionary support to all variants of spectral glass and spectral glass panes
- Added sweep particles to Blood Butcherer
### Changed
- All recipes that require spectral glass or spectral glass panes now accept both variants
- Blood Butcherer recipe now accepts a Livingmetal Sword of any durability in its recipe
- Deadtime Watch can now also be crafted with wither skulls
- Harbingers can no longer be harmed by spectral mobs
- Improved Blood Butcherer hit checks
- Updated Vampire Knife projectile sounds (courtesy of PessiMysterio)
- Updated glaive and scythe sounds (courtesy of PessiMysterio)
### Fixed
- Fixed enchantment description formatting
- Fixed soul trinkets having blood altar recipes
---
## 1.3.0
### Update Notes
The incredible sounds for the absorbers, altars, and blood butcherer were brought to you by PessiMysterio. You can also see their work on Thaumic Augmentation, Embers, Sonomagy, and Malum!
### Added
- Altars now play a sound when approached
- Altars now play a loop sound while active around players
- Added Deadtime Watch, entities around you including objects like arrows will freeze for a few seconds whenever it is used. It costs blood essence per use.
- Added basic absorbers, these allow quick transport of essence containers into crucibles. No more tedious spam clicking on crucibles!
- Added a lot of new config options for essences
- Add config option for Vampire Knife entity despawn time
- Added new tiers for blood essence (read A Bottle of Fun in the guidebook for more info about essence tiers!)
- Added XP for when essences are harvested
- Added essence trinkets, these can be used to store a ton of essence within and can even be upgraded using Nether Stars! Essence can only be transported into crucibles with the new absorbers.
- Added Baubles support, so far the essence trinkets can be equipped on any slot.
- Added the Blood Butcherer, it can sweep through large groups of mobs and will apply the Bleeding effect. The amplifier of the effect will increase upwards to V, the more the same mob is hit.
### Changed
- Updated Thaumcraft integration and added aspects to items that lacked them
- New Moons will now apply Strength to all mobs that spawn
- Overhauled essence making all tiers now obtainable (read A Bottle of Fun in the guidebook for more info about essence tiers!)
- Sickly blood essence can now spawn during New Moons
- There is now a 10% chance (5% chance for bosses) for the Soul Reaper to instantly kill the target while it reaps
- Improved athame creation
- Fully revamped altar sounds (thanks PessiMysterio!)
- Tweaked Vampire Knife projectile sounds
- Tweaked the model and texture of both altars
- Altars can now be rotated
- Improved crucible logic (position caching and essence count syncing)
- Updated grieving soul essence texture because it looked almost identical to common souls
- Essence will no longer spawn if the afflicted mob has 7 or less health
- Different tiers of essence will now spawn depending on how much health the mob has
- New Moons spawn grieving soul essence rather than culled
- Globins will now increase in size depending on essence tier
- Spectral Miners now inflict Mining Fatigue
- Spectral Miners now has a chance to spawn Ectoglobins when hurt
- Harbingers teleport away from projectiles like Endermen
- Harbingers now deal x1.5 the amount of damage on non-players and double the amount of damage on animals
- Made Harbingers neutral, in the future they will target you when you hold onto important Harken Scythe items.
- Made Spectral Humans neutral
- Harbingers now have a glow layer
- Harbingers will now gain regeneration upon reaping mobs
- Increased ender pearl drops from Harbingers
- The Vampire Knife now requires an Abyssal Fragment to craft
- Buffed biomass cultivation
- Lowered the amount of blood needed to bloody a creep block from 10 to 5
- Buffed spectral entity minions
- Soul essence tiers now affect spectral entities
- Bloodweave and Soulweave sets no longer accept Mending
- Most Harken Scythe items no longer accept Unbreaking
- Bloodweave armor, Soulweave armor, and the Vampire Knife can no longer have durability restored by XP repair
- Updated Bloodweave armor overlay
- Tweaked livingmetal armor model texture
- Updated biomass axe texture
- The Bleeding effect now spawns particles
- Renamed the Vengeful Revenant advancement to Deathtouched
- Harbingers and Spectral Miners now drop grieving soul essence rather than common
- Reduced the amount of essence dropped from Spectral Miners
- Added a pickaxe to the rotating Spectral Miner model in the guidebook
- Updated filler page texture on the guidebook (thanks to Luke.RUSTLTD for the texture)
- The mob pages in the guidebook no longer require the respective mobs to be killed first. The advancements still remain.
### Fixed
- Fixed altars duplicating items in certain circumstances
- Fixed altars playing sounds when the tile entity is removed
- Fixed a rare crash with altar rendering
- Fixed dimensional mirror cooldown application
- Fixed Harbinger targeting mechanics
- Fixed a crash with the vampire knife
- Fixed AI with spectral minions
- Fixed blood essence dropping on every entity death
- Fixed Harbinger spawning conditions
- Fixed vampire knife model json
- Fixed particle handler calls
- Fixed the drop borders position in the guidebook and extended the amount of drops shown
- Fixed entity damage being incorrect in the guidebook
- Fixed Vampire Knife projectiles being affected by liquid
---
## 1.2.1
### Added
- Added abyssal fragments (will have future uses)
### Changed
- Exempted baby mobs from granting essence drops
- Improved biomass tool textures
- Improved registration for blocks and items
- Made Exospiders immune to fire
### Fixed
- Fixed glitch when collecting despawning essence
- Fixed guidebook icon
- Fixed vampire knife recipe causing modpack edge case issues
### Removed
- Removed functionless biomass crop item block
---
## 1.2.0
### Added
- Added the Vampire Knife, a powerful item costing blood essence, obtained as an extremely rare drop from Nether mobs (if you played Terraria a lot, then you will know exactly what this is inspired by)
- Added the Exospider, a new spider variant habitated in the Nether and controlled by essences with a weakness for biomass
- Added new advancements
- Added various new config options
- Added a few new blood altar ritual recipes for converting vanilla items
- Added guidebook entries for enchantments and the Vampire Knife
### Changed
- Tweaked a few guidebook pages to fix typos and formatting
- Updated durability bar color of rechargeable items depending on essence type required
- Stereo sounds are converted to mono to improve directional audio
### Fixed
- Fixed CraftTweaker recipe addition/removal loading order for good
---
## 1.1.2
### Added
- Added an additional Blood Altar recipe, you can now infuse a Golden Apple into an Enchanted Golden Apple (Notch Apple)
- Added a cooldown for athames
---
## 1.1.1
### Changed
- Adjusted random pitching of crucible sounds to make them less annoying
---
## 1.1.0
### Added
- **Blood Magic Integration:** Blood containers like keepers and vessels can be used similarly to the sacrificial dagger to fill a blood altar with LP
- **CraftTweaker Integration:** Altar ritual recipes can be added and removed
- **GroovyScript Integration:** Altar ritual recipes can be added and removed
- **Enchantment Descriptions Integration:** All enchantments now have proper descriptions on their effects
- **Just Enough Items Integration:** All altar ritual recipes can be viewed when pressing the usage key (default: U) on altar blocks within JEI
- Added invalid altar ritual recipe status messages
- Added essence spawning animations + interpolation
### Changed
- Adjusted essence container durability bar colors
- Improved some guidebook phrasing
### Fixed
- Fixed voiding of essence with foreign containers
---