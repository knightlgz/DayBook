package k3.daybook.util;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Kyson LEE
 */

public class TimeUtil {

    private static Calendar mCalendar = Calendar.getInstance();

    private static final long MillisecondPerSecond = 1000;
    private static final long SecondPerMinute = 60;
    private static final long MinutePerHour = 60;
    private static final long HourPerDay = 24;
    private static final long DayPerWeek = 7;
    private static final long WEEK = DayPerWeek * HourPerDay * MinutePerHour * SecondPerMinute
            * MillisecondPerSecond;

    public static long oneWeekAgo() {
        long now = System.currentTimeMillis();
        return now - WEEK;
    }

    @NonNull
    public static String convertYMD(long dateStamp) {
        Date date = new Date(dateStamp);
        mCalendar.setTime(date);
        return mCalendar.get(Calendar.YEAR) + "/" + mCalendar.get(Calendar.MONTH) + "/"
                + mCalendar.get(Calendar.DATE);
    }

    public static long lastAccountingDay(int day) {
        Date now = new Date(System.currentTimeMillis());
        mCalendar.setTime(now);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        if (month == 1) {
            month = 12;
            year -= 1;
        } else {
            month -= 1;
        }
        Date lastAccountingDay = new Date(year, month, day);
        return lastAccountingDay.getTime();
    }
}
