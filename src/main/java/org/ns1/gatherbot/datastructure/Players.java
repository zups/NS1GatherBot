package org.ns1.gatherbot.datastructure;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import java.util.HashSet;
import java.util.Optional;
import java.util.StringJoiner;

public class Players {
    private HashSet<Player> players = new HashSet<>();
    private int maxPlayers = 12;

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

    public MessageEmbed playersEmbedded() {
        EmbedBuilder embed = new EmbedBuilder();
        StringJoiner joiner = new StringJoiner("\n");

        players.forEach(player -> joiner.add(player.toString()));

        embed.addField("Players:", joiner.toString(), false);

        return embed.build();
    }

    public boolean isFull() {
        return players.size() == maxPlayers;
    }

    public String printPlayers() {
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");
        StringJoiner joiner = new StringJoiner(blueSmall.getUnicode());


        players.forEach(p -> joiner.add(p.toString()));

        return "**Players "+ this.printStatus() +":**\n" + joiner.toString();
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
