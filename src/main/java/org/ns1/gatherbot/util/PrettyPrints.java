package org.ns1.gatherbot.util;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
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

    public static MessageEmbed voteableEmbedded(Map<Integer,Voteable> voteable, String voteableFieldName, MiscEmojis emojis) {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner voteables = new StringJoiner("\n");
        StringJoiner voteAmount = new StringJoiner("\n");
        StringBuilder voteEmotes = new StringBuilder();

        voteable.forEach((key, value) -> {
            voteables.add("**" + key + ")** " + value +  emojis.getEmote("empty").get().getAsMention());
        });

        voteable.forEach((key, value) -> {
            for (int i = 0; i < value.getVotes(); i++) {
                voteEmotes.append(emojis.getEmote("vote").get().getAsMention());
            }
            if (voteEmotes.length() > 0) {
                voteAmount.add(voteEmotes.toString());
                voteEmotes.setLength(0);
            } else {
                voteAmount.add(emojis.getEmote("empty").get().getAsMention());
            }
        });

        return embed
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
