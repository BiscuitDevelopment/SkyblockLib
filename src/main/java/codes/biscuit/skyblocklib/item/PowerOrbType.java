package codes.biscuit.skyblocklib.item;

/**
 * Represents the Power Orbs introduced with the Slayer Update and unlocked through the Wolf slayer quests.
 */
public enum PowerOrbType {

    RADIANT(0, "§aRadiant", 0.01, 0, 0, 0, 18),
    MANA_FLUX(1, "§9Mana Flux", 0.02, 0.5, 10, 0, 18),
    OVERFLUX(2, "§5Overflux", 0.025, 1, 25, 0.05, 18);

    private final int priority;
    private final String display;
    private final double healthRegen;
    private final double manaRegen;
    private final int strength;
    private final double healIncrease;
    private final int radiusSquared;

    PowerOrbType(int priority, String display, double healthRegen, double manaRegen, int strength, double healIncrease, int radius) {
        this.priority = priority;
        this.display = display;
        this.healthRegen = healthRegen;
        this.manaRegen = manaRegen;
        this.strength = strength;
        this.healIncrease = healIncrease;
        this.radiusSquared = radius*radius;
    }

    /**
     * Priority indicates which PowerOrb takes effect if the player is within range of multiple.
     * Higher priority takes effect over lower priority.
     *
     * @return This PowerOrb types' priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return The start of the display name of the actual floating orb entity.
     */
    public String getDisplay() {
        return display;
    }

    /**
     * @return The percentage of max health that's regenerated every second.
     */
    public double getHealthRegen() {
        return healthRegen;
    }

    /**
     * @return The percentage of mana regeneration increase given by the orb.
     */
    public double getManaRegen() {
        return manaRegen;
    }

    /**
     * @return The amount of strength given by the orb.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @return The percentage by which other healing effects are increased
     */
    public double getHealIncrease() {
        return healIncrease;
    }

    /**
     * @return The orbs effective radius, it is squared to be compared with a squared distance.
     */
    public int getRadiusSquared() {
        return radiusSquared;
    }

    /**
     * Check if a distance is within this orbs radius.
     *
     * @param distanceSquared Squared distance from orb entity to player
     * @return Whether that distance is within radius
     */
    public boolean isInRadius(double distanceSquared) {
        return distanceSquared <= radiusSquared;
    }


    /**
     * Match an entity display name against Power Orb entity names to get the corresponding type.
     *
     * @param orbDisplayName Entity display name
     * @return The matching type or null if none was found
     */
    public static PowerOrbType getByOrbDisplayname(String orbDisplayName) {
        for (PowerOrbType powerOrbType : values()) {
            if(orbDisplayName.startsWith(powerOrbType.display)) {
                return powerOrbType;
            }
        }
        return null;
    }
}
