package codes.biscuit.skyblocklib.model;

/**
 * Represents a skill progress message in the action bar that looks something like this
 * <p>{@code +[increase] [skill name] ([current progress]/[max progress])}</p>
 * or in a concrete example:
 * <p>{@code +3.5 Combat (355/1,000)}</p>
 */
public class SkillUpdate {

    private final String skillName;
    private final double currentProgress;
    private final double maxProgress;
    private final double increase;
    private final long timestamp;

    /**
     * Create a new skill update.
     *
     * @param skillName Name of the skill
     * @param currentProgress Current XP of that skill
     * @param maxProgress XP until the next level of that skill
     * @param increase XP that was just earned
     */
    public SkillUpdate(String skillName, double currentProgress, double maxProgress, double increase) {
        this.skillName = skillName;
        this.currentProgress = currentProgress;
        this.maxProgress = maxProgress;
        this.increase = increase;
        timestamp = System.currentTimeMillis();
    }

    /**
     * Name like "Combat", "Mining", "Farming", "Foraging", "Alchemy", "Enchanting", "Runecrafting", "Carpentry".
     *
     * @return The name of the progressed skill
     */
    public String getSkillName() {
        return skillName;
    }

    /**
     * @return The current XP of that skill
     */
    public double getCurrentProgress() {
        return currentProgress;
    }

    /**
     * @return The amount of XP needed to reach the next level
     */
    public double getMaxProgress() {
        return maxProgress;
    }

    /**
     * @return The XP that was just earned
     */
    public double getIncrease() {
        return increase;
    }

    /**
     * @return Timestamp when this update occurred in milliseconds
     */
    public long getTimestamp() {
        return timestamp;
    }
}
