package org.ns1.gatherbot.util;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Voteable;
import org.ns1.gatherbot.emoji.MiscEmojis;

public class PrettyPrints {

    private static String empty = "\u200B";

    public static MessageEmbed playersEmbedded(List<Player> players) {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner joiner = new StringJoiner("\n");
        AtomicInteger i = new AtomicInteger(1);

        players.forEach(player -> joiner.add("**" + i.getAndIncrement() + ")** " + player.toString()));

        embed.addField("Players:", joiner.toString(), false);

        return embed.build();
    }

    public static String printPlayersHighlight(List<Player> players) {
        StringJoiner joiner = new StringJoiner(" ");
        players.forEach(player -> joiner.add(player.getAsMention()));

        return joiner.toString();
    }

    public static String printPlayers(List<Player> players) {
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");
        StringJoiner joiner = new StringJoiner(blueSmall.getUnicode());

        players.forEach(p -> joiner.add(p.toString()));

        return joiner.toString();
    }

    public static String printCaptains(List<Captain> players) {
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");
        StringJoiner joiner = new StringJoiner(blueSmall.getUnicode());

        players.forEach(p -> joiner.add(p.toString()));

        return joiner.toString();
    }

    public static String printMaps(List<org.ns1.gatherbot.datastructure.Map> maps) {
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");
        StringJoiner joiner = new StringJoiner(blueSmall.getUnicode());

        maps.forEach(p -> joiner.add(p.toString()));

        return joiner.toString();
    }

    /**
     *             if (value.getVotes() > 0) {
     *                 IntStream.range(0, value.getVotes())
     *                         .forEach(i -> voteAmount.add(emojis.getEmote("vote").get().getAsMention()));
     *             } else {
     *                 voteAmount.add(emojis.getEmote("empty").get().getAsMention());
     *             }
     * @param voteable
     * @param voteableFieldName
     * @param emojis
     * @param description
     * @return
     */
    public static MessageEmbed voteableEmbedded(Map<Integer,Voteable> voteable, String voteableFieldName, MiscEmojis emojis, String description) {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner voteables = new StringJoiner("\n");
        StringJoiner voteAmount = new StringJoiner("\n");
        StringBuilder voteEmotes = new StringBuilder();


        voteable.forEach((key, value) -> {
            voteables.add("**" + key + ")** " + value.toString() +  emojis.getEmote("empty").get().getAsMention());
            if (value.getVotes() > 0) {
                IntStream.range(0, value.getVotes())
                        .forEach(i -> voteEmotes.append(emojis.getEmote("vote").get().getAsMention()));
                voteAmount.add(voteEmotes.toString());
                voteEmotes.setLength(0);
            } else {
                voteAmount.add(emojis.getEmote("empty").get().getAsMention());
            }
        });


        return embed
                .setDescription(description)
                .addField(voteableFieldName, voteables.toString(), true)
                .addField("Votes:", voteAmount.toString(), true)
                .build();
    }

    public static String printStatus(List<Player> players, int maxPlayers) {
        return "**Players[" + players.size() + "/" + maxPlayers + "]:**\n";
    }

    public static String printStatusAndPlayers(List<Player> players, int maxPlayers) {
        return printStatus(players, maxPlayers) + printPlayers(players);
    }
}
