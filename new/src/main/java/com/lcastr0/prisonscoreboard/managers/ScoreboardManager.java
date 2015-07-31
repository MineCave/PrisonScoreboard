package com.lcastr0.prisonscoreboard.managers;

import com.lcastr0.prisonscoreboard.PrisonScoreboard;
import com.lcastr0.prisonscoreboard.objects.Rank;
import com.lcastr0.prisonscoreboard.utils.PlayerUtils;
import com.lcastr0.prisonscoreboard.utils.ProgressBar;
import com.lcastr0.prisonscoreboard.utils.ScoreboardCreator;
import com.minecave.KillCounter.obj.KillPlayer;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import ru.tehkode.permissions.PermissionGroup;

import java.util.*;

public class ScoreboardManager {

    private final UUID uuid;
    private final ConfigManager configManager;
    private final String title;
    private final String[] progressBarInfo;
    private final ScoreboardCreator creator;
    private final Scoreboard scoreboard;
    private final ProgressBar progressBar;
    private final List<String> lines;
    private final Map<Integer, String> linesMap = new HashMap<>();
    private boolean update = false;

    public ScoreboardManager(UUID uuid){
        this.uuid = uuid;
        this.configManager = ObjectManager.getManager();
        this.title = configManager.getTitle();
        this.creator = new ScoreboardCreator(title);
        this.scoreboard = this.creator.getScoreboard();
        this.progressBarInfo = configManager.getProgressBar();
        this.progressBar =
                new ProgressBar(Integer.valueOf(progressBarInfo[6]), ChatColor.getByChar(progressBarInfo[3].charAt(0)),
                        ChatColor.getByChar(progressBarInfo[4].charAt(0)), progressBarInfo[5]);
        this.lines = configManager.getLines();
        ObjectManager.addManager(uuid, this);
    }

    public void sendScoreboard(){
        Bukkit.getScheduler().scheduleAsyncDelayedTask(PrisonScoreboard.getInstance(), new Runnable() {
            @Override
            public void run() {
                sendScoreboardToPlayer();
            }
        }, 20L);
    }

    public void sendScoreboardToPlayer(){
        Player player = Bukkit.getPlayer(uuid);
        if(player != null){
            int i = lines.size();
            for(String line : lines){
                if(line.contains("{blank}")){
                    this.creator.blank(i);
                    continue;
                }
                String money = "$ " + PlayerUtils.getFormattedMoney(player);
                String rank = "Error!";
                PermissionGroup max = PlayerUtils.getMax(this.uuid);
                if(max != null){
                    int r = max.getRank();
                    Rank playerRank = configManager.getRank(r);
                    if(playerRank != null){
                        if(playerRank.getName() != null){
                            rank = playerRank.getName();
                        }
                    }
                }
                String world = configManager.getWorld();
                if(!configManager.isDefaultWorld())
                    world = player.getWorld().getName();
                String kills = String.valueOf(KillPlayer.getKillPlayer(this.uuid).getKills(world));
                if(!PlayerUtils.isRankedRank(this.uuid)) {
                    line = StringUtils.replace(line, "%rpb", ChatColor.RED + "No next rank!");
                } else {
                    line = StringUtils.replace(line, "%rpb", PlayerUtils.getProgess(this.progressBarInfo, this.progressBar, this.uuid));
                }
                line = StringUtils.replace(line, "%m", money);
                line = StringUtils.replace(line, "%r", rank);
                line = StringUtils.replace(line, "%o", String.valueOf(Bukkit.getOnlinePlayers().size()) + "  ");
                line = StringUtils.replace(line, "%d", String.valueOf(PrisonScoreboard.getInstance().getReceiver().getVotes()) + " / " +
                    String.valueOf(PrisonScoreboard.getInstance().getReceiver().getMaxVotes()));
                line = StringUtils.replace(line, "%k", kills);
                line = ChatColor.translateAlternateColorCodes('&', line);
                if(linesMap.containsKey(i)){
                    if(!linesMap.get(i).equals(line)){
                        this.creator.add(linesMap.get(i), line, i);
                        this.linesMap.put(i, line);
                    }
                } else {
                    this.creator.add(line, i);
                    this.linesMap.put(i, line);
                }
                i--;
            }
            if(!update){
                this.creator.build();
                this.creator.send(player);
                update = true;
            } else {
                this.creator.update();
            }
        }
    }

    public ScoreboardCreator getCreator(){
        return this.creator;
    }

    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

}
