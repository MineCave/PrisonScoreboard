package com.lcastr0.prisonscoreboard.managers;

import com.lcastr0.prisonscoreboard.PrisonScoreboard;
import com.lcastr0.prisonscoreboard.objects.Rank;

import java.util.List;

public class ConfigManager {

    private final PrisonScoreboard instance = PrisonScoreboard.getInstance();

    private String title, world, maxRankScore;
    private String[] progressBar;
    private List<String> lines;
    private List<Rank> ranks;
    private boolean defaultWorld;
    private int votes;

    public ConfigManager(){
        this.title = instance.getConfig("config").get("title", String.class);
        this.world = instance.getConfig("config").get("world.name", String.class);
        this.maxRankScore = instance.getConfig("config").get("maxRankScore", String.class);
        this.lines = instance.getConfig("lines").getConfig().getStringList("lines");
        this.defaultWorld = instance.getConfig("config").get("world.useDefault", Boolean.class);
        this.votes = instance.getConfig("config").get("votes", Integer.class);
        this.setProgressBar();
        this.setRanks();
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
        int rankValue = instance.getConfig("rankConfigs").get("max-rank-value", Integer.class);
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
            this.ranks.add(newRank);
            rankValue--;
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

    public String[] getProgressBar(){
        return this.progressBar;
    }

    public List<String> getLines(){
        return this.lines;
    }

    public List<Rank> getRanks(){
        return this.ranks;
    }

    public boolean isDefaultWorld(){
        return this.defaultWorld;
    }

    public int getVotes(){
        return this.votes;
    }

}
