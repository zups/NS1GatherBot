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

public class PrettyPrints {

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
        players.forEach(player -> joiner.add(player.getUser().getAsMention()));

        return joiner.toString();
    }

    public static String printPlayers(List<Player> players) {
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");
        StringJoiner joiner = new StringJoiner(blueSmall.getUnicode());

        players.forEach(p -> joiner.add(p.toString()));

        return joiner.toString();
    }

    public static MessageEmbed voteableEmbedded(Map<Integer,Voteable> voteable, String voteableFieldName) {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner joiner = new StringJoiner("\n");

        voteable.forEach((key, value) -> joiner.add("**" + key + ")** " + value));

        return embed
                .addField(voteableFieldName, joiner.toString(), false)
                .build();
    }

    public static String printStatus(List<Player> players, int maxPlayers) {
        return "**Players[" + players.size() + "/" + maxPlayers + "]:**\n";
    }

    public static String printStatusAndPlayers(List<Player> players, int maxPlayers) {
        return printStatus(players, maxPlayers) + printPlayers(players);
    }
}
