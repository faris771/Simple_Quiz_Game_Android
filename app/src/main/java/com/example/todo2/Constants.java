package com.example.todo2;

public final class Constants {

    private Constants() {


    }

    public static final String DATABASE_NAME = "Quiz_Game.db";
    public static final int DATABASE_VERSION = 2; // Incremented version
    public static final String USER_TABLE_NAME = "USER";
    public static final String QUESTION_TABLE_NAME = "QUESTION";
    public static final String SCORE_TABLE_NAME = "SCORE";
    public static final String CSV_FILE_NAME = "Questions.csv";


    public static final String PREFS_NAME = "com.example.todo2.PREFS";
    public static final String KEY_QUESTIONS_LOADED = "questionsLoaded";


    public static final int QUESTIONS_PER_ROUND = 5;
    public static final long QUESTION_TIMER_DURATION = 10000; // 10 seconds
    public static final int QUESTION_COUNTDOWN_INTERVAL = 1000; // 1 second

    public static final String BUTTON_DEFAULT_COLOR = "#ff6750a4";
    public static final String USERNAME_SHARED_PREFS_KEY = "username";


    public static final String createUserTableQuery = "CREATE TABLE " + USER_TABLE_NAME + "(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "USER_NAME TEXT," +
            "EMAIL TEXT," +
            "BIRTH_DATE DATE)";

    public static final String createQuestionTableQuery = "CREATE TABLE " + QUESTION_TABLE_NAME + "(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "QUESTION TEXT," +
            "OPTION1 TEXT NOT NULL," +
            "OPTION2 TEXT NOT NULL," +
            "OPTION3 TEXT NOT NULL," +
            "CORRECT_ANSWER TEXT)";

    public static final String createScoreTableQuery = "CREATE TABLE " + SCORE_TABLE_NAME + "(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "USER_ID INTEGER," +
            "USER_NAME TEXT," +
            "SCORE INTEGER," +
            "TIME_STAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY(USER_ID) REFERENCES USER(ID))";
}