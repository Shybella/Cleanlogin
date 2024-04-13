package swag.cleanlogin;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.SPECTATOR);  // Put player into spectator mode

        new BukkitRunnable() {
            @Override
            public void run() {
                // Make sure player is still online
                if(event.getPlayer().isOnline()) {
                    Location safeLocation = findSafeLocation(event.getPlayer().getLocation());
                    if (safeLocation != null) {
                        event.getPlayer().teleport(safeLocation); // Teleport to a safe location
                    } else {
                        // If no safe location is found, teleport player to the world spawn
                        event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
                    }
                    event.getPlayer().setGameMode(GameMode.SURVIVAL);  // Change back to survival mode
                }
            }
        }.runTaskLater(this, 20L * 5);  // 20 Ticks * 5 = 5 seconds
    }

    // Method to find a safe location near the player's current location
    public Location findSafeLocation(Location location) {
        int range = 5; // Range to check for a safe location
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    Location checkLocation = location.clone().add(x, y, z);
                    if (isLocationSafe(checkLocation)) {
                        return checkLocation; // Found a safe location
                    }
                }
            }
        }
        return null; // No safe location found
    }

    // Method to check if a location is safe (not inside blocks)
    public boolean isLocationSafe(Location location) {
        Block feet = location.getBlock();
        Block head = feet.getRelative(0, 1, 0);
        Block ground = feet.getRelative(0, -1, 0);
        return !feet.getType().isSolid() && !head.getType().isSolid() && ground.getType().isSolid();
    }
}