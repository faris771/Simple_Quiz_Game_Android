// DatabaseScoreRepository.java
package com.example.todo2.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo2.models.DatabaseHelper;
import com.example.todo2.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseScoreRepository {

    private DatabaseHelper dbHelper;

    public DatabaseScoreRepository(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public List<String> getTop5Scores() {
        List<String> topScores = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT USER_NAME, SCORE FROM " + Constants.SCORE_TABLE_NAME + " ORDER BY SCORE DESC LIMIT 5";
        Cursor cursor = db.rawQuery(query, null);
        int usernameIndex = cursor.getColumnIndex("USER_NAME");
        int scoreIndex = cursor.getColumnIndex("SCORE");
        while (cursor.moveToNext()) {
            if (usernameIndex >= 0 && scoreIndex >= 0) {
                String username = cursor.getString(usernameIndex);
                int score = cursor.getInt(scoreIndex);
                topScores.add(username + ": " + score);
            }
        }
        cursor.close();
        return topScores;
    }

    public int getTotalNumberOfPlayers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(DISTINCT USER_NAME) FROM " + Constants.SCORE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public List<Integer> getAllScoresOfPlayer(String username) {
        List<Integer> scores = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT SCORE FROM " + Constants.SCORE_TABLE_NAME + " WHERE USER_NAME = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        int scoreIndex = cursor.getColumnIndex("SCORE");
        while (cursor.moveToNext()) {
            if (scoreIndex >= 0) {
                scores.add(cursor.getInt(scoreIndex));
            }
        }
        cursor.close();
        return scores;
    }

    public double getAverageScore() {

    SQLiteDatabase db = dbHelper.getReadableDatabase();
    String query = "SELECT AVG(SCORE) FROM " + Constants.SCORE_TABLE_NAME;
    Cursor cursor = db.rawQuery(query, null);
    cursor.moveToFirst();
    double average = cursor.getDouble(0); // 0 is the index of the column
    cursor.close();
    return Math.round(average * 100.0) / 100.0; // round to 2 decimal places
}

    public String getHighestScore() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT USER_NAME, SCORE FROM " + Constants.SCORE_TABLE_NAME + " ORDER BY SCORE DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        int usernameIndex = cursor.getColumnIndex("USER_NAME");
        int scoreIndex = cursor.getColumnIndex("SCORE");
        cursor.moveToFirst();
        String result = "";
        if (usernameIndex >= 0 && scoreIndex >= 0) {
            result = cursor.getString(usernameIndex) + ": " + cursor.getInt(scoreIndex);
        }
        cursor.close();
        return result;
    }



    public boolean isNicknameExists(String nickname) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT 1 FROM " + Constants.SCORE_TABLE_NAME + " WHERE USER_NAME = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nickname});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void insertScore(int userId, String username, int score) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USER_ID", userId);
        values.put("USER_NAME", username);
        values.put("SCORE", score);
        db.insert(Constants.SCORE_TABLE_NAME, null, values);
    }

}