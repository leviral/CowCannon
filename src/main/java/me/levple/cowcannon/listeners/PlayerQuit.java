package me.levple.cowcannon.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player quitedPlayer = event.getPlayer();
        event.quitMessage(Component.text(""));
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.sendMessage(Component.text(quitedPlayer.getName() + " hat den Server verlassen.", NamedTextColor.GOLD));
        }
    }
}
