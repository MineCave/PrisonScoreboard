package com.lcastr0.PrisonScoreboard.helper;

import org.bukkit.ChatColor;

public class ProgressBar {

    public long max, actual;
    public int bars;
    public ChatColor colorDone, colorNotDone;
    public String character;
    public boolean isComplete = false;

    public ProgressBar(long max, long actual, int bars, ChatColor colorDone, ChatColor colorNotDone, String character){
        this.max = max;
        this.actual = actual;
        this.bars = bars;
        this.colorDone = colorDone;
        this.colorNotDone = colorNotDone;
        this.character = character;
    }

    public void setMax(long max){
        this.max = max;
    }

    public void setActual(long actual){
        this.actual = actual;
    }

    public void setBars(int bars){
        this.bars = bars;
    }

    public void setColorDone(ChatColor colorDone){
        this.colorDone = colorDone;
    }

    public void setColorNotDone(ChatColor colorNotDone){
        this.colorNotDone = colorNotDone;
    }

    public String getProgressBar(){
        StringBuilder builder = new StringBuilder();
        if(this.actual >= this.max){
            builder.append(this.colorDone);
            for(int i = 0; i < this.bars; i++)
                builder.append(this.character);
            this.isComplete = true;
            return builder.toString();
        }
        this.isComplete = false;
        long a = (100 * this.actual) / this.max;
        long b = (this.bars * a) / 100;
        builder.append(this.colorDone);
        for(int i = 1; i <= b; i++){
            builder.append(this.character);
        }
        builder.append(this.colorNotDone);
        for(long i = b++; i < this.bars; i++){
            builder.append(this.character);
        }
        return builder.toString();
    }

    public boolean isComplete(){
        return this.isComplete;
    }

}
