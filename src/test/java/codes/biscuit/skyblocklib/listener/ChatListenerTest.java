package codes.biscuit.skyblocklib.listener;

import codes.biscuit.skyblocklib.SkyblockLib;
import codes.biscuit.skyblocklib.event.SkyblockAbilityEvent;
import codes.biscuit.skyblocklib.managers.ItemAbilityFile;
import codes.biscuit.skyblocklib.model.SkyblockItemAbility;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ChatListenerTest {

    private final String TEST_KEY = "Test Ability";
    private final SkyblockItemAbility TEST_ABILITY = new SkyblockItemAbility(TEST_KEY, 10, 100, "TEST_ITEM", null);
    private final Map<String, SkyblockItemAbility> testAbilities = Collections.singletonMap(TEST_KEY, TEST_ABILITY);

    @Mock
    private SkyblockLib skyblockLib;
    @Mock
    private EventBus eventBus;
    private ChatListener chatListener;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(skyblockLib.getItemAbilityFile()).thenReturn(ItemAbilityFile.fromAbilityMap(testAbilities));
        chatListener = new ChatListener(skyblockLib, eventBus);
    }

    @Test
    public void onChatReceived() throws Exception {
        String abilityText = "§r§aUsed §r§6"+TEST_KEY+"§r§a! §r§b(100 Mana)";
        ClientChatReceivedEvent receivedEvent = new ClientChatReceivedEvent((byte)0, new ChatComponentText(abilityText));
        chatListener.onChatReceived(receivedEvent);
        ArgumentMatcher<Event> argumentMatcher = argument -> (argument instanceof SkyblockAbilityEvent) && ((SkyblockAbilityEvent) argument).getAbility() == TEST_ABILITY;
        verify(eventBus, times(1)).post(argThat(argumentMatcher));

        abilityText = "Wrong text";
        receivedEvent = new ClientChatReceivedEvent((byte)0, new ChatComponentText(abilityText));
        chatListener.onChatReceived(receivedEvent);
        Mockito.verifyNoMoreInteractions(eventBus);
    }

}