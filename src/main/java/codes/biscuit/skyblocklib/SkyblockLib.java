package codes.biscuit.skyblocklib;

import codes.biscuit.skyblocklib.listener.EventListener;
import net.minecraftforge.common.MinecraftForge;

public class SkyblockLib {

    private static SkyblockLib instance = null;

    // each mod
    public static void start() {
        instance = new SkyblockLib();
        MinecraftForge.EVENT_BUS.register(new EventListener());
        // start listeners and stuff
    }

    public static SkyblockLib getInstance() {
        if (instance == null) start();
        return instance;
    }
}
