package codes.biscuit.skyblocklib.item;

/**
 * Represents the Power Orbs introduced with the Slayer Update and unlocked through the Wolf slayer quests.
 *
 * @author DidiSkywalker
 */
public enum PowerOrb {

    RADIANT("§aRadiant", 0.01, 0, 0, 0, 18),
    MANA_FLUX("§9Mana Flux", 0.02, 0.5, 10, 0, 18),
    OVERFLUX("§5Overflux", 0.025, 1, 25, 0.05, 18);

    private final String display;
    private final double healthRegen;
    private final double manaRegen;
    private final int strength;
    private final double healIncrease;
    private final int radiusSquared;

    PowerOrb(String display, double healthRegen, double manaRegen, int strength, double healIncrease, int radius) {
        this.display = display;
        this.healthRegen = healthRegen;
        this.manaRegen = manaRegen;
        this.strength = strength;
        this.healIncrease = healIncrease;
        this.radiusSquared = radius*radius;
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
     * @return The percentage of healing that all nearby entities are healed by.
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
    public static PowerOrb getByOrbDisplayname(String orbDisplayName) {
        for (PowerOrb powerOrb : values()) {
            if(orbDisplayName.startsWith(powerOrb.display)) {
                return powerOrb;
            }
        }
        return null;
    }
}
