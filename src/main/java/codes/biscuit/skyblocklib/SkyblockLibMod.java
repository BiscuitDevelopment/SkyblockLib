package codes.biscuit.skyblocklib;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static codes.biscuit.skyblocklib.SkyblockLibMod.*;

@Mod(modid = MOD_ID, version = VERSION, name = MOD_NAME, clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
public class SkyblockLibMod {

    static final String MOD_ID = "skyblocklib";
    static final String MOD_NAME = "SkyblockLib";
    static final String VERSION = "1.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        if((boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) { //isDevEnvironment from SBA
            SkyblockLib.start();
        }
    }
}
