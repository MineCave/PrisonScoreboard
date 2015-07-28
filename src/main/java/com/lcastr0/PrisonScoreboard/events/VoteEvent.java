package com.lcastr0.PrisonScoreboard.events;

import com.lcastr0.PrisonScoreboard.PrisonScoreboard;
import com.lcastr0.PrisonScoreboard.PrisonScoreboardManager;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VoteEvent implements Listener {

    @EventHandler
    public void onVote(VotifierEvent event){
        PrisonScoreboard.getInstance().receiver.increaseVotes();
        for(Player pl : Bukkit.getOnlinePlayers()){
            PrisonScoreboardManager psm = PrisonScoreboardManager.getManager(pl.getUniqueId());
            psm.updateScoreboard();
        }
    }

}
