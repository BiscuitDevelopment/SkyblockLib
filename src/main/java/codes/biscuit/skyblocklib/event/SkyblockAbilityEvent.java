package codes.biscuit.skyblocklib.event;

import codes.biscuit.skyblocklib.model.SkyblockItemAbility;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SkyblockAbilityEvent extends Event {

    private final SkyblockItemAbility ability;

    /**
     * This event is fired when a player uses an item ability.
     *
     * @param ability The ability that was used.
     */
    public SkyblockAbilityEvent(SkyblockItemAbility ability) {
        this.ability = ability;
    }

    /**
     * @return The ability that was used.
     */
    public SkyblockItemAbility getAbility() {
        return ability;
    }
}
