package com.example.todo2.models;

public class Question {

    private int questionId;
    private String question;
    private String correctAnswer;
    private String option1;
    private String option2;
    private String option3;

    public Question() {
    }

    public Question(int questionId, String question, String corrrectAnswer, String option1, String option2, String option3) {
        this.questionId = questionId;
        this.question = question;
        this.correctAnswer = corrrectAnswer;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String corrrectAnswer) {
        this.correctAnswer = corrrectAnswer;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }


}
