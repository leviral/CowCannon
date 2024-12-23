package me.levple.cowcannon.commands;

import me.levple.cowcannon.CowCannon;
import me.levple.cowcannon.gui.WorldBorder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WorldBorderCommand implements CommandExecutor, TabCompleter {

    private CowCannon plugin;

    public WorldBorderCommand(CowCannon plugin) {
        this.plugin = plugin;
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
        if (!(args[0].equals("config") || args[0].equals("load") || args[0].equals("remove"))) {
            player.sendMessage(Component.text("Nutze /worldborder config | load | remove.", NamedTextColor.RED));
            return true;
        }
        if (args[0].equals("config") && args.length != 4) {
            player.sendMessage(Component.text("Nutze /worldborder config [radius] [damageBuffer] [damageAmount].", NamedTextColor.RED));
            return true;
        }
        if (args[0].equals("load") && args.length > 1) {
            player.sendMessage(Component.text("Nutze /worldborder load.", NamedTextColor.RED));
            return true;
        }
        if (args[0].equals("remove") && args.length > 1) {
            player.sendMessage(Component.text("Nutze /worldborder remove.", NamedTextColor.RED));
            return true;
        }
        switch (args[0]) {
            case "config" -> {
                try {
                    Double radius = Double.parseDouble(args[1]);
                    Double damageBuffer = Double.parseDouble(args[2]);
                    Double damageAmount = Double.parseDouble(args[3]);

                    WorldBorder.getInstance().setupWorldBorder(player, radius, damageBuffer, damageAmount);


                } catch (Exception e) {
                    player.sendMessage(Component.text("Bitte nutze für die Argumente Ganzzahlen bzw. Gleitkommazahlen.", NamedTextColor.RED));
                    e.printStackTrace();
                }
                return true;
            }
            case "load" -> WorldBorder.getInstance().loadWorldBorder(player);
            case "remove" -> WorldBorder.getInstance().removeWorldBorder(player);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("config");
            suggestions.add("load");
            suggestions.add("remove");
        }
        if (args[0].equals("config")) {
            if (args.length == 2) {
                suggestions.add("radius");
            }
            if (args.length == 3) {
                suggestions.add("damageBuffer");
            }
            if (args.length == 4) {
                suggestions.add("damageAmount");
            }
        }

        String currentInput = args[args.length - 1].toLowerCase();
        suggestions.removeIf(s -> !s.toLowerCase().startsWith(currentInput));

        return suggestions;
    }
}
