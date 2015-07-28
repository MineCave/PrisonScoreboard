package com.lcastr0.PrisonScoreboard.obj;

import java.util.ArrayList;
import java.util.List;

public class Prefix {

    private final String prefix;
    private final String permission;
    private final String path;

    private static List<Prefix> suffixes = new ArrayList<>();

    public Prefix(String prefix, String permission, String path){
        this.prefix = prefix;
        this.permission = permission;
        this.path = path;
        suffixes.add(this);
    }

    public String getPrefix(){
        return this.prefix;
    }

    public String getPermission(){
        return this.permission;
    }

    public String getPath(){
        return this.path;
    }

    public static List<Prefix> getPrefixes(){
        return suffixes;
    }

}
