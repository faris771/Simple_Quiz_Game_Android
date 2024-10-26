package com.example.todo2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText birthDateEditText;
    private Button startButton;

    private String userName;
    private String email;
    private String birthDate;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupViews();
        String name, email, birthDate;

        name = userNameEditText.getText().toString();
        email = emailEditText.getText().toString();
        birthDate = birthDateEditText.getText().toString();


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.isEmpty() || email.isEmpty() || birthDate.isEmpty()) {
                    return;
                }
                // add the user to the database if doesn't exist already
                // if exists, get the user id

                // start the quiz activity
            }
        });








    }




    private void setupViews() {

        userNameEditText = findViewById(R.id.userNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        birthDateEditText = findViewById(R.id.birthDateEditText);
        startButton = findViewById(R.id.startButton);

    }






}