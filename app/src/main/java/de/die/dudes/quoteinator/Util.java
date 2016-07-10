package de.die.dudes.quoteinator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Phil on 03.04.2016.
 */
public class Util {
    private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static Calendar getDate(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse(date));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringOfDate(Calendar cal) {
        return dateFormat.format(cal.getTime());
    }
}
