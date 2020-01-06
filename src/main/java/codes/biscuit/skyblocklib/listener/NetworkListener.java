package codes.biscuit.skyblocklib.listener;

import codes.biscuit.skyblocklib.event.SkyblockLeftEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

/**
 * Listens to Forge network events
 */
public class NetworkListener {

    private final EventBus EVENT_BUS;

    public NetworkListener(EventBus EVENT_BUS) {
        this.EVENT_BUS = EVENT_BUS;
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        // Leave Skyblock when the player disconnects
        EVENT_BUS.post(new SkyblockLeftEvent());
    }

}
