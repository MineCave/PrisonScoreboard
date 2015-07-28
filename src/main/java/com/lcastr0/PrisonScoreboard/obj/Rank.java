package com.lcastr0.PrisonScoreboard.obj;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Rank {

    public static Map<String, Rank> ranks = new HashMap<>();
    public static Map<UUID, Rank> playerRanks = new HashMap<>();

    public final String name;
    public final long money;
    public final int rank;

    public Rank(String name, long money, int rank){
        this.name = name;
        this.money = money;
        this.rank = rank;
    }

    public String getName(){
        return this.name;
    }

    public long getMoney(){
        return this.money;
    }

    public int getRank(){
        return this.rank;
    }

    public void addPlayer(UUID id){
        playerRanks.put(id, this);
    }

    public static Rank getRank(String name){
        if(ranks.containsKey(name))
            return ranks.get(name);
        return null;
    }

    public static Rank getRank(UUID id){
        if(playerRanks.containsKey(id))
            return playerRanks.get(id);
        return null;
    }

}
