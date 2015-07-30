package com.lcastr0.prisonscoreboard;

import com.lcastr0.prisonscoreboard.utils.CustomConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class PrisonScoreboard extends JavaPlugin {

    private static PrisonScoreboard instance = null;

    private CustomConfig config, lines, ranks, rankConfigs, prefixes;
    private Map<String, CustomConfig> configs = new HashMap<>();

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
        rankConfigs = new CustomConfig(getDataFolder(), "rankConfigs.yml");
        configs.put("rankConfigs", rankConfigs);
        prefixes = new CustomConfig(getDataFolder(), "prefixes.yml");
        configs.put("prefixes", prefixes);

    }

    public CustomConfig getConfig(String name){
        return (configs.containsKey(name)) ? configs.get(name) : null;
    }

    public static PrisonScoreboard getInstance(){
        return instance;
    }

}
