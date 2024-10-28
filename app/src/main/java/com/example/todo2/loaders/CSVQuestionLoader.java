package com.example.todo2.loaders;

import android.content.Context;
import android.util.Log;

import com.example.todo2.Constants;
import com.example.todo2.generators.OptionsGenerator;
import com.example.todo2.models.Question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVQuestionLoader implements QuestionLoader {

    @Override
    public List<Question> load(Context context) {
        List<Question> questions = new ArrayList<>();
        OptionsGenerator optionsGenerator = new OptionsGenerator();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(Constants.CSV_FILE_NAME)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String questionText = parts[0];
                    String correctAnswer = parts[1];

                    Log.d("CSVQuestionLoader", "Loaded question: " + questionText);

                    // Generate options using OptionsGenerator
                    String[] options = optionsGenerator.generateOptions(correctAnswer);

                    // Create Question object with correct answer and generated options
                    Question question = new Question();
                    question.setQuestion(questionText);
                    question.setCorrectAnswer(correctAnswer);
                    question.setOption1(options[0]);
                    question.setOption2(options[1]);
                    question.setOption3(options[2]);

                    questions.add(question);

                    System.out.println("Loaded question: " + questionText);
                    Log.d("CSVQuestionLoader", "Loaded question: " + questionText);
                } else {
                    Log.e("CSVQuestionLoader", "Invalid line format: " + line);
                    System.out.println("Invalid line format: " + line);
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.e("CSVQuestionLoader", "Error loading CSV file", e);
            System.out.println("Error loading CSV file");
        }

        return questions;
    }
}