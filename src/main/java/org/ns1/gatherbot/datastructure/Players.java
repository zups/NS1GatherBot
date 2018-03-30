package org.ns1.gatherbot.datastructure;

import java.util.HashSet;
import java.util.Optional;

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
        return Optional.of("Gather is full.");
    }

    public Optional<String> removePlayer(Player player) {
        boolean removed = players.remove(player);
        if (removed) {
            return Optional.of("Removed " + player.getUser().getName());
        }
        return Optional.empty();
    }

    public String printPlayers() {
        return players.toString();
    }
}
