package codes.biscuit.skyblocklib.skyblock;

import codes.biscuit.skyblocklib.calendar.SkyblockCalendar;

public class Skyblock {

    private boolean onSkyblock = false;
    private SkyblockCalendar calendar;

    /**
     * Get the current skyblock date and time.
     *
     * @return the skyblock calendar object.
     */
    public SkyblockCalendar getCalendar() {
        return calendar;
    }

    /**
     * @return Whether the player is currently on skyblock or not.
     */
    public boolean isOnSkyblock() {
        return onSkyblock;
    }
}
