package com.lcastr0.PrisonScoreboard.helper;

import com.lcastr0.PrisonScoreboard.PrisonScoreboard;
import org.bukkit.ChatColor;

public class ScoreboardHelper {

    public final String title;

    public ScoreboardHelper(String title){
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.copyLines();
    }

    public void copyLines(){
        PrisonScoreboard instance = PrisonScoreboard.getInstance();
    }

    public String getTitle(){
        return this.title;
    }

}
