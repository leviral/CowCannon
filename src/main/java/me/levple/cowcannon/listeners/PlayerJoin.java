package me.levple.cowcannon.listeners;

import me.levple.cowcannon.gui.JoinCounter;
import me.levple.cowcannon.gui.ScoreboardTab;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
            event.joinMessage(Component.text(""));
            event.getPlayer().sendMessage(Component.text("Herzlich Willkommen " + event.getPlayer().getName() + " auf Levple's Testserver!", NamedTextColor.GOLD));
            JoinCounter.getInstance().set(event.getPlayer());
            int joinCounter = JoinCounter.getInstance().get(event.getPlayer());
            ScoreboardTab.getInstance().setTabCounter(event.getPlayer(), joinCounter, NamedTextColor.GOLD, NamedTextColor.DARK_PURPLE);
    }
}
