package com.pandaismyname1.coins.api;

/**
 * Provider for the Coins API.
 */
public final class CoinsAPIProvider {
    private static CoinsAPI instance;

    private CoinsAPIProvider() {}

    /**
     * Gets the current instance of the Coins API.
     *
     * @return the Coins API instance, or null if not registered
     */
    public static CoinsAPI get() {
        return instance;
    }

    /**
     * Registers the Coins API instance.
     * This is intended for internal mod use only.
     *
     * @param api the API instance to register
     */
    public static void register(CoinsAPI api) {
        if (instance != null) {
            throw new IllegalStateException("CoinsAPI is already registered");
        }
        instance = api;
    }

    /**
     * Unregisters the Coins API instance.
     * This is intended for internal mod use only.
     */
    public static void unregister() {
        instance = null;
    }

    /**
     * Gets the formatted currency name for a specific amount.
     *
     * @param amount the amount
     * @return the formatted currency name, or a default value if API not registered
     */
    public static String getCurrencyName(long amount) {
        return instance != null ? instance.getCurrencyName(amount) : (amount == 1 ? "Coin" : "Coins");
    }

    /**
     * Formats an amount with the correct currency name.
     *
     * @param amount the amount
     * @return the formatted amount string, or a default value if API not registered
     */
    public static String format(long amount) {
        return instance != null ? instance.format(amount) : amount + " " + (amount == 1 ? "Coin" : "Coins");
    }
}
