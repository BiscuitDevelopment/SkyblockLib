package codes.biscuit.skyblocklib.event;

import codes.biscuit.skyblocklib.model.SkyblockItemAbility;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SkyblockAbilityEvent extends Event {

    private final SkyblockItemAbility ability;

    public SkyblockAbilityEvent(SkyblockItemAbility ability) {
        this.ability = ability;
    }

    public SkyblockItemAbility getAbility() {
        return ability;
    }
}
