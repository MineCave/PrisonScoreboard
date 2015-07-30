package com.lcastr0.prisonscoreboard.objects;

import com.lcastr0.prisonscoreboard.managers.ObjectManager;

public class Suffix {

    private final String suffix, permission, path;

    public Suffix(String suffix, String permission, String path){
        this.suffix = suffix;
        this.permission = permission;
        this.path = path;
        ObjectManager.addSuffix(this);
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

}
