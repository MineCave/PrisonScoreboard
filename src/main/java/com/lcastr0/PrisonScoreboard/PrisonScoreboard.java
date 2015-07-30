package com.lcastr0.PrisonScoreboard;

import com.lcastr0.PrisonScoreboard.events.*;
import com.lcastr0.PrisonScoreboard.helper.ConfigHelper;
import com.lcastr0.PrisonScoreboard.helper.PlayerTag;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrisonScoreboard extends JavaPlugin {

    public static PrisonScoreboard instance;

    public Economy econ;
    public VoteReceiver receiver;

    private List<String> linesList = new ArrayList<>();

    @Override
    public void onEnable(){
        // TODO
        instance = this;
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        this.receiver = new VoteReceiver(this.getConfig().getInt("votes"), this.getConfig().getInt("dropPartyVotes"));
        this.getServer().getPluginManager().registerEvents(new VoteEvent(), this);
        this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        this.getCommand("psvote").setExecutor(new VoteCommand());
        this.linesList = this.getConfig().getStringList("scoreboard.lines");
        setupEcon();
        ConfigHelper.setConfigValues();
        ConfigHelper.getRanks();
        ConfigHelper.getScoreboard();
        ConfigHelper.getTags();
        PrisonScoreboardManager.setVariables();
        updateScoreboard();
    }

    @Override
    public void onDisable(){
        for(Map.Entry entry : PrisonScoreboardManager.managers.entrySet()){
            PrisonScoreboardManager psm = (PrisonScoreboardManager) entry.getValue();
            psm.removeScoreboard();
        }
        this.reloadConfig();
        this.getConfig().set("votes", this.receiver.getVotes());
        this.saveConfig();
    }

    public List<String> getLinesList(){
        return this.linesList;
    }

    private void updateScoreboard(){
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
            @Override
            public void run(){
                for(Player player : Bukkit.getOnlinePlayers()){
                    PrisonScoreboardManager ps = PrisonScoreboardManager.getManager(player.getUniqueId());
                    ps.updateScoreboard();
                    String prefix = PlayerTag.getPrefix(player), suffix = PlayerTag.getSuffix(player);
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        PrisonScoreboardManager psm = PrisonScoreboardManager.getManager(pl.getUniqueId());
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

    private void setupEcon(){
        if(getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if(rsp != null)
                this.econ = rsp.getProvider();
        }
    }

    public Economy getEcon(){
        return this.econ;
    }

    public static PrisonScoreboard getInstance(){
        return instance;
    }

}
