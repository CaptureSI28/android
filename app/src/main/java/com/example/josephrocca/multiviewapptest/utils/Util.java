package com.example.josephrocca.multiviewapptest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Util {

    public static String getStringFromDate(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(d);
        return date;
    }


    public static Date getDateFromString(String d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d H:m:s");
        Date date = new Date();
        try {
            date = formatter.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateFromShortString(String d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        try {
            date = formatter.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateFromDataPicker(String d) {
        SimpleDateFormat inFormatter = new SimpleDateFormat("d-M-yyyy");
        SimpleDateFormat outFormatter = new SimpleDateFormat("yyyy-M-d H:m:s");
        Date dateIn = new Date();
        String dateOut = "";

        try {
            dateIn = inFormatter.parse(d);
            dateOut = outFormatter.format(dateIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateOut;
    }
}
