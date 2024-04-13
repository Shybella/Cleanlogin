# CleanLogin Plugin for Bukkit/Spigot

## Introduction
CleanLogin is a Bukkit/Spigot plugin designed to enhance player experience by ensuring they are safely teleported to a secure location upon joining the server. The plugin automatically puts a player in spectator mode to prevent damage or interaction and then searches for a nearby safe location to teleport the player to, switching them back to survival mode afterwards.

## Features
- **Safe Teleportation:** Ensures that players are teleported to a safe location when they join the server.
- **Configurable Delay:** Allows server administrators to set a custom delay for teleportation after a player joins.
- **Configurable Safe Location Range:** Admins can define the search radius for identifying a safe teleportation location.

## Installation
1. Download the CleanLogin plugin JAR file.
2. Place it into your server's `plugins` folder.
3. Restart your server, or if you have a plugin manager, load the CleanLogin plugin.
4. Upon first run, the `config.yml` file will be generated in the `plugins/CleanLogin` folder.

## Configuration
Adjust the settings in the `config.yml` file to customize the plugin’s behavior.

```yaml
settings:
  teleportDelay: 5  # Delay in seconds before teleporting the player.
  safeLocationRange: 5  # Search radius for a safe location.