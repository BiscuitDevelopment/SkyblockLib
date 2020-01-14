package codes.biscuit.skyblocklib.listener;

import codes.biscuit.skyblocklib.SkyblockLib;
import codes.biscuit.skyblocklib.event.SkyblockJoinedEvent;
import codes.biscuit.skyblocklib.event.SkyblockLeftEvent;
import codes.biscuit.skyblocklib.item.PowerOrbType;
import codes.biscuit.skyblocklib.managers.PowerOrbManager;
import codes.biscuit.skyblocklib.parsers.ScoreboardParser;
import codes.biscuit.skyblocklib.utils.TextUtils;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Listens to various tick events
 */
public class TickListener {

    private static final Set<String> SKYBLOCK_IN_ALL_LANGUAGES = Sets.newHashSet("SKYBLOCK","\u7A7A\u5C9B\u751F\u5B58");

    private final EventBus EVENT_BUS;
    private final ScoreboardParser scoreboardParser;

    public TickListener(EventBus EVENT_BUS) {
        this.EVENT_BUS = EVENT_BUS;
        scoreboardParser = new ScoreboardParser(EVENT_BUS);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        if(world != null) {
            Scoreboard scoreboard = world.getScoreboard();
            ScoreObjective sidebarObjective = world.getScoreboard().getObjectiveInDisplaySlot(1);
            if (sidebarObjective != null) {
                String objectiveName = TextUtils.stripColor(sidebarObjective.getDisplayName());

                // Check if the scoreboard is a Skyblock scoreboard by checking the display name
                // against the SKYBLOCK title in various languages
                boolean skyblockScoreboard = false;
                for (String skyblock : SKYBLOCK_IN_ALL_LANGUAGES) {
                    if (objectiveName.startsWith(skyblock)) {
                        skyblockScoreboard = true;
                    }
                }

                if (skyblockScoreboard) {
                    // If it's a Skyblock scoreboard and the player has not joined Skyblock yet,
                    // this indicates that he did so.
                    if(!SkyblockLib.getSkyblock().isOnSkyblock()) {
                        EVENT_BUS.post(new SkyblockJoinedEvent());
                    }
                } else {
                    // If it's not a Skyblock scoreboard, the player must have left Skyblock and
                    // be in some other Hypixel lobby or game.
                    if(SkyblockLib.getSkyblock().isOnSkyblock()) {
                        EVENT_BUS.post(new SkyblockLeftEvent());
                    }
                }

                // Get a list of the lines in the scoreboard to parse them
                Collection<Score> scoreboardScores = scoreboard.getSortedScores(sidebarObjective);
                List<Score> list = Lists.newArrayList(scoreboardScores.stream().filter(p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")).collect(Collectors.toList()));
                if (list.size() > 15) {
                    scoreboardScores = Lists.newArrayList(Iterables.skip(list, scoreboardScores.size() - 15));
                } else {
                    scoreboardScores = list;
                }
                List<String> scoreboardLines = scoreboardScores
                        .stream()
                        .map(line ->
                                ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(line.getPlayerName()), line.getPlayerName()))
                        .collect(Collectors.toList());

                // Parse the scoreboard lines
                scoreboardParser.parseScoreboard(scoreboardLines);
            }
        }
    }

    @SubscribeEvent
    public void onEntityEvent(LivingEvent.LivingUpdateEvent e) {
        if(!SkyblockLib.isOnSkyblock()) {
            return;
        }

        Entity entity = e.entity;

        if (entity instanceof EntityArmorStand && entity.hasCustomName()) {
            String customNameTag = entity.getCustomNameTag();

            PowerOrbType powerOrbType = PowerOrbType.getByOrbDisplayname(customNameTag);
            if (powerOrbType != null
                    && Minecraft.getMinecraft().thePlayer != null
                    && powerOrbType.isInRadius(entity.getPosition().distanceSq(Minecraft.getMinecraft().thePlayer.getPosition()))) {
                String[] customNameTagSplit = customNameTag.split(" ");
                String secondsString = customNameTagSplit[customNameTagSplit.length - 1]
                        .replaceAll("§e", "")
                        .replaceAll("s", "");
                try {
                    // Apparently they don't have a second count for a moment after spawning, that's what this try-catch is for
                    int seconds = Integer.parseInt(secondsString);
                    PowerOrbManager.getInstance().put(powerOrbType, seconds);
                } catch (NumberFormatException ignored) { }
            }
        }
    }

}
