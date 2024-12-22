package me.levple.cowcannon.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(20);
            Component gradientText = Component.text()
                    .append(Component.text("Du ").color(TextColor.fromHexString("#FFFF00")))
                    .append(Component.text("wurd").color(TextColor.fromHexString("#FFCC00")))
                    .append(Component.text("est ").color(TextColor.fromHexString("#FF9900")))
                    .append(Component.text("geh").color(TextColor.fromHexString("#FF6600")))
                    .append(Component.text("eilt!").color(TextColor.fromHexString("#FF3300")))
                    .build();
            player.sendMessage(gradientText);
            return true;
        } else {
            sender.sendMessage(Component.text("Du hast keine Spieler vorhanden!").color(TextColor.fromHexString("#d5144f")));
        }
        return false;
    }
}
