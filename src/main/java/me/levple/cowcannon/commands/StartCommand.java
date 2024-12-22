package me.levple.cowcannon.commands;

import me.levple.cowcannon.CowCannon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {

    private final CowCannon plugin;

    public StartCommand(CowCannon plugin) {
        this.plugin = plugin;
    }

    BukkitTask timerTask;

    BukkitTask startTask;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Du musst ein Spieler sein, um diesen Command ausführen zu dürfen.", NamedTextColor.RED));
            return true;
        }
        if (!(player.isOp())) {
            player.sendMessage(Component.text("Du darfst diesen Command nicht ausführen.", NamedTextColor.RED));
            return true;
        }
        if (args.length != 0) {
            player.sendMessage(Component.text("Nutze den Command /start.", NamedTextColor.RED));
            return true;
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setGameMode(GameMode.ADVENTURE);
            players.setHealth(20);
            players.setFoodLevel(20);
            players.setSaturation(20);
            players.setLevel(0);
            players.setExp(0);
            players.setInvulnerable(true);
        }
        for (int i = 0; i <= 9; i++) {
            int finalI = 10 - i;
            timerTask = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.sendActionBar(Component.text("Das Spiel startet in: " + finalI, NamedTextColor.GOLD));
                    }
                }
            }.runTaskLater(plugin,20*i);
        }
        startTask = new BukkitRunnable() {
            @Override
            public void run() {
                timerTask.cancel();
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendActionBar(Component.text("LET'S GO!!", NamedTextColor.GOLD));
                    players.playSound(players, Sound.ENTITY_PLAYER_LEVELUP, 10, 0);
                    players.setGameMode(GameMode.SURVIVAL);
                    players.setHealth(20);
                    players.setFoodLevel(20);
                    players.setSaturation(20);
                    players.setInvulnerable(false);
                }
            }
        }.runTaskLater(plugin, 200);

        return true;
    }
}
