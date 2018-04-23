package org.ns1.gatherbot.controllers;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.util.TestiUser;

public class PlayerController {
    private final HashSet<Player> players = new LinkedHashSet<>();
    private final int maxPlayers;

    public PlayerController(int maxSize) {
        this.maxPlayers = maxSize;
    }

    public PlayerController(int maxSize, boolean kissa) {
        this.maxPlayers = maxSize;
        IntStream.range(0, 10).forEachOrdered(i -> addPlayer(new Player(new TestiUser(i))));
    }

    public Optional<Player> addPlayer(Player player) {
        boolean added = false;
        if (players.size() < maxPlayers) {
            added = players.add(player);
        }

        return added ? Optional.of(player) : Optional.empty();
    }

    public Optional<Player> removePlayer(Player player) {
        return players.remove(player) ? Optional.of(player) : Optional.empty();
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public List<Player> getPlayersWillingToCaptain() {
        return players.stream()
                .filter(Player::isWillingToCaptain)
                .collect(Collectors.toList());
    }

    public int howManyWillingToCaptain() {
        return getPlayersWillingToCaptain().size();
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
        return maxPlayers;
    }
}