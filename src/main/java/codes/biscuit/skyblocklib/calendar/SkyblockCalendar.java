package codes.biscuit.skyblocklib.calendar;

public class SkyblockCalendar {

    private SkyblockMonth month;
    private int day = 1;
    private int hour = 1;
    private int minute = 1;

    /**
     * @return The current skyblock month.
     */
    public SkyblockMonth getMonth() {
        return month;
    }

    /**
     * @return The current skyblock day.
     */
    public int getDay() {
        return day;
    }

    /**
     * @return The current skyblock hour.
     */
    public int getHour() {
        return hour;
    }

    /**
     * @return The current skyblock minute.
     */
    public int getMinute() {
        return minute;
    }
}
