package com.example.todo2.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.todo2.Constants;
import com.example.todo2.models.DatabaseHelper;
import com.example.todo2.models.User;

public class DatabaseUserRepository {

    private DatabaseHelper db;

    public DatabaseUserRepository(Context context) {
        db = DatabaseHelper.getInstance(context);
    }

    public void saveUserToDatabase(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER_NAME", user.getUserName());
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("BIRTH_DATE", user.getBirthDate());
        db.getWritableDatabase().insert(Constants.USER_TABLE_NAME, null, contentValues);

    }

    public void logAllUsers() {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(Constants.USER_TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            int userNameIndex = cursor.getColumnIndex("USER_NAME");
            int emailIndex = cursor.getColumnIndex("EMAIL");
            int birthDateIndex = cursor.getColumnIndex("BIRTH_DATE");

            while (cursor.moveToNext()) {
                if (userNameIndex >= 0 && emailIndex >= 0 && birthDateIndex >= 0) {
                    String userName = cursor.getString(userNameIndex);
                    String email = cursor.getString(emailIndex);
                    String birthDate = cursor.getString(birthDateIndex);
                    Log.d("DatabaseUserRepository", "User: " + userName + ", Email: " + email + ", Birth Date: " + birthDate);
                }
            }

            cursor.close();
        }
    }


    public boolean doesNicknameExist(String userName) {
        SQLiteDatabase database = db.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + Constants.USER_TABLE_NAME + " WHERE USER_NAME = ?";
        Cursor cursor = database.rawQuery(query, new String[]{userName});

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        return false;
    }

    public void addNicknameToScores(String nickname, int score) {
        if (!doesNicknameExist(nickname)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nickname", nickname);
            contentValues.put("score", score);
            db.getWritableDatabase().insert("scores", null, contentValues);
        } else {
            Log.d("Database", "Nickname already exists: " + nickname);
        }
    }

    public int getUserId(String userName) {

        Log.d("DatabaseUserRepository", "Fetching user ID for username: " + userName);

        SQLiteDatabase database = db.getReadableDatabase();
        String query = "SELECT ID FROM " + Constants.USER_TABLE_NAME + " WHERE USER_NAME = ?";
        Cursor cursor = database.rawQuery(query, new String[]{userName});

        int userId = -1;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(0);
            }
            cursor.close();
        }
        return userId;
    }
}