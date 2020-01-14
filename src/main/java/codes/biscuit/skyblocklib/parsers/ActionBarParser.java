package codes.biscuit.skyblocklib.parsers;

import codes.biscuit.skyblocklib.SkyblockLib;
import codes.biscuit.skyblocklib.model.ItemCharges;
import codes.biscuit.skyblocklib.model.SkillUpdate;
import codes.biscuit.skyblocklib.player.SkyblockPlayer;
import codes.biscuit.skyblocklib.utils.ActionBarConsumer;
import codes.biscuit.skyblocklib.utils.TextUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to parse action bar messages and get stats and other info out of them.
 * Parsed info is always kept up-to-date elsewhere in SkyblockLib, like the {@link SkyblockPlayer}, but
 * it can be customized whether that information should still be displayed in the action bar.
 * Here is a list of currently parsed information:<br/>
 * <ul>
 * <li>Health and maximum health</li>
 * <li>Mana and maximum mana</li>
 * <li>Defense</li>
 * <li>Skill progress</li>
 * <li>Item charges/tickers</li>
 * </ul>
 * <p>
 * In general, the action bar is divided into sections separated by 3 or more spaces (usually 5, zombie tickers by 4
 * , race timer by 12, trials of fire by 3). Examples can be found below. These sections get parsed individually and can
 * be removed from the displayed action bar individually.
 * <p>
 * By default, SkyblockLib will read info from the action bar, but not remove the text. To do so, you can affect
 * what stays displayed by setting {@link ActionBarConsumer consumers} on specific action bar sections or on every
 * section, the so called <em>general</em> consumer. Consumers are ran after parsing is done, so if you require the parsed
 * information it will already be available wherever it's stored. They determine how the consumed section should
 * be displayed in the action bar, so setting it do display as {@code null} removes it.
 * The easiest way to remove action bar text for a specific section, the health
 * section for example, is to use the {@link ActionBarConsumer#REMOVE_TEXT_CONSUMER} by calling {@link #setHealthConsumer(ActionBarConsumer)}
 * with it.
 * <p>
 * The general consumer is ran after every thing else, on every single section. So you can remove the entire action bar
 * by simply setting {@link ActionBarConsumer#REMOVE_TEXT_CONSUMER} as the general consumer.
 * Or you can run your own checks to cover sections that aren't handled by SkyblockLib by default.<br/>
 * See the documentation for the consumer-setters for more detailed information what their sections look like.
 * <h3>See the {@link ActionBarConsumer} documentation for more detailed information on how exactly to use one!</h3>
 * <p>
 * Here are some example action bars:
 * <p>
 * <blockquote>
 * Normal:                     §c1390/1390❤     §a720§a❈ Defense     §b183/171✎ Mana§r<br/>
 * Normal with Skill XP:       §c1390/1390❤     §3+10.9 Combat (313,937.1/600,000)     §b183/171✎ Mana§r<br/>
 * Zombie Sword:               §c1390/1390❤     §a725§a❈ Defense     §b175/233✎ Mana    §a§lⓩⓩⓩⓩ§2§l§r<br/>
 * Zombie Sword with Skill XP: §c1390/1390❤     §3+10.9 Combat (313,948/600,000)     §b187/233✎ Mana    §a§lⓩⓩⓩⓩ§2§l§r<br/>
 * Normal with Wand:           §c1390/1390❤+§c30▅     §a724§a❈ Defense     §b97/171✎ Mana§r<br/>
 * Normal with Absorption:     §61181/1161❤     §a593§a❈ Defense     §b550/550✎ Mana§r<br/>
 * Normal with Absorp + Wand:  §61181/1161❤+§c20▆     §a593§a❈ Defense     §b501/550✎ Mana§r<br/>
 * End Race:                   §d§lTHE END RACE §e00:52.370            §b147/147✎ Mana§r<br/>
 * Woods Race:                 §A§LWOODS RACING §e00:31.520            §b147/147✎ Mana§r<br/>
 * Trials of Fire:             §c1078/1078❤   §610 DPS   §c1 second     §b421/421✎ Mana§r<br/>
 * </blockquote>
 */
public class ActionBarParser {

    private final Pattern COLLECTIONS_CHAT_PATTERN = Pattern.compile("§.\\+(?:§[0-9a-f])?([0-9,.]+) §?[0-9a-f]?([A-Za-z]+) (\\([0-9.,]+/[0-9.,]+\\))");

    // ----------------------------------
    // | Consumers that allow customization
    // | in terms of displayed sections in the
    // | action bar
    // ----------------------------------
    private ActionBarConsumer healthConsumer;
    private ActionBarConsumer manaConsumer;
    private ActionBarConsumer defenseConsumer;
    private ActionBarConsumer skillConsumer;
    private ActionBarConsumer chargesConsumer;
    private ActionBarConsumer generalConsumer;

    /**
     * <h2>This should not be called outside SkyblockLib!</h2>
     * Parses the stats out of an action bar message and returns a new action bar message without the parsed stats
     * to display instead.
     * Looks for Health, Defense, Mana, Skill XP and parses and uses the stats accordingly.
     * Only removes the stats from the new action bar when consumed.
     *
     * @param actionBar Formatted action bar message
     * @return New action bar without parsed stats.
     */
    public String parseActionBar(String actionBar) {
        // First split the action bar into sections
        String[] splitMessage = actionBar.split(" {3,}");
        // This list holds the text of unused sections that aren't displayed anywhere else in SBA
        // so they can keep being displayed in the action bar
        List<String> unusedSections = new LinkedList<>();

        // health and mana section methods determine if prediction can be disabled, so enable both at first
        // TODO: Predict?
        // set charges to null so it doesn't get stale when the player changes items and the charges aren't visible anymore
        SkyblockLib.getSkyblockPlayer().setCurrentItemCharges(null);
        // set wand healing to 0 so it doesn't get stale
        SkyblockLib.getSkyblockPlayer().getAttributes().setWandHealing(0);

        for (String section : splitMessage) {
            try {
                String sectionReturn = parseSection(section);
                if(generalConsumer != null) {
                    sectionReturn = generalConsumer.consumeSection(section);
                }

                if (sectionReturn != null) {
                    // can either return a string to keep displaying in the action bar
                    // or null to not display them anymore
                    unusedSections.add(sectionReturn);
                }
            } catch(Exception ex) {
                unusedSections.add(section);
            }
        }

        // Finally display all unused sections separated by 5 spaces again
        return String.join("     ", unusedSections);
    }

    /**
     * Parses a single section of the action bar.
     *
     * @param section Section to parse
     * @return Text to keep displaying or null
     */
    private String parseSection(String section) {
        String numbersOnly = TextUtils.keepLettersAndNumbersOnly(section).trim();
        String[] splitStats = numbersOnly.split("/");

        if (section.contains("❤")) {
            // ❤ indicates a health section
            return parseHealth(section, splitStats);
        } else if (section.contains("❈")) {
            // ❈ indicates a defense section
            return parseDefense(section, numbersOnly);
        } else if (section.contains("✎")) {
            return parseMana(section, splitStats);
        } else if (section.contains("(")) {
            return parseSkill(section);
        } else if (section.contains("Ⓞ") || section.contains("ⓩ")) {
            return parseCharges(section);
        }

        return section;
    }

    private String parseHealth(String healthSection, String[] splitStats) {
        // Normal:      §c1390/1390❤
        // With Wand:   §c1390/1390❤+§c30▅
        int newHealth;
        int maxHealth;
        if (healthSection.startsWith("§6")) { // Absorption chances §c to §6. Remove §6 to make sure it isn't detected as a number of health.
            healthSection = healthSection.substring(2);
            splitStats[0] = splitStats[0].substring(1); // One less because the '§' was already removed.
        }
        if (healthSection.contains("+")) {
            // Contains the Wand indicator so it has to be split differently
            String[] splitHealthAndWand = healthSection.split("\\+");
            String[] healthSplit = TextUtils.getNumbersOnly(splitHealthAndWand[0]).split("/");
            newHealth = Integer.parseInt(healthSplit[0]);
            maxHealth = Integer.parseInt(healthSplit[1]);
            int wandHealing = Integer.parseInt(splitHealthAndWand[1]);
            SkyblockLib.getSkyblockPlayer().getAttributes().setWandHealing(wandHealing);
        } else {
            newHealth = Integer.parseInt(splitStats[0]);
            maxHealth = Integer.parseInt(splitStats[1]);
        }

        SkyblockLib.getSkyblockPlayer().getAttributes().setHealth(newHealth);
        SkyblockLib.getSkyblockPlayer().getAttributes().setMaxHealth(maxHealth);
        if(healthConsumer != null) {
            return healthConsumer.consumeSection(healthSection);
        } else {
            return healthSection;
        }
    }

    private String parseMana(String manaSection, String[] splitStats) {
        // §b183/171✎ Mana§r
        int mana = Integer.parseInt(splitStats[0]);
        int maxMana = Integer.parseInt(splitStats[1]);
        SkyblockLib.getSkyblockPlayer().getAttributes().setMana(mana);
        SkyblockLib.getSkyblockPlayer().getAttributes().setMaxMana(maxMana);
        if(manaConsumer != null) {
            return manaConsumer.consumeSection(manaSection);
        } else {
            return manaSection;
        }
    }

    private String parseDefense(String defenseSection, String numbersOnly) {
        // §a720§a❈ Defense
        int defense = Integer.parseInt(numbersOnly);
        SkyblockLib.getSkyblockPlayer().getAttributes().setDefence(defense);

        if(defenseConsumer != null) {
            return defenseConsumer.consumeSection(defenseSection);
        } else {
            return defenseSection;
        }
    }

    private String parseSkill(String skillSection) {
        // §3+10.9 Combat (313,937.1/600,000)
        // Another Example: §5+§d30 §5Runecrafting (969/1000)
        Matcher matcher = COLLECTIONS_CHAT_PATTERN.matcher(skillSection);
        if (matcher.matches()) {
            double increase = Double.parseDouble(matcher.group(1));
            String skillName = matcher.group(2);
            double currentProgress = Double.parseDouble(matcher.group(3));
            double maxProgress = Double.parseDouble(matcher.group(4));
            SkillUpdate skillUpdate = new SkillUpdate(skillName, currentProgress, maxProgress, increase);

            SkyblockLib.getSkyblockPlayer().setLastSkillUpdate(skillUpdate);
        }

        if(skillConsumer != null) {
            return skillConsumer.consumeSection(skillSection);
        } else {
            return skillSection;
        }
    }

    private String parseCharges(String tickerSection) {
        // Zombie with full charges: §a§lⓩⓩⓩⓩ§2§l§r
        // Zombie with one used charges: §a§lⓩⓩⓩ§2§lⓄ§r
        // Scorpion tickers: §e§lⓄⓄⓄⓄ§7§l§r
        // Ornate: §e§lⓩⓩⓩ§6§lⓄⓄ§r

        // Zombie uses ⓩ with color code a for usable charges, Ⓞ with color code 2 for unusable
        // Scorpion uses Ⓞ with color code e for usable tickers, Ⓞ with color code 7 for unusable
        // Ornate uses ⓩ with color code e for usable charges, Ⓞ with color code 6 for unusable
        int charges = 0;
        int maxCharges = 0;
        boolean hitUnusables = false;
        for (char character : tickerSection.toCharArray()) {
            if (!hitUnusables && (character == '7' || character == '2' || character == '6')) {
                // While the unusable tickers weren't hit before and if it reaches a grey(scorpion) or dark green(zombie)
                // or gold (ornate) color code, it means those tickers are used, so stop counting them.
                hitUnusables = true;
            } else if (character == 'Ⓞ' || character == 'ⓩ') { // Increase the ticker counts
                if (!hitUnusables) {
                    charges++;
                }
                maxCharges++;
            }
        }
        ItemCharges itemCharges = new ItemCharges(charges, maxCharges);
        SkyblockLib.getSkyblockPlayer().setCurrentItemCharges(itemCharges);
        if(chargesConsumer != null) {
            return chargesConsumer.consumeSection(tickerSection);
        } else {
            return tickerSection;
        }
    }

    /**
     * Sets the consumer on the health section.
     * This section looks like this:
     * <p>{@code §c1390/1390❤}</p> or like this:
     * <p>{@code §c1390/1390❤+§c30▅}</p>
     * if a healing wand is active.
     * The parsed information is available as {@link SkyblockPlayer#getHealth()},
     * {@link SkyblockPlayer#getMaxHealth()} and {@link SkyblockPlayer#getWandHealing()}.
     *
     * <p>Set to {@code null} to remove</p>
     *
     * @param healthConsumer The consumer
     */
    public void setHealthConsumer(ActionBarConsumer healthConsumer) {
        this.healthConsumer = healthConsumer;
    }

    /**
     * Sets the consumer on the mana section.
     * This section looks like this:
     * <p>{@code §b183/171✎ Mana§r}</p>
     * The parsed information is available as {@link SkyblockPlayer#getMana()} and
     * {@link SkyblockPlayer#getMaxMana()}.
     *
     * <p>Set to {@code null} to remove</p>
     *
     * @param manaConsumer The consumer
     */
    public void setManaConsumer(ActionBarConsumer manaConsumer) {
        this.manaConsumer = manaConsumer;
    }

    /**
     * Sets the consumer on the defense section.
     * This section looks like this:
     * <p>{@code §a720§a❈ Defense}</p>
     * The parsed information is available as {@link SkyblockPlayer#getDefense()}.
     *
     * <p>Set to {@code null} to remove</p>
     *
     * @param defenseConsumer The consumer
     */
    public void setDefenseConsumer(ActionBarConsumer defenseConsumer) {
        this.defenseConsumer = defenseConsumer;
    }

    /**
     * Sets the consumer on the skill section.
     * This section looks like this:
     * <p>{@code §3+10.9 Combat (313,937.1/600,000)}</p> or like this:
     * <p>{@code §5+§d30 §5Runecrafting (969/1000)}</p>
     * The parsed information is available as {@link SkyblockPlayer#getLastSkillUpdate()}.
     *
     * <p>Set to {@code null} to remove</p>
     *
     * @param skillConsumer The consumer
     */
    public void setSkillConsumer(ActionBarConsumer skillConsumer) {
        this.skillConsumer = skillConsumer;
    }

    /**
     * Sets the consumer on the charges section.
     * This means the charges for the Zombie Sword or the tickers for the Scorpion foil.
     * This section can have multiple shapes:
     * <p>Zombie with full charges: {@code §a§lⓩⓩⓩⓩ§2§l§r}</p>
     * <p>Zombie with one used charges: {@code §a§lⓩⓩⓩ§2§lⓄ§r}</p>
     * <p>Scorpion tickers: {@code §e§lⓄⓄⓄⓄ§7§l§r}</p>
     * <p>Ornate: {@code §e§lⓩⓩⓩ§6§lⓄⓄ§r}</p>
     * Important to note here is that Zombie uses {@code ⓩ} with color code {@code a} for usable charges and {@code Ⓞ}
     * with color code {@code 2} for unusable charges.<br/>
     * Scorpion uses {@code Ⓞ} with color code {@code e} for usable tickers, {@code Ⓞ} with color code {@code 7} for unusable charges.<br/>
     * Ornate uses {@code ⓩ} with color code {@code e} for usable charges, {@code Ⓞ} with color code {@code 6} for unusable.<br/>
     * The parsed information is available as {@link SkyblockPlayer#getCurrentItemCharges()}.
     *
     * <p>Set to {@code null} to remove</p>
     *
     * @param chargesConsumer The consumer
     */
    public void setChargesConsumer(ActionBarConsumer chargesConsumer) {
        this.chargesConsumer = chargesConsumer;
    }

    /**
     * Sets the general consumer that is run on every section of the action bar.
     *
     * <p>Set to {@code null} to remove</p>
     *
     * @param generalConsumer The consumer
     */
    public void setGeneralConsumer(ActionBarConsumer generalConsumer) {
        this.generalConsumer = generalConsumer;
    }
}