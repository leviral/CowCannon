package me.levple.cowcannon.commands;

import me.levple.cowcannon.CowCannon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ClockCommand implements CommandExecutor, TabCompleter {

    private final CowCannon plugin;

    public ClockCommand(CowCannon plugin) {
        this.plugin = plugin;
    }

    private boolean isPaused = true;
    private int counter = 0;

    private BukkitTask taskStart;

    private BukkitTask taskStop;

    private BukkitTask taskReset;

    private String[] timeConvert(Integer counter) {
        int days = counter / (3600 * 24);
        int hours = (counter % (3600 * 24)) / 3600;
        int minutes = (counter % 3600) / 60;
        int seconds = counter % 60;
        if (days > 0) {
            return new String[]{seconds + "s", minutes + "min ", hours + "h ", days + "d "};
        }
        if (hours > 0) {
            return new String[]{seconds + "s", minutes + "min ", hours + "h "};
        }
        if (minutes > 0) {
            return new String[]{seconds + "s", minutes + "min "};
        }
        return new String[]{seconds + "s"};
    }

    private void output(String[] time, Player player) {
        if (time.length > 3) {
            player.sendActionBar(Component.text(time[3] + time[2] + time[1] + time[0], NamedTextColor.AQUA));
        } else if (time.length > 2) {
            player.sendActionBar(Component.text(time[2] + time[1] + time[0], NamedTextColor.AQUA));
        } else if (time.length > 1) {
            player.sendActionBar(Component.text(time[1] + time[0], NamedTextColor.AQUA));
        } else {
            player.sendActionBar(Component.text(time[0], NamedTextColor.AQUA));
        }
    }

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
        if (args.length != 1) {
            player.sendMessage(Component.text("Bitte benutze [toggle], [pause] oder [remove].", NamedTextColor.RED));
            return true;
        }
        switch (args[0]) {
            case "toggle" -> {
                if (isPaused) {
                    // Timer starten
                    isPaused = false;
                    if (taskStop != null) {
                        taskStop.cancel();
                    }
                    if (taskReset != null) {
                        taskReset.cancel();
                    }
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(players, Sound.ENTITY_PLAYER_LEVELUP, 10, 0);
                    }
                    taskStart = new BukkitRunnable() {
                        public void run() {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                try {
                                    counter++;
                                    String[] time = timeConvert(counter);
                                    output(time, players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 20L);
                } else {
                    player.sendMessage(Component.text("Die Clock läuft bereits.", NamedTextColor.YELLOW));
                }
            }
            case "pause" -> {
                if (!(isPaused)) {
                    isPaused = true;
                    if (taskStart != null) {
                        taskStart.cancel();
                    }
                    if (taskReset != null) {
                        taskReset.cancel();
                    }
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(player, Sound.ENTITY_SKELETON_SHOOT, 10, 0);
                    }
                    taskStop = new BukkitRunnable() {
                        public void run() {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                String[] time = timeConvert(counter);
                                output(time, players);
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 1L);
                    player.sendMessage(Component.text("Die Clock wurde pausiert.", NamedTextColor.GREEN));
                } else {
                    player.sendMessage(Component.text("Die Clock läuft nicht.", NamedTextColor.YELLOW));
                }
            }
            /*case "reset" -> {
                isPaused = true;
                if (taskStart != null) {
                    taskStart.cancel();
                }
                if (taskStop != null) {
                    taskStop.cancel();
                }
                counter = 0;
                taskReset = new BukkitRunnable() {
                    public void run() {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            players.sendActionBar(Component.text(0 + "d " + 0 + "h " + 0 + "min " + 0 + "s", NamedTextColor.AQUA));
                        }
                    }
                }.runTaskTimer(plugin, 0L, 1L);
            }*/
            case "remove" -> {
                isPaused = true;
                counter = 0;
                if (taskStart != null) {
                    taskStart.cancel();
                }
                if (taskStop != null) {
                    taskStop.cancel();
                }
                if (taskReset != null) {
                    taskReset.cancel();
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(player, Sound.ENTITY_ARMOR_STAND_BREAK, 10, 0);
                    players.sendActionBar("");
                }
                player.sendMessage(Component.text("Die Clock wurde entfernt.", NamedTextColor.YELLOW));
            }
            default -> {
                player.sendMessage(Component.text("Bitte benutze [toggle], [pause] oder [remove].", NamedTextColor.RED));
                return true;
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("toggle");
            suggestions.add("pause");
            /*suggestions.add("reset");*/
            suggestions.add("remove");
        }
        String currentInput = args[args.length - 1].toLowerCase();
        suggestions.removeIf(s -> !s.toLowerCase().startsWith(currentInput));

        return suggestions;
    }
}
