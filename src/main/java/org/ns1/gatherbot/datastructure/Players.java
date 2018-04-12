package org.ns1.gatherbot.datastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;

public class Players {
    private final HashSet<Player> players = new HashSet<>();
    private final int maxPlayers;

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

    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
    }

    public List<Player> getPlayersWillingToCaptain() {
        return players.stream()
                .filter(player -> player.isWillingToCaptain()).collect(Collectors.toList());
    }

    public boolean isThereMoreWillingToCaptain(int amount) {
        return getPlayersWillingToCaptain().size() >= amount;
    }

    public void updateRoles(User user, Emote role, String messageId) {
        players.stream()
                .filter(player -> player.equals(new Player(user)))
                .findFirst()
                .ifPresent(plr -> plr.updateRoles(role, messageId));
    }

    public boolean isFull() {
        return players.size() == maxPlayers;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }
}
