package codes.biscuit.skyblocklib.player;

public class Attributes {

    private int defence = 0;
    private int health = 100;
    private int maxHealth = 100;
    private int mana = 100;
    private int maxMana = 100;
    private int wandHealing = 0;

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setWandHealing(int wandHealing) {
        this.wandHealing = wandHealing;
    }

    /**
     * Get the player's Skyblock defence. The default on a new profile is 0.
     *
     * @return The player's Skyblock defence.
     */
    public int getDefence() {
        return defence;
    }

    /**
     * Get the player's Skyblock health. The default on a new profile is 100.
     *
     * @return The player's Skyblock health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * The the player's maximum Skyblock health. The default on a new profile is 100.
     *
     * @return The player's maximum Skyblock health.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get the player's mana. The default on a new profile is 100.
     *
     * @return The player's mana.
     */
    public int getMana() {
        return mana;
    }

    /**
     * Get the players maximum mana. The default on a new profile is 100.
     *
     * @return The player's maximum mana.
     */
    public int getMaxMana() {
        return maxMana;
    }

    /**
     * Get the health per second the player is healed by a healing wand or {@code 0} if no wand is active.
     *
     * @return Healing wand health per second
     */
    public int getWandHealing() {
        return wandHealing;
    }
}
