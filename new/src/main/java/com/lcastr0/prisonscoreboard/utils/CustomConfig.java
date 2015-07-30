package com.lcastr0.prisonscoreboard.utils;

import com.lcastr0.prisonscoreboard.PrisonScoreboard;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author Ktar5
 */
public class CustomConfig {
    private String fileName;
    private FileConfiguration config;
    private File configFile;

    public CustomConfig(File folder, String fileName) {
        this.fileName = fileName;
        configFile = new File(folder, fileName);
        reloadConfig();
    }

    public String getFileName() {
        return fileName;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getConfigFile() {
        return configFile;
    }

    public void reloadConfig() {
        if (!configFile.exists())
            PrisonScoreboard.getInstance().saveResource(fileName, true);
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception e) {
            PrisonScoreboard.getInstance().getLogger().severe(String.format("Couldn't save '%s', because: '%s'", fileName, e.getMessage()));
        }
    }

    public void set(String path, Object value, boolean save) {
        config.set(path, value);
        if (save)
            saveConfig();
    }

    public void set(String path, Object value) {
        set(path, value, false);
    }

    public boolean has(String path) {
        return config.contains(path);
    }

    public <T> T get(String path, Class<T> tClass) {
        if(!config.contains(path)) {
            throw new IllegalArgumentException(path + " does not exist.");
        }
        Object object = config.get(path);
        if(!tClass.isInstance(object)) {
            throw new IllegalArgumentException(path + " is not of type " + tClass.getSimpleName());
        }
        return tClass.cast(object);
    }
}