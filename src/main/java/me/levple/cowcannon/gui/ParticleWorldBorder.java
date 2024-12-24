package me.levple.cowcannon.gui;

import me.levple.cowcannon.CowCannon;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class ParticleWorldBorder {

    private ParticleWorldBorder() {
    }

    private final static ParticleWorldBorder instance = new ParticleWorldBorder();

    private final Random random = new Random();

    public BukkitTask task;

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
                        world.spawnParticle(particleType, particleLocation, 1, 0, 0, 0, 0);
                    }
                }
            }
        }.runTaskTimer(CowCannon.getInstance(), 0, 40);
    }

    public BukkitTask getTask() {
        return task;
    }

    public static ParticleWorldBorder getInstance() {
        return instance;
    }
}
