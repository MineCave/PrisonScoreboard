package com.lcastr0.PrisonScoreboard;

public class VoteReceiver {

    public int votes;
    public int maxVotes;

    public VoteReceiver(int votes, int maxVotes){
        this.votes = votes;
        this.maxVotes = maxVotes;
    }

    public void increaseVotes(){
        this.votes++;
        if(this.votes >= this.maxVotes)
            this.votes = 0;
    }

    public void setVotes(int votes){
        this.votes = votes;
        if(this.votes >= this.maxVotes)
            this.votes = 0;
    }

    public void addVotes(int votes){
        this.votes += votes;
        if(this.votes >= this.maxVotes)
            this.votes = 0;
    }

    public void removeVotes(int votes){
        this.votes -= votes;
        if(this.votes >= this.maxVotes)
            this.votes = 0;
    }

    public int getVotes(){
        return this.votes;
    }

}
