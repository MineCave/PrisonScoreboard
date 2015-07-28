package com.lcastr0.PrisonScoreboard.obj;

import java.util.ArrayList;
import java.util.List;

public class Suffix {

    private final String suffix;
    private final String permission;
    private final String path;

    private static List<Suffix> suffixes = new ArrayList<>();

    public Suffix(String suffix, String permission, String path){
        this.suffix = suffix;
        this.permission = permission;
        this.path = path;
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

    public static List<Suffix> getSuffixes(){
        return suffixes;
    }

}
