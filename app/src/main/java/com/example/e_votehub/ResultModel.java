package com.example.e_votehub;

public class ResultModel {
    private int candidateId;
    private String candidateName;
    private int voteCount;

    public ResultModel(int candidateId, String candidateName, int voteCount) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.voteCount = voteCount;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}

