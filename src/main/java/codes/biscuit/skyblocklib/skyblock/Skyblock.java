package codes.biscuit.skyblocklib.skyblock;

import codes.biscuit.skyblocklib.calendar.SkyblockCalendar;
import codes.biscuit.skyblocklib.calendar.SkyblockMonth;

public class Skyblock {

    private boolean onSkyblock = false;
    private final SkyblockCalendar calendar = new SkyblockCalendar(SkyblockMonth.EARLY_SPRING, 1, 0, 0, "am");

    /**
     * Get the current skyblock date and time.
     * This object is kept up to date while the player is on Skyblock.
     * It should not be changed outside SkyblockLib!
     *
     * @return the Skyblock calendar object.
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

    /**
     * <b>This should not be used outside SkyblockLib!</b><br>
     * Sets whether the player is currently on Skyblock.
     *
     * @param onSkyblock Whether the player is on Skyblock
     */
    public void setOnSkyblock(boolean onSkyblock) {
        this.onSkyblock = onSkyblock;
    }
}
