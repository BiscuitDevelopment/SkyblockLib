package codes.biscuit.skyblocklib.model;

/**
 * Represents the item charges that Zombie Sword, Ornate Zombie Sword and Scorpion Foil have.
 * (Scorpion Foil calls them tickers).
 * There is a total amount of charges, and an amount of available (unused) charges.
 */
public class ItemCharges {

    private final int available;
    private final int total;

    /**
     * Create a new item charge
     *
     * @param available Available (unused) charges
     * @param total Total amount of charges
     */
    public ItemCharges(int available, int total) {
        this.available = available;
        this.total = total;
    }

    /**
     * @return The amount of available (unused) charges
     */
    public int getAvailable() {
        return available;
    }

    /**
     * @return The total amount of charges
     */
    public int getTotal() {
        return total;
    }
}
