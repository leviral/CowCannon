package me.levple.cowcannon.listeners;

import org.bukkit.event.Listener;

public class PlayerMove implements Listener {

    private final static PlayerMove instance = new PlayerMove();

    private PlayerMove() {}

    /* @EventHandler
    public void onPlayerMove(PlayerMoveEvent event, double borderRadius) {

        if (borderRadius == Double.parseDouble(null)) {
            return;
        }
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Location borderCenter = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        double distance = location.distance(borderCenter);

        if (distance > borderRadius) {
            // Spieler überschreitet die Grenze - teleportiere ihn zurück
            Location safeLocation = playerLocation.clone().subtract(playerLocation.clone().toVector().subtract(borderCenter.toVector()).normalize());
            safeLocation.setY(player.getWorld().getHighestBlockYAt(safeLocation)); // Stelle sicher, dass der Spieler nicht in Blöcken feststeckt
            player.teleport(safeLocation);

            // Informiere den Spieler
            player.sendMessage("Du kannst die Grenze nicht überschreiten!");
        }
    }

    public static PlayerMove getInstance() {
        return instance;
    }*/
}
