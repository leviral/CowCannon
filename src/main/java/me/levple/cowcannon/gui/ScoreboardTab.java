package me.levple.cowcannon.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardTab {

    private final static ScoreboardTab instance = new ScoreboardTab();

    private ScoreboardTab() {
    }

    public void setTabCounter(Player player, Integer joinCounter, NamedTextColor colorPlayer, NamedTextColor colorCounter) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(player.getName());

        if (team == null) {
            team = scoreboard.registerNewTeam(player.getName());
        }
        team.color(colorPlayer);
        team.suffix(Component.text(" " + joinCounter, colorCounter));
        team.addEntry(player.getName());
    }

    public static ScoreboardTab getInstance() {
        return instance;
    }

}
