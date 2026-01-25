package com.pandaismyname1.coins.command;

import com.pandaismyname1.coins.ui.WalletPage;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class WalletCommand extends AbstractCommand {
    public WalletCommand() {
        super("wallet", "Check your coin balance");
        this.setPermissionGroup(GameMode.Adventure);
    }

    @Override
    protected CompletableFuture<Void> execute(@NonNullDecl CommandContext commandContext) {
        if (!commandContext.isPlayer()) {
            commandContext.sendMessage(Message.empty().insert("This command can only be used by players.").color("RED"));
            return CompletableFuture.completedFuture(null);
        }

        Player player = (Player) commandContext.sender();
        
        // Open the wallet UI
        player.getPageManager().openCustomPage(player.getReference(), player.getReference().getStore(), new WalletPage(player.getPlayerRef()));

        return CompletableFuture.completedFuture(null);
    }
}
