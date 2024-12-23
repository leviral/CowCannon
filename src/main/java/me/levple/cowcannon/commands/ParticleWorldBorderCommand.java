package me.levple.cowcannon.commands;

import me.levple.cowcannon.gui.GameSettings;
import me.levple.cowcannon.gui.ParticleWorldBorder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ParticleWorldBorderCommand implements CommandExecutor, TabCompleter {
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
        if (args.length == 0 || args.length > 2 || (args[0].equalsIgnoreCase("config")) && args.length == 1) {
            player.sendMessage(Component.text("Nutze /particleworldborder config [radius]| load.", NamedTextColor.RED));
            return true;
        }
        if (args[0].equalsIgnoreCase("load") && args.length > 1) {
            player.sendMessage(Component.text("Nutze /particleworldborder load.", NamedTextColor.RED));
            return true;
        }
        if (args[0].equalsIgnoreCase("config") && args.length == 2) {
            try {
                GameSettings.getInstance().setParticleBorder(player.getWorld().getName(), player.getLocation(), Double.parseDouble(args[1]));
                player.sendMessage(Component.text("Die Particle World Border wurde erfolgreich gesetzt.", NamedTextColor.GREEN));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                player.sendMessage(Component.text("Es ist ein Fehler beim Setzen der Particle World Border aufgetreten.", NamedTextColor.RED));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("load") && args.length == 1) {
            try {
                String worldName = GameSettings.getInstance().getConfig().getString("particleborder.world");
                World world = Bukkit.getWorld(worldName);
                double centerX = GameSettings.getInstance().getConfig().getDouble("particleborder.centerX");
                double centerY = GameSettings.getInstance().getConfig().getDouble("particleborder.centerY");
                double centerZ = GameSettings.getInstance().getConfig().getDouble("particleborder.centerZ");
                double borderRadius = GameSettings.getInstance().getConfig().getDouble("particleborder.radius");

                Location borderCenter = new Location(world, centerX, centerY, centerZ);

                ParticleWorldBorder.getInstance().startParticleBorder(world, borderRadius, borderCenter);
                player.sendMessage(Component.text("Die Particle World Border wurde erfolgreich geladen.", NamedTextColor.GREEN));
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(Component.text("Es ist ein Fehler beim Laden der Particle World Border aufgetreten."));
            }
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("config");
            suggestions.add("load");
        }
        if (args[0].equalsIgnoreCase("config") && args.length == 2) {
            suggestions.add("radius");
        }
        String currentInput = args[args.length - 1].toLowerCase();
        suggestions.removeIf(s -> !s.toLowerCase().startsWith(currentInput));

        return suggestions;
    }
}