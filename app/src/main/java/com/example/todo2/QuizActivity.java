package com.example.todo2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo2.models.Question;
import com.example.todo2.repositories.DatabaseQuestionRepository;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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
                    Toast.makeText(QuizActivity.this, "Correct Answer!", Toast.LENGTH_SHORT).show();
                    selectedButton.setBackgroundColor(Color.GREEN);
                } else {
                    score = Math.max(0, score - 1);
                    Toast.makeText(QuizActivity.this, "Incorrect Answer!", Toast.LENGTH_SHORT).show();
                    selectedButton.setBackgroundColor(Color.RED);
                }

                // Highlight the correct answer
                highlightCorrectAnswer(currentQuestion.getCorrectAnswer());

                updateScore();

                // Wait for 500 ms before showing the next question
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetButtonColors();
                        showNextQuestion();
                    }
                }, 500);
            }
        };

        setOptionButtonListeners(optionClickListener); // Set the same listener for all option buttons
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
        repository.loadQuestionsToDatabase(this);  // This ensures questions are loaded once
        questions = repository.getRandomQuestions(Constants.QUESTIONS_PER_ROUND);

        if (questions == null || questions.isEmpty()) {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no questions are available
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

            // Update question number text view
            questionNumberTextView.setText("Question: " + (currentQuestionIndex + 1) + "/" + questions.size());

            List<String> options = Arrays.asList(
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getCorrectAnswer()
            );
            Collections.shuffle(options); // to make sure correct answer is not always in the same position

            optionButton1.setText(options.get(0));
            optionButton2.setText(options.get(1));
            optionButton3.setText(options.get(2));
            optionButton4.setText(options.get(3));

            startTimer();
        } else {
            // End of quiz
            Toast.makeText(this, "Quiz Complete!", Toast.LENGTH_SHORT).show();
            finish(); // Optionally close activity after quiz is complete
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
        } else {
            optionButton1.setBackgroundColor(Color.RED);
        }
        if (optionButton2.getText().toString().equals(correctAnswer)) {
            optionButton2.setBackgroundColor(Color.GREEN);
        } else {
            optionButton2.setBackgroundColor(Color.RED);
        }
        if (optionButton3.getText().toString().equals(correctAnswer)) {
            optionButton3.setBackgroundColor(Color.GREEN);
        } else {
            optionButton3.setBackgroundColor(Color.RED);
        }
        if (optionButton4.getText().toString().equals(correctAnswer)) {
            optionButton4.setBackgroundColor(Color.GREEN);
        } else {
            optionButton4.setBackgroundColor(Color.RED);
        }
    }

    private void resetButtonColors() {

        optionButton1.setBackgroundColor(Color.parseColor(Constants.BUTTON_DEFAULT_COLOR));
        optionButton2.setBackgroundColor(Color.parseColor(Constants.BUTTON_DEFAULT_COLOR));
        optionButton3.setBackgroundColor(Color.parseColor(Constants.BUTTON_DEFAULT_COLOR));
        optionButton4.setBackgroundColor(Color.parseColor(Constants.BUTTON_DEFAULT_COLOR));

    }


}