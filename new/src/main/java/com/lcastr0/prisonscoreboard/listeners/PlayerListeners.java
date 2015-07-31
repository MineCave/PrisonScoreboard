package com.lcastr0.prisonscoreboard.listeners;

import com.lcastr0.prisonscoreboard.PrisonScoreboard;
import com.lcastr0.prisonscoreboard.events.VoteChangeEvent;
import com.lcastr0.prisonscoreboard.managers.ObjectManager;
import com.lcastr0.prisonscoreboard.managers.ScoreboardManager;
import com.lcastr0.prisonscoreboard.utils.PlayerUtils;
import com.lcastr0.prisonscoreboard.utils.ScoreboardCreator;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.events.PermissionEntityEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        for(Player player : Bukkit.getOnlinePlayers()){
            String prefix = PlayerUtils.getPrefix(player);
            String suffix = PlayerUtils.getSuffix(player);
            ScoreboardManager manager = ObjectManager.getScoreboardManager(player.getUniqueId());
            if(manager != null){
                manager.sendScoreboard();
                Scoreboard scoreboard = manager.getScoreboard();
                Team team = scoreboard.getTeam(player.getUniqueId().toString().substring(0, 15));
                if(team != null){
                    team.setPrefix(prefix);
                    team.setSuffix(suffix);
                    team.addPlayer(player);
                } else {
                    team = scoreboard.registerNewTeam(player.getUniqueId().toString().substring(0, 15));
                    team.setPrefix(prefix);
                    team.setSuffix(suffix);
                    team.addPlayer(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        ScoreboardCreator.removePlayer(player);
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(!pl.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                ScoreboardManager manager = ObjectManager.getScoreboardManager(pl.getUniqueId());
                manager.sendScoreboard();
            }
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        Player player = event.getPlayer();
        ScoreboardCreator.removePlayer(player);
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(!pl.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                ScoreboardManager manager = ObjectManager.getScoreboardManager(pl.getUniqueId());
                manager.sendScoreboard();
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        for(Player pl : Bukkit.getOnlinePlayers()){
            ScoreboardManager manager = ObjectManager.getScoreboardManager(pl.getUniqueId());
            manager.sendScoreboard();
        }
    }

    @EventHandler
    public void onPermissionEvent(PermissionEntityEvent event){
        for(Player pl : Bukkit.getOnlinePlayers()){
            ScoreboardManager manager = ObjectManager.getScoreboardManager(pl.getUniqueId());
            manager.sendScoreboard();
        }
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event){
        if(event.getMessage().startsWith("/money")) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                ScoreboardManager manager = ObjectManager.getScoreboardManager(pl.getUniqueId());
                manager.sendScoreboard();
            }
        }
    }

    @EventHandler
    public void onVote(VotifierEvent event){
        PrisonScoreboard.getInstance().getReceiver().increaseVotes();
        for(Player pl : Bukkit.getOnlinePlayers()){
            ScoreboardManager manager = ObjectManager.getScoreboardManager(pl.getUniqueId());
            manager.sendScoreboard();
        }
    }

    @EventHandler
    public void onVoteChange(VoteChangeEvent event){
        for(Player pl : Bukkit.getOnlinePlayers()){
            ScoreboardManager manager = ObjectManager.getScoreboardManager(pl.getUniqueId());
            manager.sendScoreboard();
        }
    }

}
