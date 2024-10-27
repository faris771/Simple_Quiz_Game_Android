package com.example.todo2.generators;

import java.util.HashSet;
import java.util.Set;

public class OptionsGenerator {

    public String[] generateOptions(String answer) {
        int correctAnswer = Integer.parseInt(answer);
        Set<Integer> optionsSet = new HashSet<>();
        optionsSet.add(correctAnswer);


        while (optionsSet.size() < 3) {
            int option = correctAnswer + (int) (Math.random() * 20 - 10); // Generate numbers within +- 10 range
            optionsSet.add(option);
        }

        String[] options = new String[3];
        int index = 0;
        for (int option : optionsSet) {
            options[index++] = String.valueOf(option);
        }

        return options; // Return the options in a random order


    }
}