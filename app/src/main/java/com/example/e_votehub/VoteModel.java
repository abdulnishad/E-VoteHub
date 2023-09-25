package com.example.e_votehub;

public class VoteModel {
    private int voteId;
    private String voteTitle;
    // Add other properties for the vote as needed

    public VoteModel(int voteId, String voteTitle) {
        this.voteId = voteId;
        this.voteTitle = voteTitle;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }
    @Override
    public String toString() {
        return voteTitle;
    }

    // Add getter and setter methods for other properties as needed
}

