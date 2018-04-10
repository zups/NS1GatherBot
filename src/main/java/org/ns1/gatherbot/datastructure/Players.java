package org.ns1.gatherbot.datastructure;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class Players {
    private HashSet<Player> players = new HashSet<>();
    private int maxPlayers;

    public Players(int maxSize) {
        this.maxPlayers = maxSize;
    }

    public Optional<Player> addPlayer(Player player) {
        if (players.size() < maxPlayers) {
            boolean added = players.add(player);
            if (added) {
                return Optional.of(player);
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Optional<String> removePlayer(Player player) {
        boolean removed = players.remove(player);
        if (removed) {
            return Optional.of("Removed " + player.getUser().getName());
        }
        return Optional.empty();
    }

    public HashSet<Player> getPlayers() {
        return (HashSet<Player>) this.players.clone();
    }

    public List<Player> getPlayersWillingToCaptain() {
        return players.stream()
                .filter(player -> player.isWillingToCaptain()).collect(Collectors.toList());
    }

    public boolean isThereMoreThanTwoWillingToCaptain() {
        return getPlayersWillingToCaptain().size() >= 2;
    }

    public HashSet<Player> sortAndAssignNumbers(NumberEmojis numberEmojis) {
        AtomicInteger i = new AtomicInteger(1);
        HashSet<Player> sortedPlayers =  players.stream().sorted()
                .peek(player -> numberEmojis.getEmoteForNumber(i.getAndIncrement())
                            .ifPresent(number -> player.setNumber(number))
                )
                .collect(Collectors.toCollection(HashSet::new));

        this.players = sortedPlayers;
        return this.players;
    }

    public MessageEmbed playersEmbedded() {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner joiner = new StringJoiner("\n");
        AtomicInteger i = new AtomicInteger(1);

        players.forEach(player -> joiner.add("**" + i.getAndIncrement() + ")** " + player.toString()));

        embed.addField("Players:", joiner.toString(), false);

        return embed.build();
    }

    public MessageEmbed captainsEmbedded() {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner joiner = new StringJoiner("\n");
        AtomicInteger i = new AtomicInteger(1);

        getPlayersWillingToCaptain().forEach(captain -> joiner.add("**" + i.getAndIncrement() + ")** " + captain.toString()));

        embed.addField("Players:", joiner.toString(), false);

        return embed.build();
    }

    public String printPlayersHighlight() {
        StringJoiner joiner = new StringJoiner(" ");
        players.forEach(player -> joiner.add(player.getUser().getAsMention()));

        return joiner.toString();
    }

    public boolean isFull() {
        return players.size() == maxPlayers;
    }

    public String printPlayers() {
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");
        StringJoiner joiner = new StringJoiner(blueSmall.getUnicode());


        players.forEach(p -> joiner.add(p.toString()));

        return "**Players " + this.printStatus() + ":**\n" + joiner.toString();
    }

    public void updateRoles(User user, Emote role, String messageId) {
        players.stream()
                .filter(player -> player.equals(new Player(user)))
                .findFirst()
                .ifPresent(plr -> plr.updateRoles(role, messageId));
    }

    public String printStatus() {
        return "[" + players.size() + "/" + this.maxPlayers + "]";
    }
}
