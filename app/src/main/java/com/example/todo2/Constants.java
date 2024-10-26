package com.example.todo2;

public final class Constants {

    private Constants() {
    }

    public static final String DATABASE_NAME = "Quiz_Game.db";
    public static final int DATABASE_VERSION = 1;





    public static final String createUserTableQuery = "CREATE TABLE USER(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "USER_NAME TEXT," +
            "EMAIL TEXT," +
            "BIRTH_DATE DATE)";

    public static final String createQuestionTableQuery = "CREATE TABLE QUESTION(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "QUESTION TEXT," +
            "OPTION1 TEXT NOT NULL," +
            "OPTION2 TEXT NOT NULL," +
            "OPTION3 TEXT NOT NULL," +
            "CORRECT_ANSWER TEXT)";

    public static final String createScoreTableQuery = "CREATE TABLE SCORE(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "USER_ID INTEGER," +
            "SCORE INTEGER," +
            "TIME_STAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY(USER_ID) REFERENCES USER(ID))";







}
