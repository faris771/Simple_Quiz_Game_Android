
package com.example.todo2.generators;

public class OptionsGenerator {

    public String[] generateOptions(String correctAnswer) {
        String[] options = new String[3];
        try {
            int correctAnswerInt = Integer.parseInt(correctAnswer);
            options[0] = String.valueOf(correctAnswerInt + 1);
            options[1] = String.valueOf(correctAnswerInt + 2);
            options[2] = String.valueOf(correctAnswerInt + -1);
        } catch (NumberFormatException e) {
            // Handle the case where the correct answer is not an integer
            options[0] = correctAnswer + " option 1";
            options[1] = correctAnswer + " option 2";
            options[2] = correctAnswer + " option 3";
        }
        return options;
    }
}





//package com.example.todo2.generators;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class OptionsGenerator {
//
//    public String[] generateOptions(String answer) {
//        int correctAnswer = Integer.parseInt(answer);
//        Set<Integer> optionsSet = new HashSet<>();
//        optionsSet.add(correctAnswer);
//
//
//        while (optionsSet.size() < 3) {
//            int option = correctAnswer + (int) (Math.random() * 20 - 10); // Generate numbers within +- 10 range
//            optionsSet.add(option);
//        }
//
//        String[] options = new String[3];
//        int index = 0;
//        for (int option : optionsSet) {
//            options[index++] = String.valueOf(option);
//        }
//
//        return options; // Return the options in a random order
//
//
//    }
//}