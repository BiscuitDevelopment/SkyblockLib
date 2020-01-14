package codes.biscuit.skyblocklib.utils;

import codes.biscuit.skyblocklib.parsers.ActionBarParser;

/**
 * Consumers determine how action bar sections are displayed.
 * The {@link ActionBarParser} accepts consumers for different sections to customize the action bar display.
 */
@FunctionalInterface
public interface ActionBarConsumer {

    /**
     * Consumer that removes every section it receives by just returning {@code null}.
     */
    ActionBarConsumer REMOVE_TEXT_CONSUMER = (section) -> null;

    /**
     * After a section is parsed, this is called with the <em>initial, unformatted</em> section text.
     * The consumer is then expected to say how the section should continue to be displayed. To completely remove
     * it from the action bar, return {@code null}. To not alter it at all just return {@code section}.
     *
     * @param section Unformatted section text.
     * @return New display string or {@code null} to remove
     */
    String consumeSection(String section);
}
