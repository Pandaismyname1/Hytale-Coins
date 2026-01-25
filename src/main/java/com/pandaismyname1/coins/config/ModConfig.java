package com.pandaismyname1.coins.config;

import java.util.HashMap;
import java.util.Map;

public class ModConfig {
    private boolean enableMobDeathDrops = true;
    private float mobDeathDropRate = 0.1f;
    private boolean enableCropHarvestDrops = true;
    private float cropHarvestDropRate = 0.2f;
    private Map<String, Long> coinValues = new HashMap<>();
    private Map<String, Long> mobDeathDrops = new HashMap<>();
    private String payCommandPermission = "";
    private String currencyNameSingular = "Coin";
    private String currencyNamePlural = "Coins";
    private String storageType = "JSON"; // JSON or MYSQL
    private String mysqlHost = "localhost";
    private int mysqlPort = 3306;
    private String mysqlDatabase = "hytale";
    private String mysqlUser = "root";
    private String mysqlPassword = "";

    public ModConfig() {
        // Default values
        coinValues.put("Coin_Copper", 1L);
        coinValues.put("Coin_Iron", 10L);
        coinValues.put("Coin_Silver", 100L);
        coinValues.put("Coin_Gold", 1000L);
        coinValues.put("Coin_Emerald", 10000L);
        coinValues.put("Coin_Diamond", 100000L);

        // Example custom mob drops
        mobDeathDrops.put("Fox", 3L);
        mobDeathDrops.put("Cow", 3L);
    }

    public boolean isEnableMobDeathDrops() {
        return enableMobDeathDrops;
    }

    public void setEnableMobDeathDrops(boolean enableMobDeathDrops) {
        this.enableMobDeathDrops = enableMobDeathDrops;
    }

    public float getMobDeathDropRate() {
        return mobDeathDropRate;
    }

    public void setMobDeathDropRate(float mobDeathDropRate) {
        this.mobDeathDropRate = mobDeathDropRate;
    }

    public boolean isEnableCropHarvestDrops() {
        return enableCropHarvestDrops;
    }

    public void setEnableCropHarvestDrops(boolean enableCropHarvestDrops) {
        this.enableCropHarvestDrops = enableCropHarvestDrops;
    }

    public float getCropHarvestDropRate() {
        return cropHarvestDropRate;
    }

    public void setCropHarvestDropRate(float cropHarvestDropRate) {
        this.cropHarvestDropRate = cropHarvestDropRate;
    }

    public Map<String, Long> getCoinValues() {
        return coinValues;
    }

    public void setCoinValues(Map<String, Long> coinValues) {
        this.coinValues = coinValues;
    }

    public Map<String, Long> getMobDeathDrops() {
        return mobDeathDrops;
    }

    public void setMobDeathDrops(Map<String, Long> mobDeathDrops) {
        this.mobDeathDrops = mobDeathDrops;
    }

    public String getPayCommandPermission() {
        return payCommandPermission;
    }

    public void setPayCommandPermission(String payCommandPermission) {
        this.payCommandPermission = payCommandPermission;
    }

    public String getCurrencyNameSingular() {
        return currencyNameSingular;
    }

    public void setCurrencyNameSingular(String currencyNameSingular) {
        this.currencyNameSingular = currencyNameSingular;
    }

    public String getCurrencyNamePlural() {
        return currencyNamePlural;
    }

    public void setCurrencyNamePlural(String currencyNamePlural) {
        this.currencyNamePlural = currencyNamePlural;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getMysqlHost() {
        return mysqlHost;
    }

    public void setMysqlHost(String mysqlHost) {
        this.mysqlHost = mysqlHost;
    }

    public int getMysqlPort() {
        return mysqlPort;
    }

    public void setMysqlPort(int mysqlPort) {
        this.mysqlPort = mysqlPort;
    }

    public String getMysqlDatabase() {
        return mysqlDatabase;
    }

    public void setMysqlDatabase(String mysqlDatabase) {
        this.mysqlDatabase = mysqlDatabase;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public void setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public void setMysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;
    }
}
