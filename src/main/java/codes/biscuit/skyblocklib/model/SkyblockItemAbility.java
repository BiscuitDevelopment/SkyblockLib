package codes.biscuit.skyblocklib.model;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents a Skyblock Item ability.
 * This class is meant to only represent values provided from the item-abilities.json file and
 * is thus immutable.
 */
public final class SkyblockItemAbility {

    @Expose
    private final String name;
    @Expose
    private final int cooldownSeconds;
    @Expose
    private final int manaCost;
    @Expose
    private final String itemId;
    @Expose
    private final String[] otherItems;

    /**
     * Create a new Skyblock Ability
     *
     * @param name Non-null and non-empty ability name
     * @param cooldownSeconds Cooldown in positive or 0 seconds
     * @param manaCost Mana cost, must be positive or 0
     * @param itemId Non-null and non-empty Skyblock Item ID
     * @param otherItems Nullable list of other Item IDs that have this ability
     */
    public SkyblockItemAbility(String name, int cooldownSeconds, int manaCost, String itemId, String[] otherItems) {
        Preconditions.checkArgument(name != null);
        Preconditions.checkArgument(!name.isEmpty());
        Preconditions.checkArgument(cooldownSeconds >= 0);
        Preconditions.checkArgument(manaCost >= 0);
        Preconditions.checkArgument(itemId != null);
        Preconditions.checkArgument(!itemId.isEmpty());

        this.name = name;
        this.cooldownSeconds = cooldownSeconds;
        this.manaCost = manaCost;
        this.itemId = itemId;
        this.otherItems = otherItems;
    }

    /**
     * @return The ability's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The ability's cooldown in seconds
     */
    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    /**
     * @return The ability's Mana cost
     */
    public int getManaCost() {
        return manaCost;
    }

    /**
     * The ID of the item associated to this ability.
     *
     * @return The item ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * The optional list of other items associated to this ability.
     * This may be empty when the ability only has one associated item.
     * Example of multiple items per ability in Skyblock:
     * Leap is the ability of both the Leaping Sword and the Silk Edge Sword.
     *
     * @return Optional list of item IDs
     */
    public Optional<String[]> getOtherItems() {
        return Optional.ofNullable(otherItems);
    }

    @Override
    public String toString() {
        return "SkyblockItemAbility{" +
                "name='" + name + '\'' +
                ", cooldownSeconds=" + cooldownSeconds +
                ", manaCost=" + manaCost +
                ", itemId='" + itemId + '\'' +
                (getOtherItems().isPresent() ? ", otherItems=" + Arrays.toString(otherItems) : "") +
                '}';
    }
}
