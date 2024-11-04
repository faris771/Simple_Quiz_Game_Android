# Mobile Math Quiz Game

Advanced Computer Systems Engineering Laboratory – ENCS5150  
**Project Name**: ToDo2: SQLite Databases in Android  

---

## Project Overview

This project is a simple Android-based mobile math quiz game designed as part of the ENCS5150 course. Users register to participate in a math quiz with a series of 5 questions, with scores updated based on correct and incorrect answers. The game data, including quiz questions, user information, and scores, is managed through an SQLite database, and quiz questions are loaded from an external CSV file.


[screen-capture.webm](https://github.com/user-attachments/assets/406a94ef-a4e0-4587-bc4e-ac9c3620bf23)



## Features

### User Registration (Main Activity)
- Users register with a unique **Username**, **Email**, and **Birthdate**.
- Registration checks for duplicate usernames in the database.
- Once registration is complete, the **Start** button initiates the game.

### Quiz Functionality (Quiz Activity)
- **Random Question Selection**: 5 unique questions are randomly selected from a CSV file.
- **Timer**: Each question has a 10-second countdown. The quiz auto-progresses to the next question when time expires.
- **Scoring**:
  - +1 point for each correct answer.
  - -1 point for incorrect answers or unanswered questions.
- **Question Transition**: Automatically progresses to the next question.

### Quiz Summary (End Activity)
- Displays a summary of the user’s performance and feedback.
- Queries include:
  - **Top 5 Scores**: Highest scores from all users.
  - **Total Players**: Total number of unique players.
  - **Player Scores**: Retrieves all scores for a specific user.
  - **Average Score**: Calculates the average score across all users.
  - **Highest Score**: Finds the highest score recorded.

- Option to start a new game with a button to navigate back to the main screen.

## Database Schema

### Table 1: Scores
| Column      | Type        | Description                                         |
|-------------|-------------|-----------------------------------------------------|
| `id`        | INTEGER     | Unique identifier for each score entry (auto-incremented) |
| `nickname`  | TEXT        | The player's unique username                       |
| `score`     | INTEGER     | The score achieved by the player                    |
| `timestamp` | TIMESTAMP   | Date and time of score record                       |

### Table 2: Questions
| Column           | Type   | Description                         |
|------------------|--------|-------------------------------------|
| `id`             | INTEGER| Unique identifier for each question |
| `question`       | TEXT   | Math question text                 |
| `correct_answer` | TEXT   | Correct answer                     |
| `option1`        | TEXT   | Additional answer option           |
| `option2`        | TEXT   | Additional answer option           |
| `option3`        | TEXT   | Additional answer option           |

## How to Use
1. **Register**: Fill in the registration fields and click **Start**.
2. **Answer Questions**: Select the correct answer within the timer limit.
3. **View Results**: After 5 questions, view your score and game summary.

## Error Handling
- Handles potential issues such as:
  - Failed CSV file reads.
  - Database connectivity errors.

## Design Guidelines
- The app’s name follows the convention `ID_FirstName_FamilyName`.
- Built to be compatible with a Pixel 3a XL (API Level 26).

## Submission Requirements
1. **Project.zip**: Exported from Android Studio.
2. **APK file**: Built from Android Studio.
3. **Demo Video**: Recorded video demonstrating functionality (Google Drive link or upload through Ritaj).

## Dataset
- [Download Questions CSV](https://drive.google.com/file/d/1scJ2xuhrzpXDfdb9m9Nh6oDgQkLHeUWQ/view?usp=sharing)


