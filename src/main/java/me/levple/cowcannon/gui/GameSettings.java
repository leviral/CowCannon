package me.levple.cowcannon.gui;

import me.levple.cowcannon.CowCannon;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class GameSettings {

    private final static GameSettings instance = new GameSettings();

    private GameSettings() {}

    private File file;

    private YamlConfiguration config;

    public void load(){
        file = new File(CowCannon.getInstance().getDataFolder(), "game_settings.yml");

        if (!file.exists())
            CowCannon.getInstance().saveResource("game_settings.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWorldBorder(String world, Location location, Double radius, Double damageBuffer, Double damageAmount){
        if (!config.contains("worldBorder.world"))
            config.createSection("worldBorder.world");
        if (!config.contains("worldBorder.locationX")) {
            config.createSection("worldBorder.locationX");
        }
        if (!config.contains("worldBorder.locationZ")) {
            config.createSection("worldBorder.locationZ");
        }
        if (!config.contains("worldBorder.radius")) {
            config.createSection("worldBorder.radius");
        }
        if (!config.contains("worldBorder.damageBuffer")) {
            config.createSection("worldBorder.damageBuffer");
        }
        if (!config.contains("worldBorder.damageAmount")) {
            config.createSection("worldBorder.damageAmount");
        }
        config.set("worldBorder.world", world);
        config.set("worldBorder.locationX", location.getX());
        config.set("worldBorder.locationZ", location.getZ());
        config.set("worldBorder.radius", radius);
        config.set("worldBorder.damageBuffer", damageBuffer);
        config.set("worldBorder.damageAmount", damageAmount);
        save();
    }

    public void setParticleBorder(String world, Location location, Double radius, String particleType, Integer particleCount) {
        if (!config.contains("particleBorder.world"))
            config.createSection("particleBorder.world");
        if (!config.contains("particleBorder.centerX")) {
            config.createSection("particleBorder.centerX");
        }
        if (!config.contains("particleBorder.centerY")) {
            config.createSection("particleBorder.centerY");
        }
        if (!config.contains("particleBorder.centerZ")) {
            config.createSection("particleBorder.centerZ");
        }
        if (!config.contains("particleBorder.radius")) {
            config.createSection("particleBorder.radius");
        }
        if (!config.contains("particleBorder.particleType")) {
            config.createSection("particleBorder.particleType");
        }
        if (!config.contains("particleBorder.particleCount")) {
            config.createSection("particleBorder.particleCount");
        }
        config.set("particleBorder.world", world);
        config.set("particleBorder.centerX", location.getX());
        config.set("particleBorder.centerY", location.getY());
        config.set("particleBorder.centerZ", location.getZ());
        config.set("particleBorder.radius", radius);
        config.set("particleBorder.particleType", particleType);
        config.set("particleBorder.particleCount", particleCount);
        save();
    }

    public void removeParticleBorder() {
        config.set("particleBorder.world", null);
        config.set("particleBorder.centerX", null);
        config.set("particleBorder.centerY", null);
        config.set("particleBorder.centerZ", null);
        config.set("particleBorder.radius", null);
        config.set("particleBorder.particleType", null);
        config.set("particleBorder.particleCount", null);
        save();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public static GameSettings getInstance() {
        return instance;
    }
}
