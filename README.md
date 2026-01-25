# Hytale Coins Mod

A simple economy mod for Hytale that adds physical coins and a digital wallet system.

## Features

- **Physical Coins**: Six tiers of coins are available, each with its own value in the base currency (default: "Copper"):
    - **Copper**: 1 unit
    - **Iron**: 10 units
    - **Silver**: 100 units
    - **Gold**: 1,000 units
    - **Emerald**: 10,000 units
    - **Diamond**: 100,000 units
- **Wallet System**: Deposit coins into your digital wallet and withdraw them at any time.
- **Configurable Currency**: The name of the base currency can be configured in `config.json` (singular and plural names).
- **Commands**:
    - `/wallet`: Open the wallet UI to see your balance and manage coins.
    - `/pay <player> <amount>`: Send currency to another player.
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

- `getBalance(UUID playerUuid)`: Gets the player's balance in the base currency.
- `addCoins(UUID playerUuid, long amount)`: Adds the base currency to the player's wallet.
- `removeCoins(UUID playerUuid, long amount)`: Removes the base currency from the player's wallet (returns `false` if insufficient funds).
- `setBalance(UUID playerUuid, long amount)`: Sets the player's balance.
- `transferCoins(UUID senderUuid, UUID recipientUuid, long amount)`: Transfers the base currency between players.
- `getCurrencyNameSingular()`: Gets the singular name of the currency.
- `getCurrencyNamePlural()`: Gets the plural name of the currency.

## License

All Rights Reserved. Usage in modpacks is allowed as long as the download is provided through CurseForge.
