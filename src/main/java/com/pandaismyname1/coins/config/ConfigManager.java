package com.pandaismyname1.coins.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager {
    private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get("universe", "mods", "coins", "config.json");
    
    private static ModConfig config;

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                config = GSON.fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Could not load mod config", e);
                config = new ModConfig();
            }
        } else {
            config = new ModConfig();
            save();
        }
    }

    public static void save() {
        if (config == null) return;
        
        try {
            Path parent = CONFIG_PATH.getParent();
            if (parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }

            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not save mod config", e);
        }
    }

    public static ModConfig getConfig() {
        if (config == null) {
            load();
        }
        return config;
    }
}
