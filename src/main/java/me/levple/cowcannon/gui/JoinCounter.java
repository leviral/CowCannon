package me.levple.cowcannon.gui;

import me.levple.cowcannon.CowCannon;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;

public class JoinCounter implements Listener {

    private final static JoinCounter instance = new JoinCounter();

    public JoinCounter() {
    }

    private File file;

    private YamlConfiguration config;

    public void load() {
        file = new File(CowCannon.getInstance().getDataFolder(), "player_stats.yml");

        if (!file.exists())
            CowCannon.getInstance().saveResource("player_stats.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(Player player) {
        if (!(config.contains("joinCounter." + player.getUniqueId().toString()))) {
            config.createSection("joinCounter." + player.getUniqueId().toString());
            config.set("joinCounter." + player.getUniqueId().toString(), 0);
        }
        int joinCounter = config.getInt("joinCounter." + player.getUniqueId().toString());
        joinCounter++;
        config.set("joinCounter." + player.getUniqueId().toString(), joinCounter);
        save();
    }

    public int get(Player player) {
        return config.getInt("joinCounter." + player.getUniqueId().toString());
    }

    public static JoinCounter getInstance() {
        return instance;
    }
}
