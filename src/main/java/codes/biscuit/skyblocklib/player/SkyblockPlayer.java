package codes.biscuit.skyblocklib.player;

import codes.biscuit.skyblocklib.item.PowerOrb;
import codes.biscuit.skyblocklib.item.PowerOrbType;
import codes.biscuit.skyblocklib.managers.PowerOrbManager;
import codes.biscuit.skyblocklib.model.ItemCharges;
import codes.biscuit.skyblocklib.model.SkillUpdate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyblockPlayer {

    private final Attributes attributes = new Attributes();
    private SkillUpdate lastSkillUpdate;
    private ItemCharges currentItemCharges;

    /**
     * @return The attributes of a player. This includes their health, defence, and mana.
     */
    @NotNull
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Get the last skill progress update the player received via the action bar.
     * Notice the {@link SkillUpdate#getTimestamp() timestamp} when this update occurred, as it may get stale.
     * Returns {@code null} if no progress update was received yet
     *
     * @return The last skill update
     */
    @Nullable
    public SkillUpdate getLastSkillUpdate() {
        return lastSkillUpdate;
    }

    /**
     * Get the item charges if the player currently holds an item that has such charges, like the Zombie Sword,
     * Ornate Zombie Sword or Scorpion Foil (Scorpion Foil calls them tickers).
     * Returns {@code null} if the player does not hold such an item.
     *
     * @return Charges for the current item if they exist
     */
    @Nullable
    public ItemCharges getCurrentItemCharges() {
        return currentItemCharges;
    }

    /**
     * Skyblock health attribute - not vanilla! Default for new profiles is 100.
     *
     * @return The player's current health
     */
    public int getHealth() {
        return getAttributes().getHealth();
    }

    /**
     * Skyblock health attribute - not vanilla! Default for new profiles is 100.
     *
     * @return The player's maximum health
     */
    public int getMaxHealth() {
        return getAttributes().getMaxHealth();
    }

    /**
     * Skyblock mana attribute. Default for new profiles is 100.
     * <em>Mana does not equal intelligence!</em>
     *
     * @return The player's current mana
     */
    public int getMana() {
        return getAttributes().getMana();
    }

    /**
     * Skyblock mana attribute. Default for new profiles is 100.
     * <em>Mana does not equal intelligence!</em>
     *
     * @return The player's maximum mana
     */
    public int getMaxMana() {
        return getAttributes().getMaxMana();
    }

    /**
     * Skyblock defense attribute. Default for new profiles is 0.
     *
     * @return The player's defense
     */
    public int getDefense() {
        return getAttributes().getDefence();
    }

    /**
     * Amount of health per second the player is regenerating while using a healing wand or {@code 0} if the player
     * is not using one.
     *
     * @return Health per second from active healing wand
     */
    public int getWandHealing() {
        return getAttributes().getWandHealing();
    }

    /**
     * Get the currently active PowerOrb taking effect on the player.
     * Returns null if the player is not in range of an active PowerOrb.
     * If the player is in range of multiple orbs, returns the one with the highest
     * {@link PowerOrbType#getPriority() priority}.
     *
     * @return Active PowerOrb
     */
    @Nullable
    public PowerOrb getActivePowerOrb() {
        return PowerOrbManager.getInstance().get();
    }

    /**
     * <h3>This should not be used outside SkyblockLib!</h3>
     * Set the last skill progress update.
     *
     * @param lastSkillUpdate The skill update
     */
    public void setLastSkillUpdate(SkillUpdate lastSkillUpdate) {
        this.lastSkillUpdate = lastSkillUpdate;
    }

    /**
     * <h3>This should not be used outside SkyblockLib!</h3>
     * Set the current item charges. Set {@code null} to reset
     *
     * @param currentItemCharges The charges
     */
    public void setCurrentItemCharges(ItemCharges currentItemCharges) {
        this.currentItemCharges = currentItemCharges;
    }
}
