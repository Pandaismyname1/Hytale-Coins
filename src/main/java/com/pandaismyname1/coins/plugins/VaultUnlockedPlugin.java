package com.pandaismyname1.coins.plugins;

import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.common.semver.SemverRange;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.HytaleServer;
import net.cfh.vault.VaultUnlockedServicesManager;

public class VaultUnlockedPlugin {
    public static void setup(HytaleLogger logger) {
        if (HytaleServer.get().getPluginManager().hasPlugin(
                PluginIdentifier.fromString("TheNewEconomy:VaultUnlocked"),
                SemverRange.WILDCARD
        )) {
            logger.atInfo().log("VaultUnlocked is installed, enabling VaultUnlocked support.");

            VaultUnlockedServicesManager.get().economy(new VaultUnlockedEconomy());
        } else {
            logger.atInfo().log("VaultUnlocked is not installed, disabling VaultUnlocked support.");
        }
    }
}
