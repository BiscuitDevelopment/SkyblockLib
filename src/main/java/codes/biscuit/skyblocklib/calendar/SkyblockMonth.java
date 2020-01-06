package codes.biscuit.skyblocklib.calendar;

public enum SkyblockMonth {

    EARLY_WINTER("Early Winter"),
    WINTER("Winter"),
    LATE_WINTER("Late Winter"),
    EARLY_SPRING("Early Spring"),
    SPRING("Spring"),
    LATE_SPRING("Late Spring"),
    EARLY_SUMMER("Early Summer"),
    SUMMER("Summer"),
    LATE_SUMMER("Late Summer"),
    EARLY_FALL("Early Fall"),
    FALL("Fall"),
    LATE_FALL("Late Fall");

    private String scoreboardString;

    /**
     * Each value represents a month in the skyblock calendar.
     *
     * @param scoreboardString The text shown in the scoreboard for this specific month.
     */
    SkyblockMonth(String scoreboardString) {
        this.scoreboardString = scoreboardString;
    }

    /**
     * @return The text shown in the scoreboard for this specific month.
     */
    public String getScoreboardString() {
        return scoreboardString;
    }
}