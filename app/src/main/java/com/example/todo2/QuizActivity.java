// QuizActivity.java
package com.example.todo2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo2.models.Question;
import com.example.todo2.repositories.DatabaseQuestionRepository;
import com.example.todo2.repositories.DatabaseScoreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private TextView questionTextView;
    private TextView timerTextView;
    private TextView currentScoreTextView;
    private TextView questionNumberTextView;

    private Button optionButton1;
    private Button optionButton2;
    private Button optionButton3;
    private Button optionButton4;

    private CountDownTimer countDownTimer;
    private DatabaseScoreRepository scoreRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        scoreRepository = new DatabaseScoreRepository(this);

        initializeViews();
        loadQuestions();
        showQuestion();

        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button selectedButton = (Button) v;
                String selectedAnswer = selectedButton.getText().toString();
                Question currentQuestion = questions.get(currentQuestionIndex);

                if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                    score++;
                    selectedButton.setBackgroundColor(Color.GREEN);
                } else {
                    score = Math.max(0, score - 1);
                    selectedButton.setBackgroundColor(Color.RED);
                }

                highlightCorrectAnswer(currentQuestion.getCorrectAnswer());
                updateScore();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetButtonColors();
                        showNextQuestion();
                    }
                }, 500);
            }
        };

        setOptionButtonListeners(optionClickListener);
    }

    private void initializeViews() {
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
        currentScoreTextView = findViewById(R.id.currentScoreTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);

        optionButton1 = findViewById(R.id.optionButton1);
        optionButton2 = findViewById(R.id.optionButton2);
        optionButton3 = findViewById(R.id.optionButton3);
        optionButton4 = findViewById(R.id.optionButton4);
    }

    private void loadQuestions() {
        DatabaseQuestionRepository repository = new DatabaseQuestionRepository(this);
        repository.loadQuestionsToDatabase(this);
        questions = repository.getRandomQuestions(Constants.QUESTIONS_PER_ROUND);

        if (questions == null || questions.isEmpty()) {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    private void setOptionButtonListeners(View.OnClickListener listener) {
        optionButton1.setOnClickListener(listener);
        optionButton2.setOnClickListener(listener);
        optionButton3.setOnClickListener(listener);
        optionButton4.setOnClickListener(listener);
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionTextView.setText(question.getQuestion());
            questionNumberTextView.setText("Question: " + (currentQuestionIndex + 1) + "/" + questions.size());

            List<String> options = Arrays.asList(
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getCorrectAnswer()
            );
            Collections.shuffle(options);

            optionButton1.setText(options.get(0));
            optionButton2.setText(options.get(1));
            optionButton3.setText(options.get(2));
            optionButton4.setText(options.get(3));

            startTimer();
        } else {

            Log.d("QuizActivity", "Uploading to database");

            storeScoreInDatabase();
            Toast.makeText(this, "Quiz Complete!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuizActivity.this, EndActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(Constants.QUESTION_TIMER_DURATION, Constants.QUESTION_COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / Constants.QUESTION_COUNTDOWN_INTERVAL);
            }

            @Override
            public void onFinish() {
                score = Math.max(0, score - 1);
                updateScore();
                showNextQuestion();
            }
        }.start();
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        showQuestion();
    }

    private void updateScore() {
        currentScoreTextView.setText("Score: " + score);
    }

    private void highlightCorrectAnswer(String correctAnswer) {
        if (optionButton1.getText().toString().equals(correctAnswer)) {
            optionButton1.setBackgroundColor(Color.GREEN);
        }
        if (optionButton2.getText().toString().equals(correctAnswer)) {
            optionButton2.setBackgroundColor(Color.GREEN);
        }
        if (optionButton3.getText().toString().equals(correctAnswer)) {
            optionButton3.setBackgroundColor(Color.GREEN);
        }
        if (optionButton4.getText().toString().equals(correctAnswer)) {
            optionButton4.setBackgroundColor(Color.GREEN);
        }
    }

    private void resetButtonColors() {
        // make them back to normal default color using #ff6750a4
        optionButton1.setTextColor(Color.parseColor(Constants.BUTTON_DEFAULT_COLOR));
        optionButton2.setTextColor(Color.parseColor(Constants.BUTTON_DEFAULT_COLOR));
        optionButton3.setTextColor(Color.parseColor(Constants.BUTTON_DEFAULT_COLOR));
        optionButton4.setTextColor(Color.parseColor(Constants.BUTTON_DEFAULT_COLOR));

    }

    private void storeScoreInDatabase() {
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        String username = prefs.getString(Constants.USERNAME_SHARED_PREFS_KEY, "");

        if (userId == -1) {
            Toast.makeText(this, "User not found in preferences", Toast.LENGTH_SHORT).show();
            return;
        }

        scoreRepository.insertScore(userId, username, score);
    }
}
