package com.lcastr0.PrisonScoreboard.helper;

import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.lcastr0.PrisonScoreboard.obj.Prefix;
import com.lcastr0.PrisonScoreboard.obj.Suffix;

public class PlayerTag {

    public static String getPrefix(Player player){
        String prefix = "";
        for(Prefix p : Prefix.getPrefixes()){
            List<String> players = p.getList();
            if(player.isOp() && players.contains(player.getUniqueId().toString()))
                prefix = p.getPrefix();
            else if(!player.isOp() && player.hasPermission(p.getPermission()))
                prefix = p.getPrefix();
        }
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        return prefix;
    }

    public static String getSuffix(Player player){
        String suffix = "";
        for(Suffix s : Suffix.getSuffixes()){
            List<String> players = s.getList();
            if(player.isOp() && players.contains(player.getUniqueId().toString()))
                suffix = s.getSuffix();
            else if(!player.isOp() && player.hasPermission(s.getPermission()))
                suffix = s.getSuffix();
        }
        suffix = ChatColor.translateAlternateColorCodes('&', suffix);
        return suffix;
    }

}
