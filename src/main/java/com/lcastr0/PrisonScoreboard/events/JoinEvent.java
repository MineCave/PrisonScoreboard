package com.lcastr0.PrisonScoreboard.events;

import com.lcastr0.PrisonScoreboard.PrisonScoreboard;
import com.lcastr0.PrisonScoreboard.PrisonScoreboardManager;
import com.lcastr0.PrisonScoreboard.helper.PlayerTag;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event){
        Bukkit.getScheduler().scheduleSyncDelayedTask(PrisonScoreboard.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    String prefix = PlayerTag.getPrefix(player), suffix = PlayerTag.getSuffix(player);
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        PrisonScoreboardManager psm = PrisonScoreboardManager.getManager(pl.getUniqueId());
                        psm.updateScoreboard();
                        Scoreboard score = psm.scoreboardCreator.scoreboard;
                        Team team = score.getTeam(player.getUniqueId().toString().substring(0, 15));
                        if (team != null) {
                            if (!prefix.equals("NONE"))
                                team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
                            if (!suffix.equals("NONE"))
                                team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
                            team.addPlayer(player);
                        } else {
                            team = score.registerNewTeam(player.getUniqueId().toString().substring(0, 15));
                            if (!prefix.equals("NONE"))
                                team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
                            if (!suffix.equals("NONE"))
                                team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
                            team.addPlayer(player);
                        }
                    }
                }
            }
        }, 40L);
    }

}
