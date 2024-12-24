package me.levple.cowcannon.listeners;

import me.levple.cowcannon.gui.GameSettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    final FileConfiguration config = GameSettings.getInstance().getConfig();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (!(config.contains("particleBorder.centerX") || config.contains("particleBorder.centerY") || config.contains("particleBorder.centerZ") || config.contains("particleBorder.radius") || config.contains("particleBorder.world"))) {
            return;
        }

        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        try {
            double centerX = config.getDouble("particleBorder.centerX");
            double centerY = config.getDouble("particleBorder.centerY");
            double centerZ = config.getDouble("particleBorder.centerZ");
            double borderRadius = config.getDouble("particleBorder.radius");
            String worldName = config.getString("particleBorder.world");
            World world = Bukkit.getWorld(worldName);

            Location borderCenter = new Location(world, centerX, centerY, centerZ);
            double distance = playerLocation.distance(borderCenter);

            if (distance > borderRadius) {
                // Spieler überschreitet die Grenze - teleportiere ihn zurück
                Location safeLocation = playerLocation.clone().subtract(playerLocation.clone().toVector().subtract(borderCenter.toVector()).normalize());
                safeLocation.setY(player.getWorld().getHighestBlockYAt(safeLocation)); // Stelle sicher, dass der Spieler nicht in Blöcken feststeckt
                player.teleport(safeLocation);

                // Informiere den Spieler
                player.sendMessage(Component.text("Du kannst die Grenze nicht überschreiten!", NamedTextColor.YELLOW));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
