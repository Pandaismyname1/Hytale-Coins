package com.pandaismyname1.coins.economy;

import com.pandaismyname1.coins.config.ConfigManager;
import com.pandaismyname1.coins.economy.storage.JsonStorageStrategy;
import com.pandaismyname1.coins.economy.storage.MySqlStorageStrategy;
import com.pandaismyname1.coins.economy.storage.StorageStrategy;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class WalletManager {
    private static final Logger LOGGER = Logger.getLogger(WalletManager.class.getName());
    private static final ConcurrentHashMap<UUID, Wallet> wallets = new ConcurrentHashMap<>();
    private static StorageStrategy storage;

    public static void init() {
        String type = ConfigManager.getConfig().getStorageType();
        if ("MYSQL".equalsIgnoreCase(type)) {
            storage = new MySqlStorageStrategy(ConfigManager.getConfig());
            LOGGER.info("Using MySQL storage for wallets.");
        } else {
            storage = new JsonStorageStrategy();
            LOGGER.info("Using JSON storage for wallets.");
        }
    }

    public static Wallet getWallet(UUID playerUuid) {
        if (storage == null) {
            init();
        }
        return wallets.computeIfAbsent(playerUuid, storage::loadWallet);
    }

    public static void saveWallet(UUID playerUuid) {
        Wallet wallet = wallets.get(playerUuid);
        if (wallet == null || storage == null) return;
        storage.saveWallet(wallet);
    }

    public static void saveAll() {
        if (storage == null) return;
        storage.saveAll(wallets.values());
    }

    public static void shutdown() {
        saveAll();
        if (storage != null) {
            storage.shutdown();
        }
    }
}
