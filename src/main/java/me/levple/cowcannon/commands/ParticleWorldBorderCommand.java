package me.levple.cowcannon.commands;

import me.levple.cowcannon.gui.GameSettings;
import me.levple.cowcannon.gui.ParticleWorldBorder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
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
        if (args.length == 0 || args.length > 4 || (args[0].equalsIgnoreCase("config")) && args.length < 4) {
            player.sendMessage(Component.text("Nutze /particleworldborder config [radius] [particleType] [particleCount] | load | remove.", NamedTextColor.RED));
            return true;
        }
        if (args[0].equalsIgnoreCase("load") && args.length > 1) {
            player.sendMessage(Component.text("Nutze /particleworldborder load.", NamedTextColor.RED));
            return true;
        }
        if (args[0].equalsIgnoreCase("remove") && args.length > 1) {
            player.sendMessage(Component.text("Nutze /particleworldborder remove.", NamedTextColor.RED));
            return true;
        }
        if (args[0].equalsIgnoreCase("config") && args.length == 4) {
            try {
                GameSettings.getInstance().setParticleBorder(player.getWorld().getName(), player.getLocation(), Double.parseDouble(args[1]), args[2], Integer.valueOf(args[3]));
                player.sendMessage(Component.text("Die Particle World Border wurde erfolgreich gesetzt.", NamedTextColor.GREEN));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                player.sendMessage(Component.text("Es ist ein Fehler beim Setzen der Particle World Border aufgetreten.", NamedTextColor.RED));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("load") && args.length == 1) {
            try {
                if (ParticleWorldBorder.getInstance().getTask() != null)
                    ParticleWorldBorder.getInstance().getTask().cancel();
                String worldName = GameSettings.getInstance().getConfig().getString("particleBorder.world");
                World world = Bukkit.getWorld(worldName);
                double centerX = GameSettings.getInstance().getConfig().getDouble("particleBorder.centerX");
                double centerY = GameSettings.getInstance().getConfig().getDouble("particleBorder.centerY");
                double centerZ = GameSettings.getInstance().getConfig().getDouble("particleBorder.centerZ");
                double borderRadius = GameSettings.getInstance().getConfig().getDouble("particleBorder.radius");
                Particle particleType = Particle.valueOf(GameSettings.getInstance().getConfig().getString("particleBorder.particleType"));
                int particleCount = GameSettings.getInstance().getConfig().getInt("particleBorder.particleCount");

                Location borderCenter = new Location(world, centerX, centerY, centerZ);

                ParticleWorldBorder.getInstance().startParticleBorder(world, borderRadius, borderCenter, particleType, centerX, centerZ, particleCount);
                player.sendMessage(Component.text("Die Particle World Border wurde erfolgreich geladen.", NamedTextColor.GREEN));
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(Component.text("Es ist ein Fehler beim Laden der Particle World Border aufgetreten.", NamedTextColor.RED));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("remove") && args.length == 1) {
            try {
                ParticleWorldBorder.getInstance().getTask().cancel();
                GameSettings.getInstance().removeParticleBorder();
                player.sendMessage(Component.text("Die Particle World Border wurde erfolgreich gelöscht.", NamedTextColor.GREEN));
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(Component.text("Es ist ein Fehler beim Entfernen der Particle World Border aufgetreten.", NamedTextColor.RED));
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
            suggestions.add("remove");
        }
        if (args[0].equalsIgnoreCase("config")) {
            if (args.length == 2) {
                suggestions.add("radius");
            }
            if (args.length == 3) {
                for (Particle particleType : Particle.values()) {
                    suggestions.add(particleType.name().toLowerCase());
                }
            }
            if (args.length == 4) {
                suggestions.add("particleCount");
            }
        }
        String currentInput = args[args.length - 1].toLowerCase();
        suggestions.removeIf(s -> !s.toLowerCase().startsWith(currentInput));

        return suggestions;
    }
}