package me.levple.cowcannon.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
import java.util.Random;

public class SpawnPlaceCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(Component.text("You must be a player to use this command!", NamedTextColor.RED));
            return true;
        }

        Player player  = (Player) sender;
        if (args.length != 3) {
            player.sendMessage(Component.text("Nutze das Command /setspawnblock <Block> <Radius> <Höhe>", NamedTextColor.RED));
            return true;
        }

        try {

            double x = player.getLocation().getX();
            int y = Integer.parseInt(args[2]);
            double z = player.getLocation().getZ();
            Material blockType = Material.matchMaterial(args[0]);

            Random random = new Random();
            int min = 10;
            int max = Integer.parseInt(args[1]);

            int randomInt = random.nextInt(max - min + 1) + min;

            x += randomInt;
            z += randomInt;


            World world = player.getWorld();
            Location location = new Location(world, x, y, z);
            world.getBlockAt(location).setType(blockType);
            player.sendMessage("Das Objekt wurde an die Location " + location + " versetzt");
        }

        catch (NumberFormatException e) {
            player.sendMessage("Bitte gib eine gültige Zahl für <x> <y> <z> an");
        }
        catch (Exception e) {
            player.sendMessage("Es ist ein Fehler aufgetreten: " + e.getMessage());
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            for (Material material : Material.values()) {
                suggestions.add(material.name().toLowerCase());
            }
        }
        else if (args.length == 2) {
            suggestions.add("Radius");
        }
        else if (args.length == 3) {
            suggestions.add("Höhe");
        }

        String currentInput = args[args.length - 1].toLowerCase();
        suggestions.removeIf(s -> !s.toLowerCase().startsWith(currentInput));

        return suggestions;
    }
}
