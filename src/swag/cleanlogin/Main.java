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

    private int gamemodeDelay; // Seconds to delay before teleport
    private int safeLocationRange; // Range for finding a safe location

    @Override
    public void onEnable() {
        this.saveDefaultConfig(); // Ensure config.yml is saved to plugin folder if not existing
        gamemodeDelay = this.getConfig().getInt("settings.gamemodeDelay");
        safeLocationRange = this.getConfig().getInt("settings.safeLocationRange");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.SPECTATOR);  // Put player into spectator mode

        new BukkitRunnable() {
            @Override
            public void run() {
                if(event.getPlayer().isOnline()) {
                    Location safeLocation = findSafeLocation(event.getPlayer().getLocation());
                    if (safeLocation != null) {
                        event.getPlayer().teleport(safeLocation);
                    } else {
                        event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
                    }
                    event.getPlayer().setGameMode(GameMode.SURVIVAL);
                }
            }
        }.runTaskLater(this, 20L * gamemodeDelay);
    }

    public Location findSafeLocation(Location location) {
        for (int x = -safeLocationRange; x <= safeLocationRange; x++) {
            for (int y = -safeLocationRange; y <= safeLocationRange; y++) {
                for (int z = -safeLocationRange; z <= safeLocationRange; z++) {
                    Location checkLocation = location.clone().add(x, y, z);
                    if (isLocationSafe(checkLocation)) {
                        return checkLocation;
                    }
                }
            }
        }
        return null;
    }
    public boolean isLocationSafe(Location location) {
        Block feet = location.getBlock();
        Block head = feet.getRelative(0, 1, 0);
        Block ground = feet.getRelative(0, -1, 0);
        return !feet.getType().isSolid() && !head.getType().isSolid() && ground.getType().isSolid();
    }
    // The isLocationSafe method remains unchanged
}