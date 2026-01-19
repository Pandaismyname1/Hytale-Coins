package com.pandaismyname1.coins.plugins;

import com.pandaismyname1.coins.economy.Wallet;
import com.pandaismyname1.coins.economy.WalletManager;
import net.milkbowl.vault2.economy.EconomyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VaultUnlockedEconomyTest {
    private VaultUnlockedEconomy economy;
    private UUID playerUuid;

    @BeforeEach
    public void setup() {
        economy = new VaultUnlockedEconomy();
        playerUuid = UUID.randomUUID();
        // Ensure wallet is clean for test
        Wallet wallet = WalletManager.getWallet(playerUuid);
        wallet.remove(wallet.getBalance());
    }

    @Test
    public void testGetBalance() {
        BigDecimal balance = economy.getBalance("test", playerUuid);
        assertEquals(0, balance.compareTo(BigDecimal.ZERO));

        WalletManager.getWallet(playerUuid).add(100);
        balance = economy.getBalance("test", playerUuid);
        assertEquals(0, balance.compareTo(BigDecimal.valueOf(100)));
    }

    @Test
    public void testDeposit() {
        EconomyResponse response = economy.deposit("test", playerUuid, BigDecimal.valueOf(50));
        assertTrue(response.transactionSuccess());
        assertEquals(0, response.amount.compareTo(BigDecimal.valueOf(50)));
        assertEquals(0, response.balance.compareTo(BigDecimal.valueOf(50)));
        assertEquals(50, WalletManager.getWallet(playerUuid).getBalance());
    }

    @Test
    public void testWithdraw() {
        WalletManager.getWallet(playerUuid).add(100);
        
        EconomyResponse response = economy.withdraw("test", playerUuid, BigDecimal.valueOf(40));
        assertTrue(response.transactionSuccess());
        assertEquals(0, response.amount.compareTo(BigDecimal.valueOf(40)));
        assertEquals(0, response.balance.compareTo(BigDecimal.valueOf(60)));
        assertEquals(60, WalletManager.getWallet(playerUuid).getBalance());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        WalletManager.getWallet(playerUuid).add(30);
        
        EconomyResponse response = economy.withdraw("test", playerUuid, BigDecimal.valueOf(40));
        assertFalse(response.transactionSuccess());
        assertEquals("Insufficient funds", response.errorMessage);
        assertEquals(30, WalletManager.getWallet(playerUuid).getBalance());
    }

    @Test
    public void testHas() {
        WalletManager.getWallet(playerUuid).add(100);
        assertTrue(economy.has("test", playerUuid, BigDecimal.valueOf(50)));
        assertTrue(economy.has("test", playerUuid, BigDecimal.valueOf(100)));
        assertFalse(economy.has("test", playerUuid, BigDecimal.valueOf(101)));
    }

    @Test
    public void testFormat() {
        assertEquals("100 Copper", economy.format(BigDecimal.valueOf(100)));
        assertEquals("100 Copper", economy.format(BigDecimal.valueOf(100), "Copper"));
        assertEquals("100 Gold", economy.format(BigDecimal.valueOf(100), "Gold"));
    }
}
