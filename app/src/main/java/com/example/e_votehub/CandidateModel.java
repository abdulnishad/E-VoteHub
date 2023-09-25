package com.example.e_votehub;

public class CandidateModel {
    private int candidateId;
    private String fullName;
    private String position; // Assuming each candidate has a position
    // Add other properties for the candidate as needed

    public CandidateModel(int candidateId, String fullName, String position) {
        this.candidateId = candidateId;
        this.fullName = fullName;
        this.position = position;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // Add getter and setter methods for other properties as needed
}

