package com.example.todo2.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo2.Constants;
import com.example.todo2.generators.OptionsGenerator;
import com.example.todo2.loaders.CSVQuestionLoader;
import com.example.todo2.models.DatabaseHelper;
import com.example.todo2.models.Question;

import java.util.List;
public class DatabaseQuestionRepository {


    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    public DatabaseQuestionRepository(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
        sharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void loadQuestionsToDatabase(Context context) {
        // Check if questions have already been loaded
        if (sharedPreferences.getBoolean(Constants.KEY_QUESTIONS_LOADED, false)) {
            return; // Skip loading if already loaded
        }

        // Load questions from CSV
        CSVQuestionLoader csvQuestionLoader = new CSVQuestionLoader();
        List<Question> questions = csvQuestionLoader.load(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        OptionsGenerator optionsGenerator = new OptionsGenerator();

        for (Question question : questions) {

            String[] options = optionsGenerator.generateOptions(question.getCorrrectAnswer());
            ContentValues contentValues = new ContentValues();
            contentValues.put("QUESTION", question.getQuestion());
            contentValues.put("CORRECT_ANSWER", question.getCorrrectAnswer());
            contentValues.put("OPTION1", options[0]);
            contentValues.put("OPTION2", options[1]);
            contentValues.put("OPTION3", options[2]);
            db.insert(Constants.QUESTION_TABLE_NAME, null, contentValues);
        }

        // Set flag in SharedPreferences to indicate loading is complete
        sharedPreferences.edit().putBoolean(Constants.KEY_QUESTIONS_LOADED, true).apply();
    }
}