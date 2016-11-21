package k3.daybook.util;

/**
 * @author Kyson LEE
 */

public class TimeUtil {

    private static final long MillisecondPerSecond = 1000;
    private static final long SecondPerMinute = 60;
    private static final long MinutePerHour = 60;
    private static final long HourPerDay = 24;
    private static final long DayPerWeek = 7;
    private static final long WEEK = DayPerWeek * HourPerDay * MinutePerHour * SecondPerMinute * MillisecondPerSecond;

    public static long oneWeekAgo() {
        long now = System.currentTimeMillis();
        return now - WEEK;
    }
}
