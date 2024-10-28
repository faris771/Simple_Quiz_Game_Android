package com.example.todo2.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.todo2.Constants;
import com.example.todo2.loaders.CSVQuestionLoader;
import com.example.todo2.models.DatabaseHelper;
import com.example.todo2.models.Question;

import java.util.ArrayList;
import java.util.List;

public class DatabaseQuestionRepository {

    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    public DatabaseQuestionRepository(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
        sharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void loadQuestionsToDatabase(Context context) {
        if (sharedPreferences.getBoolean(Constants.KEY_QUESTIONS_LOADED, false)) { // Check if questions are already loaded to the databae if loaded return else load
            Log.d("DatabaseQuestionRepository", "[PREF]Questions already loaded to the database.");

            return;
        }

        CSVQuestionLoader csvQuestionLoader = new CSVQuestionLoader();
        List<Question> questions = csvQuestionLoader.load(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (Question question : questions) {

            Log.d("DatabaseQuestionRepository", "Inserting question: " + question.getQuestion());

            ContentValues contentValues = new ContentValues();
            contentValues.put("QUESTION", question.getQuestion());
            contentValues.put("CORRECT_ANSWER", question.getCorrectAnswer());
            contentValues.put("OPTION1", question.getOption1());
            contentValues.put("OPTION2", question.getOption2());
            contentValues.put("OPTION3", question.getOption3());
            long result = db.insert(Constants.QUESTION_TABLE_NAME, null, contentValues);
            if (result == -1) {
                Log.e("DatabaseQuestionRepository", "Failed to insert question: " + question.getQuestion());
            } else {
                Log.d("DatabaseQuestionRepository", "Inserted question: " + question.getQuestion());
            }
        }

        sharedPreferences.edit().putBoolean(Constants.KEY_QUESTIONS_LOADED, true).apply();
    }

    public List<Question> getRandomQuestions(int limit) {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.QUESTION_TABLE_NAME + " ORDER BY RANDOM() LIMIT " + limit, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();

                int questionIndex = cursor.getColumnIndex("QUESTION");
                int correctAnswerIndex = cursor.getColumnIndex("CORRECT_ANSWER");
                int option1Index = cursor.getColumnIndex("OPTION1");
                int option2Index = cursor.getColumnIndex("OPTION2");
                int option3Index = cursor.getColumnIndex("OPTION3");

                if (questionIndex >= 0) {
                    question.setQuestion(cursor.getString(questionIndex));
                }
                if (correctAnswerIndex >= 0) {
                    question.setCorrectAnswer(cursor.getString(correctAnswerIndex));
                }
                if (option1Index >= 0) {
                    question.setOption1(cursor.getString(option1Index));
                }
                if (option2Index >= 0) {
                    question.setOption2(cursor.getString(option2Index));
                }
                if (option3Index >= 0) {
                    question.setOption3(cursor.getString(option3Index));
                }

                questions.add(question);
            } while (cursor.moveToNext());
        } else {
            Log.e("DatabaseQuestionRepository", "GETRANDOMQUESTION METHOD No questions found in the database.");
        }
        cursor.close();
        return questions;
    }
}
