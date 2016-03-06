package org.ahomewithin.ahomewithin.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by barbara on 3/5/16.
 */
public class DateHelper {
    // public static final String FORMAT_MMDDYYYY = "MM/dd/yyyy";
//    public static final String FORMAT_YYYYMMDD = "yyyyMMdd"; //"YYYYMMDD";
//    public static final String FORMAT_MONTH_DAY_YEAR = "MM/dd/yyyy";
//    public static final String FORMAT_LONG = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"; // "Mon Apr 01 21:16:23 +0000 2014"

    public static Long getTimestamp(String strDate, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format, Locale.ENGLISH);
        sf.setLenient(true);
        try {
            long dateMillis = sf.parse(strDate).getTime();
            return new Long(dateMillis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestampToString(Long timestamp, String format) {
        CharSequence str = "";
        if (timestamp != null) {
            str = android.text.format.DateFormat.format(format, timestamp);
        }
        return str.toString();
    }

    public static String getRelativeTimeAgo(long timestamp) {
       return DateUtils.getRelativeTimeSpanString(timestamp,
         System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }

    public static Date parseDate(String strDate, String format) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
