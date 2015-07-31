package com.lcastr0.prisonscoreboard.managers;

import com.lcastr0.prisonscoreboard.PrisonScoreboard;
import com.lcastr0.prisonscoreboard.objects.Prefix;
import com.lcastr0.prisonscoreboard.objects.Rank;
import com.lcastr0.prisonscoreboard.objects.Suffix;
import com.lcastr0.prisonscoreboard.utils.CustomConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private final PrisonScoreboard instance = PrisonScoreboard.getInstance();

    private String title, world, maxRankScore, customText;
    private String[] progressBar;
    private List<String> lines;
    private Map<Integer, Rank> ranks = new HashMap<>();
    private boolean defaultWorld, customTextEnable;
    private int maxRank;

    public ConfigManager(){
        this.title = instance.getConfig("config").get("title", String.class);
        this.world = instance.getConfig("config").get("world.name", String.class);
        this.maxRankScore = instance.getConfig("config").get("maxRankScore", String.class);
        this.customText = instance.getConfig("config").get("customText", String.class);
        this.lines = instance.getConfig("lines").getConfig().getStringList("lines");
        this.defaultWorld = instance.getConfig("config").get("world.useDefaultWorld", Boolean.class);
        this.customTextEnable = instance.getConfig("config").get("customTextEnabled", Boolean.class);
        this.maxRank = instance.getConfig("config").get("max-rank-value", Integer.class);
        this.setProgressBar();
        this.setRanks();
        this.setPrefixes();
        this.setSuffixes();
    }

    private void setProgressBar(){
        String currentRank = instance.getConfig("config").get("progressBar.colors.currentRank", String.class);
        String nextRank = instance.getConfig("config").get("progressBar.colors.nextRank", String.class);
        String nextRankReached = instance.getConfig("config").get("progressBar.colors.nextRankReached", String.class);
        String barReached = instance.getConfig("config").get("progressBar.colors.barReached", String.class);
        String barNotReached = instance.getConfig("config").get("progressBar.colors.barNotReached", String.class);
        String character = instance.getConfig("config").get("progressBar.character", String.class);
        String size = String.valueOf(instance.getConfig("config").get("progressBar.size", Integer.class));
        this.progressBar = new String[] {currentRank, nextRank, nextRankReached, barReached, barNotReached, character, size};
    }

    private void setRanks(){
        int rankValue = this.maxRank;
        List<String> rankList = instance.getConfig("ranks").getConfig().getStringList("ranks");
        for(String rank : rankList){
            String name = rank.substring(0, rank.indexOf(':'));
            Rank newRank;
            long money = Long.valueOf(rank.substring(rank.indexOf(":") + 1));
            if(name.equalsIgnoreCase("OWNER")){
                newRank = new Rank(name, money, 1);
            } else if(name.equalsIgnoreCase("CO-OWNER")){
                newRank = new Rank(name, money, 2);
            } else if(name.equalsIgnoreCase("DEV")){
                newRank = new Rank(name, money, 3);
            } else if(name.equalsIgnoreCase("MOD")){
                newRank = new Rank(name, money, 4);
            } else {
                newRank = new Rank(name, money, rankValue);
            }
            this.ranks.put(newRank.getRank(), newRank);
            rankValue--;
        }
    }

    private void setPrefixes(){
        CustomConfig prefixes = instance.getConfig("prefixes");
        for(String prefix : prefixes.getConfig().getStringList("prefixes.list")){
            String confPath = "prefixes." + prefix + ".";
            String tag = prefixes.get(confPath + "tag", String.class);
            String permission = prefixes.get(confPath + "permission", String.class);
            Prefix pre = new Prefix(tag, permission, prefix);
            ObjectManager.addPrefix(pre);
        }
    }

    private void setSuffixes(){
        CustomConfig prefixes = instance.getConfig("prefixes");
        for(String suffix : prefixes.getConfig().getStringList("suffixes.list")){
            String confPath = "suffixes." + suffix + ".";
            String tag = prefixes.get(confPath + "tag", String.class);
            String permission = prefixes.get(confPath + "permission", String.class);
            Suffix su = new Suffix(tag, permission, suffix);
            ObjectManager.addSuffix(su);
        }
    }

    public String getTitle(){
        return this.title;
    }

    public String getWorld(){
        return this.world;
    }

    public String getMaxRankScore(){
        return this.maxRankScore;
    }

    public String getCustomText(){
        return this.customText;
    }

    public String[] getProgressBar(){
        return this.progressBar;
    }

    public List<String> getLines(){
        return this.lines;
    }

    public Map<Integer, Rank> getRanks(){
        return this.ranks;
    }

    public Rank getRank(Integer rank){
        return this.ranks.get(rank);
    }

    public Rank getNextRank(Integer rank){
        return this.getRank(rank - 1);
    }

    public boolean isDefaultWorld(){
        return this.defaultWorld;
    }

    public boolean isCustomTextEnable(){
        return this.customTextEnable;
    }

    public int getMaxRank(){
        return this.maxRank;
    }

}
