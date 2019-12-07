package codes.biscuit.skyblocklib.utils;

import net.minecraftforge.fml.common.FMLLog;

/**
 * Logging class that wraps {@link FMLLog} and logs messages with a [SkyblockLib] prefix.
 */
public final class SBLLog {

    private static final String TAG = "SkyblockLib";

    /**
     * Formats logs with the tag prefix in front like
     * {@code [TAG] message}
     *
     * @param message Message string
     * @param args    Optional format arguments for the message
     * @return Formatted message with tag in front
     */
    private static String format(String message, Object... args) {
        return String.format("[%s] %s", TAG, String.format(message, args));
    }

    /**
     * Logs on ERROR level
     *
     * @param message Message string
     * @param args    Optional format arguments for the message
     * @see FMLLog#severe(String, Object...)
     */
    public static void severe(String message, Object... args) {
        FMLLog.severe(format(message, args));
    }

    /**
     * Logs on WARN level
     *
     * @param message Message string
     * @param args    Optional format arguments for the message
     * @see FMLLog#warning(String, Object...)
     */
    public static void warning(String message, Object... args) {
        FMLLog.warning(format(message, args));
    }

    /**
     * Logs on INFO level
     *
     * @param message Message string
     * @param args    Optional format arguments for the message
     * @see FMLLog#info(String, Object...)
     */
    public static void info(String message, Object... args) {
        FMLLog.info(format(message, args));
    }

    /**
     * Logs on DEBUG level
     *
     * @param message Message string
     * @param args    Optional format arguments for the message
     * @see FMLLog#fine(String, Object...)
     */
    public static void fine(String message, Object... args) {
        FMLLog.fine(format(message, args));
    }

    /**
     * Logs on TRACE level
     *
     * @param message Message string
     * @param args    Optional format arguments for the message
     * @see FMLLog#finer(String, Object...)
     */
    public static void finer(String message, Object... args) {
        FMLLog.finer(format(message, args));
    }

}
