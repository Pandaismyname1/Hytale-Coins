package com.pandaismyname1.coins.economy.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pandaismyname1.coins.economy.Wallet;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonStorageStrategy implements StorageStrategy {
    private static final Logger LOGGER = Logger.getLogger(JsonStorageStrategy.class.getName());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path STORAGE_DIR = Paths.get("universe", "mods", "coins", "players");

    @Override
    public Wallet loadWallet(UUID playerUuid) {
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

    @Override
    public void saveWallet(Wallet wallet) {
        try {
            if (Files.notExists(STORAGE_DIR)) {
                Files.createDirectories(STORAGE_DIR);
            }

            Path filePath = getFilePath(wallet.getOwner());
            try (Writer writer = Files.newBufferedWriter(filePath)) {
                JsonObject json = new JsonObject();
                json.addProperty("balance", wallet.getBalance());
                GSON.toJson(json, writer);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not save wallet for " + wallet.getOwner(), e);
        }
    }

    @Override
    public void saveAll(Collection<Wallet> wallets) {
        wallets.forEach(this::saveWallet);
    }

    @Override
    public void shutdown() {
        // No-op for JSON
    }

    private Path getFilePath(UUID playerUuid) {
        return STORAGE_DIR.resolve(playerUuid.toString() + ".json");
    }
}
