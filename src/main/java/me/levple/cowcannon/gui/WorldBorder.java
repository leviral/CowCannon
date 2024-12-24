package me.levple.cowcannon.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class WorldBorder {

    private final static WorldBorder instance = new WorldBorder();

    private WorldBorder() {
    }

    public void setupWorldBorder(Player player, Double radius, Double damageBuffer, Double damageAmount) {
        World world = Bukkit.getWorld(player.getWorld().getName());
        if (world == null) {
            player.sendMessage(Component.text("Keine Welt gefunden.", NamedTextColor.RED));
        } else {
            Location location = player.getLocation();
            player.sendMessage(Component.text("Die World Border wurde erfolgreich konfiguriert.", NamedTextColor.GREEN));
            GameSettings.getInstance().setWorldBorder(world.getName(), location, radius, damageBuffer, damageAmount);
        }
    }

    public void loadWorldBorder(Player player) {
        YamlConfiguration config = GameSettings.getInstance().getConfig();
        try {
            World world = Bukkit.getWorld(config.getString("worldBorder.world"));
            double x = config.getDouble("worldBorder.locationX");
            double z = config.getDouble("worldBorder.locationZ");
            double radius = config.getDouble("worldBorder.radius");
            double damageBuffer = config.getDouble("worldBorder.damageBuffer");
            double damageAmount = config.getDouble("worldBorder.damageAmount");

            org.bukkit.WorldBorder border = world.getWorldBorder();
            border.setCenter(x, z);
            border.setSize(radius);
            border.setWarningDistance(10);
            border.setDamageBuffer(damageBuffer);
            border.setDamageAmount(damageAmount);
            player.sendMessage(Component.text("Die World Border wurde erfolgreich geladen.", NamedTextColor.GREEN));

        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Component.text("Keine World Border gefunden.", NamedTextColor.RED));
        }
    }

    public void removeWorldBorder(Player player) {
        YamlConfiguration config = GameSettings.getInstance().getConfig();
        try {
            World world = Bukkit.getWorld(config.getString("worldBorder.world"));
            org.bukkit.WorldBorder border = world.getWorldBorder();

            // Größe und Zentrum
            border.setSize(60000000); // Maximalgröße
            border.setCenter(0, 0); // Standardzentrum

            // Damage-Werte
            border.setDamageBuffer(5.0); // Damage-Bereich
            border.setDamageAmount(0.2); // Schaden pro Block

            // Warnung
            border.setWarningTime(15); // Warnungszeit
            border.setWarningDistance(5); // Warnungsdistanz

            config.set("worldBorder.world", null);
            config.set("worldBorder.locationX", null);
            config.set("worldBorder.locationZ", null);
            config.set("worldBorder.radius", null);
            config.set("worldBorder.damageBuffer", null);
            config.set("worldBorder.damageAmount", null);

            GameSettings.getInstance().save();

            player.sendMessage(Component.text("Die World Border wurde erfolgreich gelöscht.", NamedTextColor.GREEN));

        } catch (Exception e) {
            player.sendMessage(Component.text("Es ist ein Fehler beim Löschen der World Border aufgetreten.", NamedTextColor.RED));
            e.printStackTrace();
        }
    }

    public static WorldBorder getInstance() {
        return instance;
    }
}
