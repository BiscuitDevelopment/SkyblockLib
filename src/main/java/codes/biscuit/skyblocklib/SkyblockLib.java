package codes.biscuit.skyblocklib;

import codes.biscuit.skyblocklib.listener.*;
import codes.biscuit.skyblocklib.managers.ItemAbilityFile;
import codes.biscuit.skyblocklib.parsers.ActionBarParser;
import codes.biscuit.skyblocklib.player.SkyblockPlayer;
import codes.biscuit.skyblocklib.skyblock.Skyblock;
import net.minecraftforge.common.MinecraftForge;

public class SkyblockLib {

    private static SkyblockLib instance = null;

    // each mod
    public static void start() {
        instance = new SkyblockLib();
    }

    public static SkyblockLib getInstance() {
        if (instance == null) {
            start();
        }
        return instance;
    }

    private final ItemAbilityFile itemAbilityFile;
    private final Skyblock skyblock;
    private final SkyblockPlayer skyblockPlayer;
    private final ActionBarParser actionBarParser;

    private SkyblockLib() {
        itemAbilityFile = ItemAbilityFile.fromFileAndRemote();
        skyblock = new Skyblock();
        skyblockPlayer = new SkyblockPlayer();
        actionBarParser = new ActionBarParser();

        // start listeners and stuff
        MinecraftForge.EVENT_BUS.register(new EventListener());
        MinecraftForge.EVENT_BUS.register(new NetworkListener(MinecraftForge.EVENT_BUS));
        MinecraftForge.EVENT_BUS.register(new TickListener(MinecraftForge.EVENT_BUS));
        MinecraftForge.EVENT_BUS.register(new ChatListener(this, MinecraftForge.EVENT_BUS));
        MinecraftForge.EVENT_BUS.register(new SkyblockListener());
    }

    public ItemAbilityFile getItemAbilityFile() {
        return itemAbilityFile;
    }

    public ActionBarParser getActionBarParser() {
        return actionBarParser;
    }

    public static Skyblock getSkyblock() {
        return getInstance().skyblock;
    }

    public static SkyblockPlayer getSkyblockPlayer() {
        return getInstance().skyblockPlayer;
    }

    public static boolean isOnSkyblock() {
        return getSkyblock().isOnSkyblock();
    }
}
