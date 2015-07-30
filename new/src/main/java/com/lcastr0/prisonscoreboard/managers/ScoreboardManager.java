package com.lcastr0.prisonscoreboard.managers;

import com.lcastr0.prisonscoreboard.PrisonScoreboard;
import com.lcastr0.prisonscoreboard.utils.ProgressBar;
import com.lcastr0.prisonscoreboard.utils.ScoreboardCreator;
import net.md_5.bungee.api.ChatColor;

import java.util.*;

public class ScoreboardManager {

    private final UUID uuid;
    private final String title;
    private final String[] progressBarInfo;
    private final ScoreboardCreator creator;
    private final PrisonScoreboard instance;
    private final ProgressBar progressBar;
    private final List<String> lines;
    private final Map<Integer, String> linesMap = new HashMap<>();
    private boolean update = false;

    public ScoreboardManager(UUID uuid){
        this.uuid = uuid;
        ConfigManager manager = ObjectManager.getManager();
        this.title = manager.getTitle();
        this.creator = new ScoreboardCreator(title);
        this.instance = PrisonScoreboard.getInstance();
        this.progressBarInfo = manager.getProgressBar();
        this.progressBar =
                new ProgressBar(Integer.valueOf(progressBarInfo[6]), ChatColor.getByChar(progressBarInfo[3].charAt(0)),
                        ChatColor.getByChar(progressBarInfo[4].charAt(0)), progressBarInfo[5]);
        this.lines = manager.getLines();
    }

}
