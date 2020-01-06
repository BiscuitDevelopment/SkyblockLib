package codes.biscuit.skyblocklib.managers;

import codes.biscuit.skyblocklib.model.SkyblockItemAbility;
import codes.biscuit.skyblocklib.utils.SBLLog;
import codes.biscuit.skyblocklib.utils.Utils;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Manages the item abilities file and exposes the possibility to {@link #findAbilityByName(String) get abilities by name}.
 * Create a new instance that loads abilities from the local file and remote repository with {@link #fromFileAndRemote()}.
 * <br>
 * The intention behind this is to have a dynamic way of loading abilities, so the remote repository can update the values
 * without needing to release a new version when the abilities change or new ones get introduced in Skyblock.
 */
public class ItemAbilityFile {

    private static final String FILE_NAME = "item-abilities.json";

    private final Map<String, SkyblockItemAbility> itemAbilityMap = new HashMap<>();
    private final Gson gson = new Gson();
    private final Type itemAbilityMapType = new TypeToken<Map<String, SkyblockItemAbility>>() {}.getType();

    /**
     * Create a new instance by first loading from the local file in the resources folder and then fetching the remote
     * file to add onto/override the local values.
     *
     * @return ItemAbilityFile instance
     */
    public static ItemAbilityFile fromFileAndRemote() {
        return new ItemAbilityFile(true);
    }

    /**
     * Create a new instance with a given map of abilities.
     *
     * @param abilityMap Non-null map of abilities
     * @return ItemAbilityFile instance
     */
    public static ItemAbilityFile fromAbilityMap(Map<String, SkyblockItemAbility> abilityMap) {
        return new ItemAbilityFile(abilityMap);
    }

    private ItemAbilityFile(boolean loadRemote) {
        loadLocalFile();
        if(loadRemote) {
            fetchRemoteFile();
        }
    }

    private ItemAbilityFile(Map<String, SkyblockItemAbility> abilityMap) {
        Preconditions.checkArgument(abilityMap != null);
        itemAbilityMap.putAll(abilityMap);
    }

    /**
     * Loads abilities from local file.
     * If this fails for whatever reason the {@link #itemAbilityMap} will just be empty
     */
    private void loadLocalFile() {
        SBLLog.info("Loading local item abilities...");
        try {
            InputStream fileStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);
            Reader fileReader = new InputStreamReader(Objects.requireNonNull(fileStream));
            Map<String, SkyblockItemAbility> abilityMap = gson.fromJson(fileReader, itemAbilityMapType);
            if (abilityMap != null) {
                SBLLog.info("Loaded local item abilities: %s", abilityMap);
                itemAbilityMap.putAll(abilityMap);
            }
        } catch (Exception ex) {
            if (ex instanceof NullPointerException) {
                SBLLog.warning("Couldn't read local %s file: File not found", FILE_NAME);
            } else if (ex instanceof JsonSyntaxException) {
                SBLLog.warning("Couldn't read local %s file: Malformed JSON", FILE_NAME);
            } else {
                // Most likely NumberFormatException or something like that when wrong values are entered for the
                // expected SkyblockItemAbility fields
                SBLLog.warning("Couldn't read local %s file. Probably wrongly formatted ability objects.", FILE_NAME);
            }
            ex.printStackTrace();
        }
    }

    /**
     * Attempts to fetch the remote file and load them on top of the {@link #itemAbilityMap}
     */
    private void fetchRemoteFile() {
        SBLLog.info("Attempting to fetch remote item abilities...");
        try {
            URL url = new URL(Utils.getRemoteResourceFileUrl(FILE_NAME));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "SkyblockLib");

            SBLLog.info("Got response code " + connection.getResponseCode());

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
            }
            connection.disconnect();
            Map<String, SkyblockItemAbility> abilityMap = gson.fromJson(response.toString(), itemAbilityMapType);
            if (abilityMap != null) {
                SBLLog.info("Loaded remote item abilities: %s", abilityMap);
                itemAbilityMap.putAll(abilityMap);
            } else {
                SBLLog.info("No remote item abilities found.");
            }
        } catch (JsonParseException | IllegalStateException | IOException ex) {
            SBLLog.warning("There was an error loading the remote abilities file: %s", ex.getCause());
        }
    }

    /**
     * Find an ability object by it's name, for example when it gets detected in a chat message.
     * Returns an optional that either holds the ability or is empty if none was found matching the name.
     * Lookup is case-sensitive!
     *
     * @param abilityName Name of the ability to find
     * @return Optional that holds the found ability or is empty
     */
    public Optional<SkyblockItemAbility> findAbilityByName(String abilityName) {
        return Optional.ofNullable(itemAbilityMap.get(abilityName));
    }

}
