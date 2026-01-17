package com.pandaismyname1.coins;

import com.pandaismyname1.coins.api.CoinsAPIProvider;
import com.pandaismyname1.coins.command.EconomyCommand;
import com.pandaismyname1.coins.command.PayCommand;
import com.pandaismyname1.coins.command.WalletCommand;
import com.pandaismyname1.coins.economy.CoinsAPIImpl;
import com.pandaismyname1.coins.economy.WalletManager;
import com.pandaismyname1.coins.interaction.DepositCoinInteraction;
import com.pandaismyname1.coins.ui.WalletPage;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class Main extends JavaPlugin {

    public Main(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();

        // Register API
        CoinsAPIProvider.register(new CoinsAPIImpl());

        // Register commands
        this.getCommandRegistry().registerCommand(new WalletCommand());
        this.getCommandRegistry().registerCommand(new PayCommand());
        this.getCommandRegistry().registerCommand(new EconomyCommand());

        // Register Custom UI
        OpenCustomUIInteraction.registerSimple(this, WalletPage.class, "Coins_Wallet", WalletPage::new);

        // Register custom interaction
        ((AssetCodecMapCodec) Interaction.CODEC).register("DepositCoin", DepositCoinInteraction.class, DepositCoinInteraction.CODEC);
    }

    @Override
    public void shutdown() {
        WalletManager.saveAll();
        CoinsAPIProvider.unregister();
        super.shutdown();
    }
}

