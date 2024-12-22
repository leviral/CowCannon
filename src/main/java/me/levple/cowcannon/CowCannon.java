package me.levple.cowcannon;

import me.levple.cowcannon.commands.*;
import me.levple.cowcannon.gui.JoinCounter;
import me.levple.cowcannon.listeners.PlayerJoin;
import me.levple.cowcannon.listeners.PlayerQuit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CowCannon extends JavaPlugin {

    private FileConfiguration config;

    public CowCannon(){

    }

    private static CowCannon instance;
    
    public static CowCannon getInstance(){
        if (instance == null){
            return instance = new CowCannon();
        }
        return instance;
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        this.config = getConfig();
        JoinCounter.getInstance().load();
        registerCommands();
        registerListeners();
        registerTabCompletions();
        getLogger().info("CowCannon is enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CowCannon disabled");
    }

    private void registerCommands(){
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("setspawnblock").setExecutor(new SpawnPlaceCommand());
        getCommand("cooldown").setExecutor(new CooldownCommand());
        getCommand("clock").setExecutor(new ClockCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(),this);
    }

    private void registerTabCompletions(){
        getCommand("setspawnblock").setTabCompleter(new SpawnPlaceCommand());
        getCommand("clock").setTabCompleter(new ClockCommand(this));
    }

}
