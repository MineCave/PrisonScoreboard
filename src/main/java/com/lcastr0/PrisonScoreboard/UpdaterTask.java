package com.lcastr0.PrisonScoreboard;

import org.bukkit.Bukkit;

public class UpdaterTask implements Runnable {

    private int s = -1;

    public void start(){
        this.s = Bukkit.getScheduler().scheduleSyncRepeatingTask(PrisonScoreboard.getInstance(), this, 10 * 20L, 10 * 20L);
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(this.s);
    }

    @Override
    public void run(){
        PrisonScoreboardManager.updateAll();
    }

}
