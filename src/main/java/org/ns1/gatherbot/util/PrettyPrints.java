package org.ns1.gatherbot.util;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.ns1.gatherbot.controllers.Pick;
import org.ns1.gatherbot.controllers.Vote;
import org.ns1.gatherbot.datastructure.*;
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

    public static MessageEmbed pickEmbedded(Pick pick) {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner pickables = new StringJoiner("\n");
        AtomicInteger teamNumber = new AtomicInteger(1);

        pick.getPickables().forEach((number, player) -> {
            pickables.add("**" + number + ")** " + player.toString());
        });

        if (pickables.length() > 0 )
            embed.addField("Players:", pickables.toString(), true);

        pick.getTeams().teams.forEach(team -> {
            embed.addField("Team" + teamNumber.getAndIncrement(), team.toString(), true);
        });

        return embed.setDescription("_Pick players by cliking the smileys._").build();
    }

    public static MessageEmbed voteEmbedded(Vote vote) {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner voteables = new StringJoiner("\n");
        StringJoiner voteAmount = new StringJoiner("\n");
        MiscEmojis emojis = MiscEmojis.getEmojis();


        vote.getVoteables().forEach((key, value) -> {
            voteables.add("**" + key + ")** " + value.toString() +  emojis.getEmote("empty").get().getAsMention());
            voteAmount.add(voteEmojis(value.getVotes(), emojis));
        });


        return embed
                .setDescription(vote.getDescription())
                .addField(vote.getVoteName(), voteables.toString(), true)
                .addField("Votes:", voteAmount.toString(), true)
                .build();
    }

    public static String voteEmojis(int amount, MiscEmojis emojis) {
        StringBuilder voteEmotes = new StringBuilder();

        IntStream.range(0, amount)
                .forEach(i -> voteEmotes.append(emojis.getEmote("vote").get().getAsMention()));

        return voteEmotes.length() > 0 ? voteEmotes.toString() : emojis.getEmote("empty").get().getAsMention();
    }

    public static String printStatus(List<Player> players, int maxPlayers) {
        return "**Players[" + players.size() + "/" + maxPlayers + "]:**\n";
    }

    public static String printStatusAndPlayers(List<Player> players, int maxPlayers) {
        return printStatus(players, maxPlayers) + printPlayers(players);
    }
}
