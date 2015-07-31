package com.lcastr0.prisonscoreboard.managers;

import com.lcastr0.prisonscoreboard.objects.Prefix;
import com.lcastr0.prisonscoreboard.objects.Rank;
import com.lcastr0.prisonscoreboard.objects.Suffix;
import java.util.*;

public class ObjectManager {

    private static Map<UUID, ScoreboardManager> scoreboardManagers = new HashMap<>();
    private static List<Rank> ranks = new ArrayList<>();
    private static List<Prefix> prefixes = new ArrayList<>();
    private static List<Suffix> suffixes = new ArrayList<>();
    private static ConfigManager manager = new ConfigManager();

    public static void addManager(UUID uuid, ScoreboardManager scoreboardManager){
        scoreboardManagers.put(uuid, scoreboardManager);
    }

    public static void removeManager(UUID uuid){
        scoreboardManagers.remove(uuid);
    }

    public static void addRank(Rank rank){
        ranks.add(rank);
    }

    public static void addPrefix(Prefix prefix){
        prefixes.add(prefix);
    }

    public static void addSuffix(Suffix suffix){
        suffixes.add(suffix);
    }

    public static Map<UUID, ScoreboardManager> getScoreboardManagers(){
        return scoreboardManagers;
    }

    public static Set<UUID> getScoreboardManagerKeys(){
        return scoreboardManagers.keySet();
    }

    public static ScoreboardManager getScoreboardManager(UUID uuid){
        if(scoreboardManagers.containsKey(uuid))
            return scoreboardManagers.get(uuid);
        ScoreboardManager manager = new ScoreboardManager(uuid);
        scoreboardManagers.put(uuid, manager);
        return manager;
    }

    public static List<Rank> getRanks(){
        return ranks;
    }

    public static List<Prefix> getPrefixes(){
        return prefixes;
    }

    public static List<Suffix> getSuffixes(){
        return suffixes;
    }

    public static ConfigManager getManager(){
        return manager;
    }

}
