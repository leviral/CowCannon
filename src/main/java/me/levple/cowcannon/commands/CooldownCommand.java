package me.levple.cowcannon.commands;

import me.levple.cowcannon.CowCannon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CooldownCommand implements CommandExecutor {

    CowCannon plugin = CowCannon.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;
        for (int i = 0; i<=3; i++) {
            int finalI = 3 - i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (finalI > 0) {
                    player.sendActionBar(Component.text(finalI, NamedTextColor.GOLD));
                }
                else {
                    player.sendActionBar(Component.text(plugin.getConfig().getString("cooldown.txt"), NamedTextColor.GOLD));
                }
            }, 20 * i);

        }

        return true;
    }
}
