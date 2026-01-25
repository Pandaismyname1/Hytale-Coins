package com.pandaismyname1.coins.api;

import java.util.UUID;

/**
 * API for interacting with the Coins economy.
 */
public interface CoinsAPI {
    /**
     * Gets the balance of a player's wallet.
     *
     * @param playerUuid the UUID of the player
     * @return the balance in copper
     */
    long getBalance(UUID playerUuid);

    /**
     * Adds coins to a player's wallet.
     *
     * @param playerUuid the UUID of the player
     * @param amount     the amount of copper to add
     */
    void addCoins(UUID playerUuid, long amount);

    /**
     * Removes coins from a player's wallet.
     *
     * @param playerUuid the UUID of the player
     * @param amount     the amount of copper to remove
     * @return true if the coins were successfully removed, false if the player has insufficient funds
     */
    boolean removeCoins(UUID playerUuid, long amount);

    /**
     * Sets the balance of a player's wallet.
     *
     * @param playerUuid the UUID of the player
     * @param amount     the new balance in copper
     */
    void setBalance(UUID playerUuid, long amount);

    /**
     * Transfers coins from one player to another.
     *
     * @param senderUuid    the UUID of the sender
     * @param recipientUuid the UUID of the recipient
     * @param amount        the amount of copper to transfer
     * @return true if the transfer was successful, false if the sender has insufficient funds
     */
    boolean transferCoins(UUID senderUuid, UUID recipientUuid, long amount);

    /**
     * Gets the singular name of the currency.
     *
     * @return the singular currency name
     */
    String getCurrencyNameSingular();

    /**
     * Gets the plural name of the currency.
     *
     * @return the plural currency name
     */
    String getCurrencyNamePlural();

    /**
     * Gets the formatted currency name for a specific amount (singular or plural).
     *
     * @param amount the amount
     * @return the formatted currency name
     */
    default String getCurrencyName(long amount) {
        return amount == 1 ? getCurrencyNameSingular() : getCurrencyNamePlural();
    }

    /**
     * Formats an amount with the correct currency name.
     *
     * @param amount the amount
     * @return the formatted amount string (e.g., "10 Coins")
     */
    default String format(long amount) {
        return amount + " " + getCurrencyName(amount);
    }
}
