package com.lcastr0.PrisonScoreboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.lcastr0.PrisonScoreboard.helper.Color;
import com.lcastr0.PrisonScoreboard.helper.ConfigHelper;
import com.lcastr0.PrisonScoreboard.helper.ProgressBar;
import com.lcastr0.PrisonScoreboard.helper.ScoreboardCreator;
import com.lcastr0.PrisonScoreboard.obj.Rank;
import com.minecave.KillCounter.obj.KillPlayer;

public class PrisonScoreboardManager {

	private static DecimalFormat tFormat;
	private static DecimalFormat bFormat;
	private static DecimalFormat mFormat;
	private static DecimalFormat kFormat;
	private static String barText;
	private static boolean textEnabled;
	
    public static Map<UUID, PrisonScoreboardManager> managers = new HashMap<>();

    public final UUID uuid;

    public String title;
    public ScoreboardCreator scoreboardCreator;
    public PrisonScoreboard instance = PrisonScoreboard.getInstance();
    public Color cs;
    public ProgressBar pBar;
    public boolean update = false;
    private Map<Integer, String> lines;

    private List<String> linesList = new ArrayList<>();

    public PrisonScoreboardManager(UUID uuid){
        this.uuid = uuid;
        this.title = ConfigHelper.getScoreboard().getTitle();
        this.linesList = instance.getLinesList();
        managers.put(uuid, this);
    }

    public void updateScoreboard(){
        Player player = Bukkit.getPlayer(this.uuid);
        boolean next = true;
        if(player != null) {
            if(this.cs == null && ConfigHelper.titleScroll()) {
                String[] s = ConfigHelper.getTitleConfig();
                this.cs = new Color(this.title, ChatColor.getByChar(s[0]), ChatColor.getByChar(s[1]), ChatColor.getByChar(s[2]), ChatColor.getByChar(s[3]),
                        Color.ScrollType.getScroll(Integer.valueOf(s[4])));
            }
            if(this.scoreboardCreator == null) {
                if(ConfigHelper.titleScroll())
                    this.scoreboardCreator = new ScoreboardCreator(this.cs.next());
                else
                    this.scoreboardCreator = new ScoreboardCreator(this.title);
                next = false;
            }
            int i = linesList.size();
            for (String line : linesList) {
                String formattedMoney;
                long playerMoney = (long) this.getMoney(player);
                if (playerMoney >= 1000000000000L)
                    formattedMoney = String.valueOf(tFormat.format((playerMoney * 1.0D / 1000000000000L)));
                else if (playerMoney >= 1000000000)
                    formattedMoney = String.valueOf(bFormat.format((playerMoney * 1.0D / 1000000000)));
                else if (playerMoney >= 1000000)
                    formattedMoney = String.valueOf(mFormat.format((playerMoney * 1.0D / 1000000)));
                else if (playerMoney >= 1000)
                    formattedMoney = String.valueOf(kFormat.format((playerMoney * 1.0D / 1000)));
                else
                    formattedMoney = String.valueOf(playerMoney);
                String money = "$ " + formattedMoney;

                String rank = "ERROR!";

                if(this.getRank() != -1){
                    int r = this.getRank();
                    if(ConfigHelper.getRank(r) != null){
                        if(ConfigHelper.getRank(r).getName() != null){
                            rank = ConfigHelper.getRank(r).getName();
                        }
                    }
                }

                if (rank == "E")
                    rank = "ERROR!";
                String world = ConfigHelper.getDefaultWorld();
                if(!ConfigHelper.useDefaultWorld())
                    world = player.getWorld().getName();
                String kills = String.valueOf(KillPlayer.getKillPlayer(player.getUniqueId()).getKills(world));
                if(!isRankedRank())
                    line = StringUtils.replace(line, "%rpb", ChatColor.RED + "No next rank!");
                else
                    line = StringUtils.replace(line, "%rpb", this.getRankProgress());
                line = StringUtils.replace(line, "%m", money);
                line = StringUtils.replace(line, "%r", rank);
                line = StringUtils.replace(line, "%o", String.valueOf(Bukkit.getOnlinePlayers().size()));
                line = StringUtils.replace(line, "%d", this.instance.receiver.getVotes() + " / " + ConfigHelper.getDropPartyVotes());
                line = StringUtils.replace(line, "%k", kills);
                /*line = line.replaceAll("%m", money).replaceAll("%r", rank)
                        .replaceAll("%o", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replaceAll("%d", this.instance.receiver.getVotes() + " / " + ConfigHelper.getDropPartyVotes()).replaceAll("%k",
                                kills);*/
                line = ChatColor.translateAlternateColorCodes('&', line);
                if (line.contains("{blank}")) {
                    this.scoreboardCreator.blank(i);
                } else {
                    if(lines.containsKey(i)){
                        if(!lines.get(i).equals(line)){
                            this.scoreboardCreator.add(lines.get(i), line, i, player);
                            this.lines.put(i, line);
                        }
                    } else {
                        this.scoreboardCreator.add(line, i);
                        this.lines.put(i, line);
                    }
                }
                i--;
            }
            if (!update) {
                this.scoreboardCreator.build();
                this.scoreboardCreator.send(player);
                update = true;
            } else {
                this.scoreboardCreator.update(player);
            }
            if(ConfigHelper.titleScroll() && next) {
                this.title = this.cs.next();
                if(this.scoreboardCreator != null)
                    this.scoreboardCreator.setTitle(this.title);
            }
        }
    }

