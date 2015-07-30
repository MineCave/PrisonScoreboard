package com.lcastr0.prisonscoreboard.objects;

import com.lcastr0.prisonscoreboard.managers.ObjectManager;

public class Prefix {

    private final String prefix, permission, path;

    public Prefix(String prefix, String permission, String path){
        this.prefix = prefix;
        this.permission = permission;
        this.path = path;
        ObjectManager.addPrefix(this);
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

}
