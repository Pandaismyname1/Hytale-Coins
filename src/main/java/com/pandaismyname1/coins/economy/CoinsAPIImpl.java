package com.pandaismyname1.coins.economy;

import com.pandaismyname1.coins.api.CoinsAPI;
import com.pandaismyname1.coins.config.ConfigManager;
import java.util.UUID;

public class CoinsAPIImpl implements CoinsAPI {
    @Override
    public long getBalance(UUID playerUuid) {
        return WalletManager.getWallet(playerUuid).getBalance();
    }

    @Override
    public void addCoins(UUID playerUuid, long amount) {
        WalletManager.getWallet(playerUuid).add(amount);
    }

    @Override
    public boolean removeCoins(UUID playerUuid, long amount) {
        return WalletManager.getWallet(playerUuid).remove(amount);
    }

    @Override
    public void setBalance(UUID playerUuid, long amount) {
        Wallet wallet = WalletManager.getWallet(playerUuid);
        wallet.remove(wallet.getBalance());
        wallet.add(amount);
    }

    @Override
    public boolean transferCoins(UUID senderUuid, UUID recipientUuid, long amount) {
        if (amount <= 0) return false;
        if (senderUuid.equals(recipientUuid)) return false;

        Wallet senderWallet = WalletManager.getWallet(senderUuid);
        if (senderWallet.remove(amount)) {
            Wallet recipientWallet = WalletManager.getWallet(recipientUuid);
            recipientWallet.add(amount);
            return true;
        }
        return false;
    }

    @Override
    public String getCurrencyNameSingular() {
        return ConfigManager.getConfig().getCurrencyNameSingular();
    }

    @Override
    public String getCurrencyNamePlural() {
        return ConfigManager.getConfig().getCurrencyNamePlural();
    }
}
