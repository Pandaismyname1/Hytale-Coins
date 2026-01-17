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
}
