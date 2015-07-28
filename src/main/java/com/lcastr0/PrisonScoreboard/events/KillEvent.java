package com.lcastr0.PrisonScoreboard.events;

import com.lcastr0.PrisonScoreboard.PrisonScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillEvent implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent event){
        Player killer = event.getEntity().getKiller();
        PrisonScoreboardManager psm = PrisonScoreboardManager.getManager(killer.getUniqueId());
        psm.updateScoreboard();
    }

}
