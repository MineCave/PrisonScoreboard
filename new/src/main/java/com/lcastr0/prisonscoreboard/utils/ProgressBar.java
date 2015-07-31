package com.lcastr0.prisonscoreboard.utils;

import net.md_5.bungee.api.ChatColor;

public class ProgressBar {

    private final int bars;
    private final ChatColor colorDone, colorNotDone;
    private final String character;

    private long max = 0, current = 0;
    private boolean complete = false;

    public ProgressBar(int bars, ChatColor colorDone, ChatColor colorNotDone, String character){
        this.bars = bars;
        this.colorDone = colorDone;
        this.colorNotDone = colorNotDone;
        this.character = character;
    }

    public void setMax(double max){
        this.max = (long) max;
    }

    public void setCurrent(double current){
        this.current = (long) current;
    }

    public String getProgressBar(){

        StringBuilder builder = new StringBuilder();

        if(this.current >= this.max){
            builder.append(colorDone);
            for(int i = 0; i < this.bars; i++){
                builder.append(this.character);
            }
            this.complete = true;
            return builder.toString();
        }

        this.complete = false;

        long a = (100 * this.current) / this.max;

        long b = (this.bars * a) / 100;

        if(b >= 1){
            builder.append(colorDone);
            for(int i = 1; i <= b; i++){
                builder.append(this.character);
            }
        }

        builder.append(colorNotDone);
        for(long l = b++; l < this.bars; l++){
            builder.append(this.character);
        }

        return builder.toString();

    }

    public boolean isComplete(){
        return this.complete;
    }

}
