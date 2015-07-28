package com.lcastr0.PrisonScoreboard.events;

import com.lcastr0.PrisonScoreboard.PrisonScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PrisonScoreboardManager.removeScoreboard(player.getUniqueId());
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(!pl.getUniqueId().toString().equals(event.getPlayer().getUniqueId().toString())) {
                PrisonScoreboardManager psm = PrisonScoreboardManager.getManager(pl.getUniqueId());
                psm.updateScoreboard();
            }
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        Player player = event.getPlayer();
        PrisonScoreboardManager.removeScoreboard(player.getUniqueId());
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(!pl.getUniqueId().toString().equals(event.getPlayer().getUniqueId().toString())) {
                PrisonScoreboardManager psm = PrisonScoreboardManager.getManager(pl.getUniqueId());
                psm.updateScoreboard();
            }
        }
    }

}
