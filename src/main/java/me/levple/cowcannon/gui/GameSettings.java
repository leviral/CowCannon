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
        if (!config.contains("worldborder.world"))
            config.createSection("worldborder.world");
        if (!config.contains("worldborder.locationX")) {
            config.createSection("worldborder.locationX");
        }
        if (!config.contains("worldborder.locationZ")) {
            config.createSection("worldborder.locationZ");
        }
        if (!config.contains("worldborder.radius")) {
            config.createSection("worldborder.radius");
        }
        if (!config.contains("worldborder.damageBuffer")) {
            config.createSection("worldborder.damageBuffer");
        }
        if (!config.contains("worldborder.damageAmount")) {
            config.createSection("worldborder.damageAmount");
        }
        config.set("worldborder.world", world);
        config.set("worldborder.locationX", location.getX());
        config.set("worldborder.locationZ", location.getZ());
        config.set("worldborder.radius", radius);
        config.set("worldborder.damageBuffer", damageBuffer);
        config.set("worldborder.damageAmount", damageAmount);
        save();
    }

    public void setParticleBorder(String world, Location location, Double radius) {
        if (!config.contains("particleborder.world"))
            config.createSection("particleborder.world");
        if (!config.contains("particleborder.centerX")) {
            config.createSection("particleborder.centerX");
        }
        if (!config.contains("particleborder.centerY")) {
            config.createSection("particleborder.centerY");
        }
        if (!config.contains("particleborder.centerZ")) {
            config.createSection("particleborder.centerZ");
        }
        if (!config.contains("particleborder.radius")) {
            config.createSection("particleborder.radius");
        }
        config.set("particleborder.world", world);
        config.set("particleborder.centerX", location.getX());
        config.set("particleborder.centerY", location.getY());
        config.set("particleborder.centerZ", location.getZ());
        config.set("particleborder.radius", radius);
        save();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public static GameSettings getInstance() {
        return instance;
    }
}
