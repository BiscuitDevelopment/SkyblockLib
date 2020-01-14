package codes.biscuit.skyblocklib.listener;

import codes.biscuit.skyblocklib.SkyblockLib;
import codes.biscuit.skyblocklib.event.SkyblockJoinedEvent;
import codes.biscuit.skyblocklib.event.SkyblockLeftEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Listens to SkyblockLib's Skyblock events
 */
public class SkyblockListener {

    @SubscribeEvent
    public void onSkyblockJoined(SkyblockJoinedEvent event) {
        SkyblockLib.getSkyblock().setOnSkyblock(true);
    }

    @SubscribeEvent
    public void onSkyblockLeft(SkyblockLeftEvent event) {
        SkyblockLib.getSkyblock().setOnSkyblock(false);
    }

}
