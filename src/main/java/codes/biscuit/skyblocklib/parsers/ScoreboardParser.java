package codes.biscuit.skyblocklib.parsers;

import codes.biscuit.skyblocklib.SkyblockLib;
import codes.biscuit.skyblocklib.calendar.SkyblockCalendar;
import codes.biscuit.skyblocklib.event.SkyblockTimeUpdateEvent;
import codes.biscuit.skyblocklib.utils.TextUtils;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import java.util.List;

/**
 * Class for parsing information from the Skyblock scoreboard
 */
public class ScoreboardParser {

    private final EventBus EVENT_BUS;

    public ScoreboardParser(EventBus EVENT_BUS) {
        this.EVENT_BUS = EVENT_BUS;
    }

    /**
     * Parses the scoreboard lines for useful information.
     *
     * @param scoreboardLines List of lines in the Scoreboard
     */
    public void parseScoreboard(List<String> scoreboardLines) {
        String dateString = null;
        String timeString = null;
        for (String scoreboardLine : scoreboardLines) {
            String strippedLine = TextUtils.stripColor(scoreboardLine).trim();
            strippedLine = TextUtils.keepLettersAndNumbersOnly(strippedLine);

            if (strippedLine.endsWith("am") || strippedLine.endsWith("pm")) {
                timeString = strippedLine;
            }
            if(strippedLine.endsWith("st")
                    || strippedLine.endsWith("nd")
                    || strippedLine.endsWith("rd")
                    || strippedLine.endsWith("th")) {
                dateString = strippedLine;
            }
        }

        updateCalendar(dateString, timeString);
    }

    /**
     * Update the main calendar and fire a {@link SkyblockTimeUpdateEvent} whenever the Skyblock time changes.
     *
     * @param dateString Date string
     * @param timeString Time string
     */
    private void updateCalendar(String dateString, String timeString) {
        SkyblockCalendar skyblockCalendar = SkyblockCalendar.parse(dateString, timeString);
        if(skyblockCalendar != null) {
            SkyblockCalendar mainCalendar = SkyblockLib.getInstance().getSkyblock().getCalendar();
            if(!mainCalendar.equals(skyblockCalendar)) {
                EVENT_BUS.post(new SkyblockTimeUpdateEvent(mainCalendar, skyblockCalendar));
                mainCalendar.setMonth(skyblockCalendar.getMonth());
                mainCalendar.setDay(skyblockCalendar.getDay());
                mainCalendar.setHour(skyblockCalendar.getHour());
                mainCalendar.setMinute(skyblockCalendar.getMinute());
                mainCalendar.setPeriod(skyblockCalendar.getPeriod());
            }
        }
    }

}
