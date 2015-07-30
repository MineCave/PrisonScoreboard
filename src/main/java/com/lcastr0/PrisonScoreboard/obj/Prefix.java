package com.lcastr0.PrisonScoreboard.obj;

import java.util.ArrayList;
import java.util.List;

import com.lcastr0.PrisonScoreboard.PrisonScoreboard;

public class Prefix {

    private final String prefix;
    private final String permission;
    private final String path;
    private final List<String> list;

    private static List<Prefix> prefixes = new ArrayList<>();

    public Prefix(String prefix, String permission, String path){
        this.prefix = prefix;
        this.permission = permission;
        this.path = path;
        if (PrisonScoreboard.getInstance().getConfig().contains("prefixes." + getPath() + ".players"))
        	list = PrisonScoreboard.getInstance().getConfig().getStringList("prefixes." + getPath() + ".players");
        else
        	list = new ArrayList<String>();
        prefixes.add(this);
    }

    public String getPrefix(){
        return prefix;
    }

    public String getPermission(){
        return permission;
    }

    public String getPath(){
        return path;
    }
    
    public List<String> getList() {
    	return list;
    }

    public static List<Prefix> getPrefixes(){
        return prefixes;
    }

}
