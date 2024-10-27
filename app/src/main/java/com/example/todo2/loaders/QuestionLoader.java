package com.example.todo2.loaders;

import android.content.Context;

import com.example.todo2.models.Question;

import java.util.List;

public interface QuestionLoader {


    public List<Question> load(Context context);

}
