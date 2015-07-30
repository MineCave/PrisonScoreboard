package com.lcastr0.prisonscoreboard.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lcastr0.prisonscoreboard.managers.ObjectManager;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScoreboardCreator {

    private final Scoreboard scoreboard;
    private final String title;
    private final Map<String, Integer> lines = Maps.newLinkedHashMap();
    private final List<String> remove = Lists.newArrayList();
    private Objective objective;

    public ScoreboardCreator(String title){
        this.scoreboard = ObjectManager.getScoreboard();
        this.title = title;
    }

    public void blank(int score){
        add(fixString(" "), score);
    }

    public void add(String text, Integer score){
        lines.put(text, score);
    }

    public void add(String oldText, String text, Integer score){
        this.remove.add(oldText);
        lines.remove(oldText);
        lines.put(text, score);
    }

    public String fixString(String text){
        while (this.lines.containsKey(text))
            text += ChatColor.RESET;
        return text;
    }

    public void remove(String text){
        if(lines.containsKey(text))
            lines.remove(text);
    }

    public Map<String, Integer> getLines(){
        return this.lines;
    }

    public void build(){
        if(this.objective == null){
            UUID objectiveId = UUID.randomUUID();
            String id = StringUtils.replace(objectiveId.toString(), "-", "").substring(0, 15);
            this.objective = this.scoreboard.registerNewObjective(id, "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.title));
        }
        int i = this.lines.size();
        for(Map.Entry<String, Integer> entry : this.lines.entrySet()){
            Integer score = (entry.getValue() == null) ? i : entry.getValue();
            String line = entry.getKey();
            if(line.length() > 40)
                line = line.substring(0, 39);
            this.objective.getScore(line).setScore(score);
            i--;
        }
        for(String r : remove){
            if(r.length() > 40)
                r = r.substring(0, 39);
            this.scoreboard.resetScores(r);
        }
        this.remove.clear();
    }

    public void send(Player... players){
        for(Player player : players){
            player.setScoreboard(this.scoreboard);
        }
    }

    public void removePlayer(Player player){
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public void update(){
        this.build();
    }

}
