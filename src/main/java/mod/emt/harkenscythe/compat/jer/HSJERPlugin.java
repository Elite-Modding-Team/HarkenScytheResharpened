package mod.emt.harkenscythe.compat.jer;

import jeresources.api.IJERAPI;
import jeresources.api.IMobRegistry;
import jeresources.api.conditionals.LightLevel;
import jeresources.compatibility.JERAPI;
import mod.emt.harkenscythe.entity.*;
import mod.emt.harkenscythe.init.HSLootTables;
import net.minecraft.world.World;

public class HSJERPlugin {
    public static void init() {
        IJERAPI jerApi = JERAPI.getInstance();
        IMobRegistry jerMobRegistry = jerApi.getMobRegistry();
        World jerWorld = jerApi.getWorld();
        
        jerMobRegistry.register(new HSEntityEctoglobin(jerWorld), HSLootTables.ECTOGLOBIN);
        jerMobRegistry.register(new HSEntityExospider(jerWorld), LightLevel.hostile, 20, HSLootTables.EXOSPIDER);
        jerMobRegistry.register(new HSEntityHarbinger(jerWorld), LightLevel.hostile, 50, HSLootTables.HARBINGER);
        jerMobRegistry.register(new HSEntityHemoglobin(jerWorld), HSLootTables.HEMOGLOBIN);
        jerMobRegistry.register(new HSEntitySpectralHuman(jerWorld), LightLevel.hostile, 20, HSLootTables.SPECTRAL_HUMAN);
        jerMobRegistry.register(new HSEntitySpectralMiner(jerWorld), LightLevel.hostile, 40, HSLootTables.SPECTRAL_MINER);
    }
}
