package codes.biscuit.skyblocklib.event;

import codes.biscuit.skyblocklib.calendar.SkyblockCalendar;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * This Event is fired whenever a change in Skyblock time is detected, which should be
 * every 10 seconds or 10 Skyblock minutes.
 */
public class SkyblockTimeUpdateEvent extends Event {

    private final SkyblockCalendar previousTime;
    private final SkyblockCalendar newTime;

    public SkyblockTimeUpdateEvent(SkyblockCalendar previousTime, SkyblockCalendar newTime) {
        this.previousTime = new SkyblockCalendar(previousTime);
        this.newTime = new SkyblockCalendar(newTime);
    }

    /**
     * @return The Skyblock time before the update
     */
    public SkyblockCalendar getPreviousTime() {
        return new SkyblockCalendar(previousTime);
    }

    /**
     * @return The Skyblock time after the update
     */
    public SkyblockCalendar getNewTime() {
        return new SkyblockCalendar(newTime);
    }
}
