package codes.biscuit.skyblocklib.listener;

import codes.biscuit.skyblocklib.SkyblockLib;
import codes.biscuit.skyblocklib.event.SkyblockAbilityEvent;
import codes.biscuit.skyblocklib.model.SkyblockItemAbility;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Listens to chat messages to read information from them and fire corresponding Events through the EventBus
 */
public class ChatListener {

    private static final Pattern CHAT_ABILITY_PATTERN = Pattern.compile("§r§aUsed §r§6([A-Za-z ]+)§r§a! §r§b\\([0-9]+ Mana\\)§r");

    private final SkyblockLib skyblockLib;
    private final EventBus eventBus;

    public ChatListener(SkyblockLib skyblockLib, EventBus eventBus) {
        this.skyblockLib = skyblockLib;
        this.eventBus = eventBus;
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        final String unformattedText = event.message.getUnformattedText();
        final String formattedText = event.message.getFormattedText();

        if(event.type == 0) { // normal chat message
            Matcher matcher = CHAT_ABILITY_PATTERN.matcher(formattedText);
            if (matcher.matches()) {
                // Fire SkyblockAbilityEvent if ability matching that name was found
                final String abilityName = matcher.group(1);
                final Optional<SkyblockItemAbility> ability = skyblockLib.getItemAbilityFile().findAbilityByName(abilityName);
                ability.ifPresent(skyblockItemAbility -> eventBus.post(new SkyblockAbilityEvent(skyblockItemAbility)));
            }
        }
    }

}