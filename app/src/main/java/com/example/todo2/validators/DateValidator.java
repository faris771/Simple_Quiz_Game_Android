package com.example.todo2.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateValidator implements Validator {

    @Override
    public boolean validate(String input) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);
        try {
            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);

            Calendar birthDateCalendar = Calendar.getInstance();
            birthDateCalendar.setTime(dateFormat.parse(input));
            int birthYear = birthDateCalendar.get(Calendar.YEAR);

            return birthYear < currentYear;
        } catch (ParseException e) {
            return false;
        }
    }

}
