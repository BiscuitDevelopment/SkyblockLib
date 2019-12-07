package codes.biscuit.skyblocklib;

import codes.biscuit.skyblocklib.listener.ChatListener;
import codes.biscuit.skyblocklib.listener.EventListener;
import codes.biscuit.skyblocklib.managers.ItemAbilityFile;
import net.minecraftforge.common.MinecraftForge;

public class SkyblockLib {

    private static SkyblockLib instance = null;

    // each mod
    public static void start() {
        instance = new SkyblockLib();
    }

    public static SkyblockLib getInstance() {
        if (instance == null) start();
        return instance;
    }

    private ItemAbilityFile itemAbilityFile;

    private SkyblockLib() {
        itemAbilityFile = ItemAbilityFile.fromFileAndRemote();

        MinecraftForge.EVENT_BUS.register(new EventListener());
        MinecraftForge.EVENT_BUS.register(new ChatListener(this, MinecraftForge.EVENT_BUS));
        // start listeners and stuff
    }

    public ItemAbilityFile getItemAbilityFile() {
        return itemAbilityFile;
    }
}
