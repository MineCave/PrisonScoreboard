package com.lcastr0.prisonscoreboard.utils;

import com.lcastr0.prisonscoreboard.PrisonScoreboard;
import com.lcastr0.prisonscoreboard.managers.ConfigManager;
import com.lcastr0.prisonscoreboard.managers.ObjectManager;
import com.lcastr0.prisonscoreboard.objects.Prefix;
import com.lcastr0.prisonscoreboard.objects.Rank;
import com.lcastr0.prisonscoreboard.objects.Suffix;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerUtils {

    private static ConfigManager manager = ObjectManager.getManager();

    public static PermissionGroup getMax(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if(player != null){
            PermissionUser permissionUser = PermissionsEx.getUser(player);
            PermissionGroup max = null;
            for(PermissionGroup group : permissionUser.getParents()){
                if(group.isRanked()){
                    if(max == null){
                        max = group;
                    } else if(max.getRank() > group.getRank()){
                        max = group;
                    }
                }
            }
            if(max != null){
                return max;
            }
        }
        return null;
    }

    public static Rank getPlayerRank(UUID uuid){
        PermissionGroup max = getMax(uuid);
        if(max != null){
            return manager.getRank(max.getRank());
        }
        return manager.getRank(ObjectManager.getManager().getMaxRank());
    }

    public static boolean isRankedRank(UUID uuid){
        Rank r = getPlayerRank(uuid);
        if(isNotRanked(r)){
            return false;
        }
        return true;
    }

    public static String getProgess(String[] config, ProgressBar bar, UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if(player != null){
            PermissionGroup max = getMax(uuid);
            if(max != null){
                int rank = max.getRank();
                Rank r = manager.getRank(rank);
                if(isNotRanked(r)){
                    return manager.getCustomText();
                }
                Rank nr = manager.getNextRank(rank);
                if(nr == null) {
                    return manager.getMaxRankScore();
                }
                String current = r.getName(), next = nr.getName();
                double money = getMoney(player);
                double nextMoney = nr.getMoney();
                bar.setCurrent(money);
                bar.setMax(nextMoney);
                ChatColor color = ChatColor.getByChar(config[0].charAt(0));
                String progress = bar.getProgressBar();
                if(bar.isComplete() && !manager.isCustomTextEnable()){
                    return color + current + " " + progress + " " + next;
                } else if(bar.isComplete()){
                    return manager.getCustomText();
                } else {
                    return color + current + " " + progress + " " + next;
                }
            }
        }
        return "&cError!";
    }

    public static String getFormattedMoney(Player player){
        String formattedMoney;
        long playerMoney = (long) getMoney(player);
        if (playerMoney >= 1000000000000L)
            formattedMoney = String.valueOf(new DecimalFormat("0.00T").format((playerMoney * 1.0D / 1000000000000L)));
        else if (playerMoney >= 1000000000)
            formattedMoney = String.valueOf(new DecimalFormat("0.00B").format((playerMoney * 1.0D / 1000000000)));
        else if (playerMoney >= 1000000)
            formattedMoney = String.valueOf(new DecimalFormat("0.00M").format((playerMoney * 1.0D / 1000000)));
        else if (playerMoney >= 1000)
            formattedMoney = String.valueOf(new DecimalFormat("0.00K").format((playerMoney * 1.0D / 1000)));
        else
            formattedMoney = String.valueOf(playerMoney);
        return formattedMoney;
    }

    private static double getMoney(Player player){
        return PrisonScoreboard.getInstance().getEcon().getBalance(player);
    }

    public static String getPrefix(Player player){
        String p = "";
        for(Prefix prefix : ObjectManager.getPrefixes()){
            List<String> players = new ArrayList<>();
            if(PrisonScoreboard.getInstance().getConfig("prefixes").getConfig().contains("prefixes." + prefix.getPath() + ".players")){
                players = PrisonScoreboard.getInstance().getConfig("prefixes").getConfig().getStringList("prefixes." + prefix.getPath() + ".players");
            }
            if(player.isOp() && players.contains(player.getUniqueId().toString()))
                p = prefix.getPrefix();
            else if(!player.isOp() && player.hasPermission(prefix.getPermission()))
                p = prefix.getPrefix();
        }
        return p;
    }

    public static String getSuffix(Player player){
        String s = "";
        for(Suffix suffix : ObjectManager.getSuffixes()){
            List<String> players = new ArrayList<>();
            if(PrisonScoreboard.getInstance().getConfig("prefixes").getConfig().contains("suffixes." + suffix.getPath() + ".players")){
                players = PrisonScoreboard.getInstance().getConfig("prefixes").getConfig().getStringList("suffixes." + suffix.getPath() + ".players");
            }
            if(player.isOp() && players.contains(player.getUniqueId().toString()))
                s = suffix.getSuffix();
            else if(!player.isOp() && player.hasPermission(suffix.getPermission()))
                s = suffix.getSuffix();
        }
        return s;
    }

    private static boolean isNotRanked(Rank r){
        String name = r.getName();
        if(name.equalsIgnoreCase("OWNER") || name.equalsIgnoreCase("CO-OWNER") || name.equalsIgnoreCase("DEV")
                || name.equalsIgnoreCase("MOD")) {
            return true;
        }
        return false;
    }

}
