# Hytale Coins Mod

A simple economy mod for Hytale that adds physical coins and a digital wallet system.

## Features

- **Physical Coins**: Six tiers of coins are available, each with its own value in Copper:
    - **Copper**: 1 Copper
    - **Iron**: 10 Copper
    - **Silver**: 100 Copper
    - **Gold**: 1,000 Copper
    - **Emerald**: 10,000 Copper
    - **Diamond**: 100,000 Copper
- **Wallet System**: Deposit coins into your digital wallet and withdraw them at any time.
- **Commands**:
    - `/wallet`: Open the wallet UI to see your balance and manage coins.
    - `/pay <player> <amount>`: Send copper coins to another player.
    - `/economy <add/set> <player> <amount>`: Admin commands to manage player balances (Alias: `/eco`). Requires `coins.admin.economy` permission.
- **Interactions**: Right click with a coin in hand to quickly deposit it into your wallet.
- **API**: A built-in Java API for other mods to interact with the economy.

## Installation

1. Ensure you have Hytale installed.
2. Place the `coins.jar` (or the mod folder) into your Hytale `mods` directory.
3. Launch the Hytale server.

## Development

### Prerequisites

- **Java Development Kit (JDK)**: Version 25 or newer is required.
- **Hytale**: Local installation for server dependencies.

### Building from Source

1. Clone the repository:
   ```bash
   git clone https://github.com/Pandaismyname1/hytale-coins.git
   ```
2. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```
   The built JAR will be in `build/libs/`.

### IntelliJ IDEA Integration

The project includes run configurations for IntelliJ IDEA.
1. Open the project in IntelliJ.
2. Select the **ModHytaleServer** configuration to run the server with the mod.

## Project Structure

- `src/main/java/com/pandaismyname1/coins`:
    - `api/`: Public API and provider for external mods.
    - `command/`: Mod commands.
    - `economy/`: Core economy logic and wallet management.
    - `interaction/`: Custom player/item interactions.
    - `ui/`: Custom NoesisGUI pages.
- `src/main/resources`: Mod assets, UI files, and item configurations.

## Developer API

Other mods can interact with the Coins economy using the `CoinsAPI`.

### Accessing the API

```java
import com.pandaismyname1.coins.api.CoinsAPI;
import com.pandaismyname1.coins.api.CoinsAPIProvider;

CoinsAPI api = CoinsAPIProvider.get();
if (api != null) {
    // Use the API
}
```

### Available Methods

- `getBalance(UUID playerUuid)`: Gets the player's balance in Copper.
- `addCoins(UUID playerUuid, long amount)`: Adds Copper to the player's wallet.
- `removeCoins(UUID playerUuid, long amount)`: Removes Copper from the player's wallet (returns `false` if insufficient funds).
- `setBalance(UUID playerUuid, long amount)`: Sets the player's balance.
- `transferCoins(UUID senderUuid, UUID recipientUuid, long amount)`: Transfers Copper between players.

## License

All Rights Reserved. Usage in modpacks is allowed as long as the download is provided through CurseForge.
