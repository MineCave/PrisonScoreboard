package com.lcastr0.PrisonScoreboard.helper;

import com.lcastr0.PrisonScoreboard.PrisonScoreboard;
import com.lcastr0.PrisonScoreboard.obj.Prefix;
import com.lcastr0.PrisonScoreboard.obj.Rank;
import com.lcastr0.PrisonScoreboard.obj.Suffix;

import java.util.ArrayList;
import java.util.List;

public class ConfigHelper {

    private static PrisonScoreboard instance = PrisonScoreboard.getInstance();
    private static List<Rank> ranks = new ArrayList<>();
    private static ScoreboardHelper scoreboardHelper;

    public static List<Rank> getRanks(){
        if(ranks.size() != 0)
            return ranks;
        int aRank = instance.getConfig().getInt("max-rank-value");
        for(String rank : instance.getConfig().getStringList("ranks")){
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
                newRank = new Rank(name, money, aRank);
            }
            ranks.add(newRank);
            aRank--;
        }
        return ranks;
    }

    public static Rank getNext(Rank rank){
        int rankInt = rank.getRank();
        int nextRank = rankInt - 1;
        Rank nextNewRank = null;
        for(Rank r : ranks){
            if(r.getRank() == nextRank)
                return r;
        }
        return nextNewRank;
    }

    public static Rank getRank(int rank){
        Rank newRank = null;
        for(Rank r : ranks){
            if(r.getRank() == rank)
                newRank = r;
        }
        return newRank;
    }

    public static String getLastRankString(){
        return instance.getConfig().getString("maxRankScore");
    }

    public static ScoreboardHelper getScoreboard(){
        if(scoreboardHelper != null)
            return scoreboardHelper;
        String title = instance.getConfig().getString("scoreboard.title.text");
        scoreboardHelper = new ScoreboardHelper(title);
        return scoreboardHelper;
    }

    public static int getDropPartyVotes(){
        return instance.getConfig().getInt("dropPartyVotes");
    }

    public static boolean titleScroll(){
        return instance.getConfig().getBoolean("scoreboard.title.scroll.canScroll");
    }

    public static String[] getTitleConfig(){
        String[] s = {instance.getConfig().getString("scoreboard.title.scroll.scrollColors.text"), instance.getConfig().getString("scoreboard.title.scroll.scrollColors.before"),
                instance.getConfig().getString("scoreboard.title.scroll.scrollColors.during"), instance.getConfig().getString("scoreboard.title.scroll.scrollColors.after"),
                String.valueOf(instance.getConfig().getString("scoreboard.title.scroll.scrollType"))};
        return s;
    }

    public static String[] getProgressBarConfig(){
        String[] s = {instance.getConfig().getString("scoreboard.progressBar.colors.currentRank"), instance.getConfig().getString("scoreboard.progressBar.colors.nextRank"),
                instance.getConfig().getString("scoreboard.progressBar.colors.nextRankReached"), instance.getConfig().getString("scoreboard.progressBar.colors.barReached"),
                instance.getConfig().getString("scoreboard.progressBar.colors.barNotReached"), instance.getConfig().getString("scoreboard.progressBar.character"),
                String.valueOf(instance.getConfig().getString("scoreboard.progressBar.size"))};
        return s;
    }

    public static boolean useDefaultWorld(){
        return instance.getConfig().getBoolean("scoreboard.kills.useDefaultWorld");
    }

    public static String getDefaultWorld(){
        return instance.getConfig().getString("scoreboard.kills.defaultWorld");
    }

    public static void getTags(){
        for(String prefix : instance.getConfig().getStringList("prefixes.list")){
            String confPath = "prefixes." + prefix + ".";
            new Prefix(instance.getConfig().getString(confPath + "tag"), instance.getConfig().getString(confPath + "permission"), prefix);
        }
        for(String suffix : instance.getConfig().getStringList("suffixes.list")){
            String confPath = "suffixes." + suffix + ".";
            new Suffix(instance.getConfig().getString(confPath + "tag"), instance.getConfig().getString(confPath + "permission"), suffix);
        }
    }

}
