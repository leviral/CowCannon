package me.levple.cowcannon.gui;

import me.levple.cowcannon.CowCannon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class ParticleWorldBorder {

    private ParticleWorldBorder() {
    }

    private final static ParticleWorldBorder instance = new ParticleWorldBorder();

    private final Random random = new Random();

    public BukkitTask task;

    private final Set<UUID> playersOutsideBorder = new HashSet<>();
    private BukkitTask borderDamageTask;

    public void startParticleBorder(World world, double borderRadius, Location borderCenter, Particle particleType, double centerX, double centerZ, int particleCount) {
        int minY = world.getMinHeight();
        int maxY = world.getMaxHeight();
        task = new BukkitRunnable() {

            @Override
            public void run() {

                World world = borderCenter.getWorld();
                if (world == null) {
                    cancel();
                    return;
                }

                for (int i = 0; i < particleCount; i++) {
                    double theta = random.nextDouble() * Math.PI * 2;
                    double x = centerX + borderRadius * Math.cos(theta);
                    double z = centerZ + borderRadius * Math.sin(theta);
                    for (double y = minY; y <= maxY; y+= 3) {
                        Location particleLocation = new Location(world, x, y, z);
                        world.spawnParticle(particleType, particleLocation, 1, .5, 0, .5, 0);
                    }
                }
            }
        }.runTaskTimer(CowCannon.getInstance(), 0, 20);
    }

    public void startBorderDamageTask(World world, Location borderCenter, double borderRadius, double damageAmount) {
        borderDamageTask = new BukkitRunnable() {

            @Override
            public void run() {
                for (UUID playerUUID : playersOutsideBorder) {
                    Player player = Bukkit.getPlayer(playerUUID);

                    // Prüfen, ob der Spieler noch online ist
                    if (player == null || !player.isOnline()) {
                        playersOutsideBorder.remove(playerUUID);
                        continue;
                    }

                    // Berechne Entfernung zur Border-Mitte
                    double deltaX = player.getLocation().getX() - borderCenter.getX();
                    double deltaZ = player.getLocation().getZ() - borderCenter.getZ();
                    double distance2D = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
                    double damageAmount = (distance2D - borderRadius) * 0.5;

                    // Schaden zufügen, wenn Spieler außerhalb der Border ist
                    if (distance2D + 1 > borderRadius) {
                        player.damage(damageAmount);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f);
                        player.getWorld().spawnParticle(Particle.CRIT, player.getLocation(), 10);
                    } else {
                        // Spieler ist wieder innerhalb der Border, entfernen
                        playersOutsideBorder.remove(playerUUID);
                    }
                }
            }

        }.runTaskTimer(CowCannon.getInstance(), 0, 20);
    }

    public void stopBorderDamageTask() {
        if (borderDamageTask != null && !borderDamageTask.isCancelled()) {
            borderDamageTask.cancel();
        }
        playersOutsideBorder.clear(); // Spielerliste leeren
    }

    public BukkitTask getTask() {
        return task;
    }

    public Set<UUID> getPlayersOutsideBorder() {
        return playersOutsideBorder;
    }

    public static ParticleWorldBorder getInstance() {
        return instance;
    }
}
