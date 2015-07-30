package com.lcastr0.prisonscoreboard.objects;

import com.lcastr0.prisonscoreboard.managers.ObjectManager;

public class Rank {

    private final String name;
    private final double money;
    private final int rank;

    public Rank(String name, double money, int rank){
        this.name = name;
        this.money = money;
        this.rank = rank;
        ObjectManager.addRank(this);
    }

    public String getName(){
        return this.name;
    }

    public double getMoney(){
        return this.money;
    }

    public int getRank(){
        return this.rank;
    }

}
