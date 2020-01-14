package codes.biscuit.skyblocklib.managers;

import codes.biscuit.skyblocklib.item.PowerOrb;
import codes.biscuit.skyblocklib.item.PowerOrbType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class for managing active PowerOrbs around the player.
 * {@link #put(PowerOrbType, int) Insert} orbs that get detected and {@link #get() get} the
 * active orb with the {@link PowerOrbType#getPriority()}  highest priority}.
 */
public class PowerOrbManager {

    private static final PowerOrbManager instance = new PowerOrbManager();

    /**
     * @return The PowerOrbManager instance
     */
    public static PowerOrbManager getInstance() {
        return instance;
    }

    private Map<PowerOrbType, PowerOrb> powerOrbEntryMap = new HashMap<>();

    /**
     * Put any detected orb into the list of active orbs.
     *
     * @param powerOrbType Detected PowerOrb type
     * @param seconds Seconds the orb has left before running out
     */
    public void put(PowerOrbType powerOrbType, int seconds) {
        powerOrbEntryMap.put(powerOrbType, new PowerOrb(powerOrbType, seconds));
    }

    /**
     * Get the active orb with the highest priority. Priority is based on the value defined in
     * {@link PowerOrbType#getPriority()} and the returned orb is guaranteed to have been active at least 100ms ago.
     *
     * @return Highest priority orb or null if none is around
     */
    public PowerOrb get() {
        Optional<Map.Entry<PowerOrbType, PowerOrb>> max = powerOrbEntryMap.entrySet().stream()
                .filter(powerOrbEntryEntry -> powerOrbEntryEntry.getValue().getTimestamp() + 100 > System.currentTimeMillis())
                .max(Comparator.comparing(Map.Entry::getKey));

        return max.map(Map.Entry::getValue).orElse(null);
    }

}
