package codes.biscuit.skyblocklib.item;

/**
 * Represents an active PowerOrb somewhere around the player, with a {@link #getPowerOrbType() type}
 * and {@link #getSeconds() remaining active time}.
 */
public class PowerOrb {

    private final PowerOrbType powerOrbType;
    private final int seconds;
    private final long timestamp;

    public PowerOrb(PowerOrbType powerOrbType, int seconds) {
        this.powerOrbType = powerOrbType;
        this.seconds = seconds;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * @return PowerOrb type
     */
    public PowerOrbType getPowerOrbType() {
        return powerOrbType;
    }

    /**
     * @return Seconds the orb has left before running out
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * @return The timestamp when the orb was last updated
     */
    public long getTimestamp() {
        return timestamp;
    }
}
