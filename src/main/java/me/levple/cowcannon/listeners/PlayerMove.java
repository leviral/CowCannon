package me.levple.cowcannon.listeners;

import me.levple.cowcannon.gui.GameSettings;
import me.levple.cowcannon.gui.ParticleWorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerMove implements Listener {

    private final FileConfiguration config = GameSettings.getInstance().getConfig();

    private BukkitTask task;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (!(config.contains("particleBorder.centerX") || config.contains("particleBorder.centerY") || config.contains("particleBorder.centerZ") || config.contains("particleBorder.radius") || config.contains("particleBorder.world"))) {
            return;
        }

        Player player = event.getPlayer();

        try {
            double centerX = config.getDouble("particleBorder.centerX");
            double centerY = config.getDouble("particleBorder.centerY");
            double centerZ = config.getDouble("particleBorder.centerZ");
            double borderRadius = config.getDouble("particleBorder.radius");
            String worldName = config.getString("particleBorder.world");
            World world = Bukkit.getWorld(worldName);

            Location borderCenter = new Location(world, centerX, centerY, centerZ);
            double deltaX = player.getLocation().getX() - borderCenter.getX();
            double deltaZ = player.getLocation().getZ() - borderCenter.getZ();
            double distance2D = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

            if (distance2D > borderRadius) {
                ParticleWorldBorder.getInstance().getPlayersOutsideBorder().add(player.getUniqueId());
            }
            else {
                ParticleWorldBorder.getInstance().getPlayersOutsideBorder().remove(player.getUniqueId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
