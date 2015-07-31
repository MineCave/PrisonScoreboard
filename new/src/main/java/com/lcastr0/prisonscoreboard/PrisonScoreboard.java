package com.lcastr0.prisonscoreboard;

import com.lcastr0.prisonscoreboard.commands.VoteCommand;
import com.lcastr0.prisonscoreboard.listeners.PlayerListeners;
import com.lcastr0.prisonscoreboard.managers.ObjectManager;
import com.lcastr0.prisonscoreboard.managers.ScoreboardManager;
import com.lcastr0.prisonscoreboard.objects.Vote;
import com.lcastr0.prisonscoreboard.utils.CustomConfig;
import com.lcastr0.prisonscoreboard.utils.ScoreboardCreator;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class PrisonScoreboard extends JavaPlugin {

    private static PrisonScoreboard instance = null;

    private CustomConfig config, lines, ranks, prefixes;
    private Map<String, CustomConfig> configs = new HashMap<>();

    private Economy econ;
    private Vote receiver;

    // TODO SEND TO ONLINE PLAYERS AND REMOVE ONDISABLE

    @Override
    public void onEnable(){

        instance = this;

        // Loads configs
        config = new CustomConfig(getDataFolder(), "config.yml");
        configs.put("config", config);
        lines = new CustomConfig(getDataFolder(), "lines.yml");
        configs.put("lines", lines);
        ranks = new CustomConfig(getDataFolder(), "ranks.yml");
        configs.put("ranks", ranks);
        prefixes = new CustomConfig(getDataFolder(), "prefixes.yml");
        configs.put("prefixes", prefixes);

        // ECON
        setupEcon();

        // VOTES
        this.receiver = new Vote(config.get("votes", Integer.class), config.get("maxVotes", Integer.class));

        // COMMAND
        this.getCommand("psvote").setExecutor(new VoteCommand());

        // EVENT
        this.getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        // SENDS SCOREBOARD TO ONLINE PLAYERS
        for(Player player : Bukkit.getOnlinePlayers()){
            ScoreboardManager manager = ObjectManager.getScoreboardManager(player.getUniqueId());
            manager.sendScoreboard();
        }

    }

    @Override
    public void onDisable(){
        this.config.set("votes", this.receiver.getVotes());
        this.config.saveConfig();
        for(Player player : Bukkit.getOnlinePlayers()){
            ScoreboardCreator.removePlayer(player);
        }
    }

    public CustomConfig getConfig(String name){
        return (configs.containsKey(name)) ? configs.get(name) : null;
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

    public Vote getReceiver(){
        return this.receiver;
    }

    public static PrisonScoreboard getInstance(){
        return instance;
    }

}
