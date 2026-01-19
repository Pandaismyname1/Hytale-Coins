package com.pandaismyname1.coins.config;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {
    @Test
    public void testMobDeathDropsConfig() {
        ModConfig config = new ModConfig();
        
        // Check default values
        assertEquals(3L, config.getMobDeathDrops().get("Fox"));
        assertEquals(3L, config.getMobDeathDrops().get("Cow"));
        
        // Check custom values
        Map<String, Long> customDrops = new HashMap<>();
        customDrops.put("Zombie", 50L);
        config.setMobDeathDrops(customDrops);
        
        assertEquals(50L, config.getMobDeathDrops().get("Zombie"));
        assertNull(config.getMobDeathDrops().get("Fox"));
    }

    @Test
    public void testModelIdParsingLogic() {
        // Simulating the logic used in MobDeathListener
        String modelAssetId = "hytale:fox";
        String shortName = modelAssetId.contains(":") ? modelAssetId.substring(modelAssetId.indexOf(":") + 1) : modelAssetId;

        assertEquals("fox", shortName);

        modelAssetId = "fox";
        shortName = modelAssetId.contains(":") ? modelAssetId.substring(modelAssetId.indexOf(":") + 1) : modelAssetId;
        assertEquals("fox", shortName);
    }
}
