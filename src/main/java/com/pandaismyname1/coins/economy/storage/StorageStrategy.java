package com.pandaismyname1.coins.economy.storage;

import com.pandaismyname1.coins.economy.Wallet;
import java.util.UUID;
import java.util.Collection;

public interface StorageStrategy {
    Wallet loadWallet(UUID playerUuid);
    void saveWallet(Wallet wallet);
    void saveAll(Collection<Wallet> wallets);
    void shutdown();
}
