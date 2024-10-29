// EndActivity.java
package com.example.todo2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo2.repositories.DatabaseScoreRepository;

import java.util.List;

public class EndActivity extends AppCompatActivity {

    private DatabaseScoreRepository scoreRepository;
    private TextView topScoresTextView;
    private TextView totalPlayersTextView;
    private EditText playerNicknameEditText;
    private Button retrieveScoresButton;
    private Button returnToMainActivityButton;


    private TextView playerScoresTextView;
    private TextView averageScoreTextView;
    private TextView highestScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        scoreRepository = new DatabaseScoreRepository(this);

        initializeViews();
        displayTopScores();
        displayTotalPlayers();
        displayAverageScore();
        displayHighestScore();

        retrieveScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = playerNicknameEditText.getText().toString();
                displayPlayerScores(nickname);
            }
        });

        returnToMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to MainActivity
                finish();
            }
        });



    }

    private void initializeViews() {
        topScoresTextView = findViewById(R.id.topScoresTextView);
        totalPlayersTextView = findViewById(R.id.totalPlayersTextView);
        playerNicknameEditText = findViewById(R.id.playerNicknameEditText);
        retrieveScoresButton = findViewById(R.id.retrieveScoresButton);
        returnToMainActivityButton = findViewById(R.id.returnToMainActivityButton);
        playerScoresTextView = findViewById(R.id.playerScoresTextView);
        averageScoreTextView = findViewById(R.id.averageScoreTextView);
        highestScoreTextView = findViewById(R.id.highestScoreTextView);
    }

    private void displayTopScores() {
        List<String> topScores = scoreRepository.getTop5Scores();
        StringBuilder topScoresText = new StringBuilder("Top 5 Scores:\n");
        for (String score : topScores) {
            topScoresText.append(score).append("\n");
        }
        topScoresTextView.setText(topScoresText.toString());
    }

    private void displayTotalPlayers() {
        int totalPlayers = scoreRepository.getTotalNumberOfPlayers();
        totalPlayersTextView.setText("Total Players: " + totalPlayers);
    }

    private void displayPlayerScores(String username) {
        username = username.trim();
        // to lower
        username = username.toLowerCase();
        List<Integer> scores = scoreRepository.getAllScoresOfPlayer(username);
        StringBuilder playerScoresText = new StringBuilder("Scores of " + username + ":\n");
        for (int score : scores) {
            playerScoresText.append(score).append("\n");
        }
        playerScoresTextView.setText(playerScoresText.toString());
    }

    private void displayAverageScore() {
        double averageScore = scoreRepository.getAverageScore();
        averageScoreTextView.setText("Average Score: " + averageScore);
    }

    private void displayHighestScore() {
        String highestScore = scoreRepository.getHighestScore();
        highestScoreTextView.setText("Highest Score: " + highestScore);
    }
}