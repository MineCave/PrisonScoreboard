package com.lcastr0.PrisonScoreboard.helper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class ScoreboardCreator {

    public static Map<String, ScoreboardCreator> scoreboards = new HashMap<>();

    public Scoreboard scoreboard;
    public Objective objective;
    public String title;
    public Map<String, Integer> lines = Maps.newLinkedHashMap();
    public List<String> remove = Lists.newArrayList();

    public ScoreboardCreator(String title){
        this.title = title;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboards.put(title, this);
    }

    public void blank(int score){
        add(fixString(" "), score);
    }

    public void add(String text, Integer score){
        lines.put(text, score);
    }

    public void add(String oldText, String text, Integer score, Player player){
        this.remove.add(oldText);
        lines.remove(oldText);
        lines.put(text, score);
        player.setScoreboard(this.scoreboard);
    }

    public String fixString(String text){
        while (this.lines.containsKey(text))
            text += "§r";
        return text;
    }

    public void remove(String text){
        if(lines.containsKey(text))
            lines.remove(text);
    }

    public void setTitle(String title){
        this.title = title;
        this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
    }

    public Map<String, Integer> getLines(){
        return this.lines;
    }

    public void build(){
        if(this.objective == null) {
            this.objective = this.scoreboard.registerNewObjective((this.title.length() > 16 ? this.title.substring(0, 15) : this.title), "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.title));
        }
        int i = this.lines.size();
        for(Map.Entry<String, Integer> entry : this.lines.entrySet()) {
            Integer score = (entry.getValue() == null) ? i : entry.getValue();
            String line = entry.getKey();
            if(entry.getKey().length() > 40)
                line = entry.getKey().substring(0, 39);
            this.objective.getScore(line).setScore(score);
            i--;
        }
        for(String r : remove){
            if(r.length() > 40)
                r = r.substring(0, 39);
            this.scoreboard.resetScores(r);
        }
        remove.clear();
    }

    public void send(Player... p){
        for(Player player : p) {
            player.setScoreboard(this.scoreboard);
        }
    }

    public void removePlayer(Player player){
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public void update(Player... p){
        this.build();
    }

    public static ScoreboardCreator getScoreboard(String title){
        if(scoreboards.containsKey((title.length() > 16 ? title.substring(0, 15) : title))){
            return scoreboards.get((title.length() > 16 ? title.substring(0, 15) : title));
        }
        return null;
    }

}
