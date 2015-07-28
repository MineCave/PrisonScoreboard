package com.lcastr0.PrisonScoreboard.helper;

import com.lcastr0.PrisonScoreboard.PrisonScoreboard;
import com.lcastr0.PrisonScoreboard.obj.Prefix;
import com.lcastr0.PrisonScoreboard.obj.Suffix;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerTag {

    public static String getPrefix(Player player){
        String prefix = "NONE";
        for(Prefix p : Prefix.getPrefixes()){
            List<String> players = new ArrayList<>();
            if(PrisonScoreboard.getInstance().getConfig().contains("prefixes." + p.getPath() + ".players"))
                players = PrisonScoreboard.getInstance().getConfig().getStringList("prefixes." + p.getPath() + ".players");
            if(player.isOp() && players.contains(player.getUniqueId().toString()))
                prefix = p.getPrefix();
            else if(!player.isOp() && player.hasPermission(p.getPermission()))
                prefix = p.getPrefix();
        }
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        return (prefix.equals("NONE")) ? "" : prefix;
    }

    public static String getSuffix(Player player){
        String suffix = "NONE";
        for(Suffix s : Suffix.getSuffixes()){
            List<String> players = new ArrayList<>();
            if(PrisonScoreboard.getInstance().getConfig().contains("suffixes." + s.getPath() + ".players"))
                players = PrisonScoreboard.getInstance().getConfig().getStringList("suffixes." + s.getPath() + ".players");
            if(player.isOp() && players.contains(player.getUniqueId().toString()))
                suffix = s.getSuffix();
            else if(!player.isOp() && player.hasPermission(s.getPermission()))
                suffix = s.getSuffix();
        }
        suffix = ChatColor.translateAlternateColorCodes('&', suffix);
        return (suffix.equals("NONE")) ? "" : suffix;
    }

}
