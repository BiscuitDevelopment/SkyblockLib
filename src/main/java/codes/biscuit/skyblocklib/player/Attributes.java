package codes.biscuit.skyblocklib.player;

public class Attributes {

    private int defence = 0;
    private int health = 100;
    private int mana = 100;

    /**
     * Get the player's skyblock defence. The default on a new profile is 0.
     *
     * @return The player's skyblock defence.
     */
    public int getDefence() {
        return defence;
    }

    /**
     * Get the player's skyblock health. The default on a new profile is 100.
     *
     * @return The player's skyblock health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the player's mana. The default on a new profile is 100.
     *
     * @return The player's mana.
     */
    public int getMana() {
        return mana;
    }
}