    public void removeScoreboard(){
        managers.remove(this.uuid);
        Player player = Bukkit.getPlayer(this.uuid);
        if(player != null)
            this.scoreboardCreator.removePlayer(player);
    }

    private boolean isRankedRank(){
        Player player = Bukkit.getPlayer(this.uuid);
        if(player != null){
            PermissionUser permissionUser = PermissionsEx.getUser(player);
            PermissionGroup max = null;
            for(PermissionGroup group : permissionUser.getParents()){
                if(group.isRanked()) {
                    if (max == null)
                        max = group;
                    else if (max.getRank() > group.getRank())
                        max = group;
                }
            }
            if(max != null) {
                int rank = max.getRank();
                Rank r = ConfigHelper.getRank(rank);
                r.addPlayer(player.getUniqueId());
                if(r.getName().equalsIgnoreCase("OWNER") || r.getName().equalsIgnoreCase("CO-OWNER")
                        || r.getName().equalsIgnoreCase("DEV") || r.getName().equalsIgnoreCase("MOD")
                        || r.getName().equalsIgnoreCase("ADMIN"))
                    return false;
            }
        }
        return true;
    }

    private String getRankProgress(){
        Player player = Bukkit.getPlayer(this.uuid);
        if(player != null){
            PermissionUser permissionUser = PermissionsEx.getUser(player);
            PermissionGroup max = null;
            for(PermissionGroup group : permissionUser.getParents()){
                if(group.isRanked()) {
                    if (max == null)
                        max = group;
                    else if (max.getRank() > group.getRank())
                        max = group;
                }
            }
            if(max != null) {
                int rank = max.getRank();
                Rank r = ConfigHelper.getRank(rank);
                r.addPlayer(player.getUniqueId());
                if(r.getName().equalsIgnoreCase("OWNER") || r.getName().equalsIgnoreCase("CO-OWNER")
                        || r.getName().equalsIgnoreCase("DEV") || r.getName().equalsIgnoreCase("MOD")
                        || r.getName().equalsIgnoreCase("ADMIN"))
                    return barText;
                Rank nr = ConfigHelper.getNext(r);
                if(nr == null)
                    return ChatColor.translateAlternateColorCodes('&', ConfigHelper.getLastRankString());
                String current = r.getName();
                String next = nr.getName();
                long money = (long) this.getMoney(player);
                long nextMoney = nr.getMoney();
                String[] s = ConfigHelper.getProgressBarConfig();
                if(this.pBar == null)
                    this.pBar = new ProgressBar(nextMoney, money, Integer.valueOf(s[6]), ChatColor.getByChar(s[3]), ChatColor.getByChar(s[4]), s[5]);
                else {
                    this.pBar.setMax(nextMoney);
                    this.pBar.setActual(money);
                }
                ChatColor[] c = {ChatColor.getByChar(s[0]), ChatColor.getByChar(s[1]), ChatColor.getByChar(s[2])};
                String progress = this.pBar.getProgressBar();
                if(this.pBar.isComplete() && !textEnabled)
                    return c[0] + current + " " + progress + " " + next;
                else if(this.pBar.isComplete())
                    return barText;
                return c[0] + current + " " + progress + " " + next;
            }
        }
        return "&cERROR";
    }

    private int getRank(){
        Player player = Bukkit.getPlayer(this.uuid);
        if(player != null){
            PermissionUser permissionUser = PermissionsEx.getUser(player);
            PermissionGroup max = null;
            for(PermissionGroup group : permissionUser.getParents()){
                if(group.isRanked()) {
                    if (max == null)
                        max = group;
                    else if (max.getRank() > group.getRank())
                        max = group;
                }
            }
            if(max != null) {
                int rank = max.getRank();
                return rank;
            }
        }
        return -1;
    }

    private double getMoney(Player player){
        PrisonScoreboard instance = PrisonScoreboard.getInstance();
        return instance.getEcon().getBalance(player);
    }

    public static PrisonScoreboardManager getManager(UUID uuid){
        if(managers.containsKey(uuid))
            return managers.get(uuid);
        return new PrisonScoreboardManager(uuid);
    }

    public static void removeScoreboard(UUID uuid){
        if(managers.containsKey(uuid))
            managers.remove(uuid);
    }

    public static void updateAll(){
        for(Player player : Bukkit.getOnlinePlayers()){
            PrisonScoreboardManager psm = PrisonScoreboardManager.getManager(player.getUniqueId());
            psm.updateScoreboard();
        }
    }

    public static void setVariables() {
    	tFormat = new DecimalFormat("0.00T");
    	bFormat = new DecimalFormat("0.00B");
    	mFormat = new DecimalFormat("0.00M");
    	kFormat = new DecimalFormat("0.00K");
    	barText = PrisonScoreboard.getInstance().getConfig().getString("scoreboard.progressBar.customText.text");
    	textEnabled = PrisonScoreboard.getInstance().getConfig().getBoolean("scoreboard.progressBar.customText.enabled");
    }
    
}
