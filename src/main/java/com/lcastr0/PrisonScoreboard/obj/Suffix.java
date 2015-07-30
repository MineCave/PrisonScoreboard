package com.lcastr0.PrisonScoreboard.obj;

import java.util.ArrayList;
import java.util.List;

import com.lcastr0.PrisonScoreboard.PrisonScoreboard;

public class Suffix {

    private final String suffix;
    private final String permission;
    private final String path;
    private final List<String> list;

    private static List<Suffix> suffixes = new ArrayList<>();

    public Suffix(String suffix, String permission, String path){
        this.suffix = suffix;
        this.permission = permission;
        this.path = path;
        if (PrisonScoreboard.getInstance().getConfig().contains("suffixes." + getPath() + ".players"))
        	list = PrisonScoreboard.getInstance().getConfig().getStringList("suffixes." + getPath() + ".players");
        else
        	list = new ArrayList<String>();
        suffixes.add(this);
    }

    public String getSuffix(){
        return this.suffix;
    }

    public String getPermission(){
        return this.permission;
    }

    public String getPath(){
        return this.path;
    }
    
    public List<String> getList() {
    	return list;
    }

    public static List<Suffix> getSuffixes(){
        return suffixes;
    }

}
