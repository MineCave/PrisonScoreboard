package com.lcastr0.PrisonScoreboard.events;

import com.lcastr0.PrisonScoreboard.PrisonScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.tehkode.permissions.events.PermissionEntityEvent;

import java.util.UUID;

public class RankChange implements Listener {

    @EventHandler
    public void onRankChange(PermissionEntityEvent event){
        Player pl = Bukkit.getPlayer(UUID.fromString(event.getEntityIdentifier()));
        if(pl != null){
            if(pl.isOnline()){
                PrisonScoreboardManager psm = PrisonScoreboardManager.getManager(pl.getUniqueId());
                psm.updateScoreboard();
            }
        }
    }

}
