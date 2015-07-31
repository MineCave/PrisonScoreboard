package com.lcastr0.prisonscoreboard.objects;

public class Vote {

    private int votes;
    private final int maxVotes;

    public Vote(int votes, int maxVotes){
        this.votes = votes;
        this.maxVotes = maxVotes;
    }

    public void increaseVotes(){
        this.votes++;
        this.checkVotes();
    }

    public void setVotes(int votes){
        this.votes = votes;
        this.checkVotes();
    }

    public void addVotes(int votes){
        this.votes += votes;
        this.checkVotes();
    }

    public void removeVotes(int votes){
        this.votes -= votes;
        this.checkVotes();
    }

    public int getVotes(){
        return this.votes;
    }

    public int getMaxVotes(){
        return this.maxVotes;
    }

    private void checkVotes(){
        if(this.votes >= this.maxVotes){
            this.votes = 0;
        }
    }

}
