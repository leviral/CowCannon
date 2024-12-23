package me.levple.cowcannon.gui;

import me.levple.cowcannon.CowCannon;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ParticleWorldBorder {

    private ParticleWorldBorder() {
    }
    private final static ParticleWorldBorder instance = new ParticleWorldBorder();

    private final Particle particleType = Particle.FLAME;

    public void startParticleBorder(World world, double borderRadius, Location borderCenter) {
        int minY = world.getMinHeight();
        int maxY = world.getMaxHeight();
        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                World world = borderCenter.getWorld();
                if (world == null) {
                    cancel();
                    return;
                }

                for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 32) {
                    double x = Math.cos(theta) * borderRadius;
                    double z = Math.sin(theta) * borderRadius;
                    for (double y = minY; y <= maxY; y++) {
                        Location particleLocation = borderCenter.clone().add(x, 2*y, z);
                        world.spawnParticle(particleType, particleLocation, 1, 0, 0, 0, 0);
                    }
                }
            }
        }.runTaskTimer(CowCannon.getInstance(), 0, 10);
    }


    public static ParticleWorldBorder getInstance() {
        return instance;
    }
}
