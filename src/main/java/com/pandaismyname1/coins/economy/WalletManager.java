package com.pandaismyname1.coins.economy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletManager {
    private static final Logger LOGGER = Logger.getLogger(WalletManager.class.getName());
    private static final ConcurrentHashMap<UUID, Wallet> wallets = new ConcurrentHashMap<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path STORAGE_DIR = Paths.get("universe", "mods", "coins", "players");

    public static Wallet getWallet(UUID playerUuid) {
        return wallets.computeIfAbsent(playerUuid, WalletManager::loadWallet);
    }

    private static Wallet loadWallet(UUID playerUuid) {
        Path filePath = getFilePath(playerUuid);
        if (Files.exists(filePath)) {
            try (Reader reader = Files.newBufferedReader(filePath)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                long balance = json.get("balance").getAsLong();
                return new Wallet(playerUuid, balance);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Could not load wallet for " + playerUuid, e);
            }
        }
        return new Wallet(playerUuid, 0);
    }

    public static void saveWallet(UUID playerUuid) {
        Wallet wallet = wallets.get(playerUuid);
        if (wallet == null) return;

        try {
            if (Files.notExists(STORAGE_DIR)) {
                Files.createDirectories(STORAGE_DIR);
            }

            Path filePath = getFilePath(playerUuid);
            try (Writer writer = Files.newBufferedWriter(filePath)) {
                JsonObject json = new JsonObject();
                json.addProperty("balance", wallet.getBalance());
                GSON.toJson(json, writer);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not save wallet for " + playerUuid, e);
        }
    }

    public static void saveAll() {
        wallets.keySet().forEach(WalletManager::saveWallet);
    }

    private static Path getFilePath(UUID playerUuid) {
        return STORAGE_DIR.resolve(playerUuid.toString() + ".json");
    }
}
