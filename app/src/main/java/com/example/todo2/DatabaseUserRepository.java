        package com.example.todo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
}