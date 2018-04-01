package org.ns1.gatherbot.datastructure;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.StringJoiner;

public class Players {
    private HashSet<Player> players = new HashSet<>();
    private int maxPlayers = 12;

    public Optional<String> addPlayer(Player player) {
        if (players.size() < maxPlayers) {
            boolean added = players.add(player);
            if (added) {
                return Optional.of("Added " + player.getUser().getName());
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

    public String printPlayers() {
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");
        StringJoiner joiner = new StringJoiner(blueSmall.getUnicode());

        players.forEach(p -> joiner.add(p.toString()));

        return "**Players "+ this.printStatus() +":**\n" + joiner.toString();
    }

    public String printStatus() {
        return "[" + players.size() + "/" + this.maxPlayers + "]";
    }
}
