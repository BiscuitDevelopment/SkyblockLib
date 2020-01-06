package codes.biscuit.skyblocklib.calendar;

import codes.biscuit.skyblocklib.utils.TextUtils;
import com.google.common.base.Preconditions;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a date in the Skyblock calendar.
 * The Skyblock calendar is structured into months, days, hours and minutes on a 12-hour clock.
 * There are 12 Skyblock months based on the seasons Spring, Summer, Fall and Winter with
 * the months being Early, normal and Late variations of those.
 *
 * An example Skyblock date would be:
 * <p>
 *     Early Winter 10th, 9:40am
 * </p>
 * {@link #toString()} formats the date like that too.
 */
public class SkyblockCalendar {

    private static final Pattern DATE_PATTERN = Pattern.compile("(?<month>[\\w ]+) (?<day>\\d{1,2})(th|st|nd|rd)");
    private static final Pattern TIME_PATTERN = Pattern.compile("(?<hour>\\d{1,2}):(?<minute>\\d\\d)(?<period>am|pm)");

    /**
     * Parses a {@link SkyblockCalendar} from a string containing the date and a string containing the time, as displayed in the
     * Skyblock scoreboard.
     * As such the date string must be formatted like {@code [Month] [Day][Ordinal]} and the time string must be
     * formatted like {@code [Hour]:[Minute][am|pm]}.
     * If parsing fails, {@code null} is returned.
     *
     * @param dateString String containing the date
     * @param timeString String containing the time
     * @return The parsed {@link SkyblockCalendar} or {@code null}
     */
    public static SkyblockCalendar parse(String dateString, String timeString) {
        if(dateString == null
                || timeString == null
                || dateString.isEmpty()
                || timeString.isEmpty()) {
            return null;
        }

        Matcher dateMatcher = DATE_PATTERN.matcher(dateString.trim());
        Matcher timeMatcher = TIME_PATTERN.matcher(timeString.trim());

        Integer day = null;
        Integer hour = null;
        Integer minute = null;
        String monthString = null;
        String period = null;

        try {
            if (dateMatcher.find()) {
                monthString = dateMatcher.group("month");
                day = Integer.parseInt(dateMatcher.group("day"));
            }
            if (timeMatcher.find()) {
                hour = Integer.parseInt(timeMatcher.group("hour"));
                minute = Integer.parseInt(timeMatcher.group("minute"));
                period = timeMatcher.group("period");
            }
        } catch(NumberFormatException ex) {
            return null;
        }

        SkyblockMonth month = SkyblockMonth.fromScoreboardName(monthString);
        if(month != null
                && day != null
                && hour != null
                && period != null) {
            return new SkyblockCalendar(month, day, hour, minute, period);
        } else {
            return null;
        }
    }

    private SkyblockMonth month;
    private int day;
    private int hour;
    private int minute;
    private String period;

    /**
     * Create a new Skyblock Calendar from specific values.
     *
     * @param month The Skyblock month. Cannot be null
     * @param day The day in the month. Must be between 1 and 31
     * @param hour The amount of hours. Must be between 0 and 12
     * @param minute The amount of minutes. Must be between 0 and 59 and a multiple of 10
     * @param period The period. Must be "am" or "pm"
     *
     * @throws IllegalArgumentException If any preconditions are not met
     */
    public SkyblockCalendar(SkyblockMonth month, int day, int hour, int minute, String period) {
        setMonth(month);
        setDay(day);
        setHour(hour);
        setMinute(minute);
        setPeriod(period);
    }

    /**
     * Create a new Skyblock Calendar with the values of another calendar object.
     *
     * @param calendar Calendar to copy values from
     */
    public SkyblockCalendar(SkyblockCalendar calendar) {
        this.month = calendar.getMonth();
        this.day = calendar.getDay();
        this.hour = calendar.getHour();
        this.minute = calendar.getMinute();
        this.period = calendar.getPeriod();
    }

    /**
     * @return The current Skyblock month
     */
    public SkyblockMonth getMonth() {
        return month;
    }

    /**
     * Day in the current Skyblock month so between 1 and 31
     * @return The current day in the Skyblock month
     */
    public int getDay() {
        return day;
    }

    /**
     * Current amount of hours between 0 and 12.
     * See {@link #getPeriod()} to know in which period they are.
     *
     * @return The current Skyblock hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Current amount of minutes between 0 and 59 and always a multiple of 10.
     *
     * @return The current Skyblock minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Get the current period as in "am" or "pm".
     *
     * @return The current Skyblock time period.
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Sets the current day in the month.
     * Must be between 1 and 31.
     *
     * @param day Day in the month
     */
    public void setDay(int day) {
        Preconditions.checkArgument(day > 0 && day <= 31);
        this.day = day;
    }

    /**
     * Set the current hours.
     * Must be between 0 and 12.
     * Use {@link #setPeriod(String)} to set the time period.
     *
     * @param hour Amount of hours
     */
    public void setHour(int hour) {
        Preconditions.checkArgument(hour >= 0 && hour <= 12);
        this.hour = hour;
    }

    /**
     * Set the current minutes.
     * Must be between 0 and 59 and a multiple of 10.
     *
     * @param minute Amount of minutes
     */
    public void setMinute(int minute) {
        Preconditions.checkArgument(minute >= 0 && minute < 60);
        Preconditions.checkArgument(minute % 10 == 0);
        this.minute = minute;
    }

    /**
     * Set the current Skyblock month.
     * Cannot be null.
     *
     * @param month Current month
     */
    public void setMonth(SkyblockMonth month) {
        Preconditions.checkArgument(month != null);
        this.month = month;
    }

    /**
     * Set the current time period.
     * Must be "am" or "pm".
     *
     * @param period Time period string
     */
    public void setPeriod(String period) {
        Preconditions.checkArgument(period != null);
        Preconditions.checkArgument(period.equals("am") || period.equals("pm"));
        this.period = period;
    }

    /**
     * Formats the date like this
     * <p><code>
     *      [Month] [Day][Ordinal], [Hour]:[Minute][Period]
     * </code></p>
     * or as a real example:
     * <p>
     *     Early Winter 10th, 9:40am
     * </p>
     * @return Formatted date
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return String.format("%s %s, %d:%s%s",
                month.getScoreboardString(),
                day + TextUtils.getOrdinalSuffix(day),
                hour,
                decimalFormat.format(minute),
                period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkyblockCalendar that = (SkyblockCalendar) o;

        return day == that.day
                && hour == that.hour
                && minute == that.minute
                && month == that.month
                && period.equals(that.period);
    }

    @Override
    public int hashCode() {
        int result = month.hashCode();
        result = 31 * result + day;
        result = 31 * result + hour;
        result = 31 * result + minute;
        result = 31 * result + period.hashCode();
        return result;
    }
}
