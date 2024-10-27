package com.example.todo2.loaders;

import android.content.Context;

import com.example.todo2.Constants;
import com.example.todo2.models.Question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVQuestionLoader implements QuestionLoader {


    @Override
    public List<Question> load(Context context) {
        List<Question> questions = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(Constants.CSV_FILE_NAME)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
//                    questions.add(new Question(parts[0], parts[1]));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;

    }
}
