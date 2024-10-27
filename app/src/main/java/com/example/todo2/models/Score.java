package com.example.todo2.models;

public class Score {

    private int scoreID;
    private String userName; // foreign key  HERE MIGHT CAUSE A PROBLEM CUZ IT IS NOT A PRIMARY KEY, USER_ID SHOULD BE THE PRIMARY KEY
    private int score;
    // TIME STAMP
    private String timeStamp;


    public Score(int scoreID, String userName, int score, String timeStamp) {
        this.scoreID = scoreID;
        this.userName = userName;
        this.score = score;
        this.timeStamp = timeStamp;
    }

    public int getScoreID() {
        return scoreID;
    }

    public void setScoreID(int scoreID) {
        this.scoreID = scoreID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


}
