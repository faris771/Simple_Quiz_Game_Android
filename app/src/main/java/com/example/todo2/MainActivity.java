// MainActivity.java
package com.example.todo2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo2.models.User;
import com.example.todo2.repositories.DatabaseUserRepository;
import com.example.todo2.validators.DateValidator;
import com.example.todo2.validators.EmailValidator;

public class MainActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText birthDateEditText;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        EmailValidator emailValidator = new EmailValidator();
        DateValidator dateValidator = new DateValidator();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String birthDate = birthDateEditText.getText().toString();

                if (userName.isEmpty()) {
                    userNameEditText.setError("Please enter your name");
                } else if (email.isEmpty()) {
                    emailEditText.setError("Please enter your email");
                } else if (birthDate.isEmpty()) {
                    birthDateEditText.setError("Please enter your birth date");
                } else if (!emailValidator.validate(email)) {
                    emailEditText.setError("Invalid email format");
                } else if (!dateValidator.validate(birthDate)) {
                    birthDateEditText.setError("Invalid birth date format (DD/MM/YY) or year is not less than current year");
                } else {
                    saveUser(userName, email, birthDate);
                }
            }
        });
    }

    private void setupViews() {
        userNameEditText = findViewById(R.id.userNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        birthDateEditText = findViewById(R.id.birthDateEditText);
        startButton = findViewById(R.id.startButton);
    }

    private void saveUser(String name, String email, String birthDate) {
        DatabaseUserRepository databaseUserRepository = new DatabaseUserRepository(getApplicationContext());
        User user = new User(name, email, birthDate);

        if (!databaseUserRepository.doesNicknameExist(name)) {
            databaseUserRepository.saveUserToDatabase(user);
            saveUserNameToPreferences(name);
            Toast.makeText(this, "User saved successfully", Toast.LENGTH_SHORT).show();

            // Go to Quiz Activity
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Nickname already exists. Please choose a different name.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserNameToPreferences(String userName) {
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.USERNAME_SHARED_PREFS_KEY, userName);
        editor.apply();
    }
}
