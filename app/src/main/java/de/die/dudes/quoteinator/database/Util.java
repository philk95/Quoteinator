package de.die.dudes.quoteinator.database;

import android.database.Cursor;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Phil on 03.04.2016.
 */
public class Util {
    public static String format(Calendar calendar) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy:HH:mm:ss");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
    }

    public static Calendar parse(String parse) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy:HH:mm:ss");
        Date date;
        try {
            date = formatter.parse(parse);
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar;
    }


    public static int getPositionByName(Spinner mSpinner, int id) {
        SpinnerAdapter adapter = mSpinner.getAdapter();
        Cursor cursor = null;
        long adapterId = -1;
        for (int i = 0; i < adapter.getCount(); i++) {
            adapterId = adapter.getItemId(i);
            if (adapterId == id) {
                return i;
            }
        }

        return -1;
    }
}
