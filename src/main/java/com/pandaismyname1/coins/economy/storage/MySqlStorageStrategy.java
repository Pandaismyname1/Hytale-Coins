package com.pandaismyname1.coins.economy.storage;

import com.pandaismyname1.coins.config.ModConfig;
import com.pandaismyname1.coins.economy.Wallet;

import java.sql.*;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySqlStorageStrategy implements StorageStrategy {
    private static final Logger LOGGER = Logger.getLogger(MySqlStorageStrategy.class.getName());
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public MySqlStorageStrategy(ModConfig config) {
        this.url = String.format("jdbc:mysql://%s:%d/%s", config.getMysqlHost(), config.getMysqlPort(), config.getMysqlDatabase());
        this.user = config.getMysqlUser();
        this.password = config.getMysqlPassword();
        setupTable();
    }

    private synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed() || !connection.isValid(2)) {
            if (connection != null && !connection.isClosed()) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    private void setupTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player_wallets (" +
                "player_uuid VARCHAR(36) PRIMARY KEY," +
                "balance BIGINT NOT NULL DEFAULT 0" +
                ")";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not setup MySQL table", e);
        }
    }

    @Override
    public Wallet loadWallet(UUID playerUuid) {
        String sql = "SELECT balance FROM player_wallets WHERE player_uuid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerUuid.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Wallet(playerUuid, rs.getLong("balance"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not load wallet for " + playerUuid + " from MySQL", e);
        }
        return new Wallet(playerUuid, 0);
    }

    @Override
    public void saveWallet(Wallet wallet) {
        String sql = "INSERT INTO player_wallets (player_uuid, balance) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE balance = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, wallet.getOwner().toString());
            pstmt.setLong(2, wallet.getBalance());
            pstmt.setLong(3, wallet.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not save wallet for " + wallet.getOwner() + " to MySQL", e);
        }
    }

    @Override
    public void saveAll(Collection<Wallet> wallets) {
        if (wallets.isEmpty()) return;
        String sql = "INSERT INTO player_wallets (player_uuid, balance) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE balance = ?";
        try (Connection conn = getConnection()) {
            boolean originalAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Wallet wallet : wallets) {
                    pstmt.setString(1, wallet.getOwner().toString());
                    pstmt.setLong(2, wallet.getBalance());
                    pstmt.setLong(3, wallet.getBalance());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(originalAutoCommit);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not save all wallets to MySQL", e);
        }
    }

    @Override
    public void shutdown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing MySQL connection", e);
        }
    }
}
