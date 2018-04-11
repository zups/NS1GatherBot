package org.ns1.gatherbot.datastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.User;
import org.ns1.gatherbot.emoji.NumberEmojis;

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

    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
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
        HashSet<Player> sortedPlayers = players.stream().sorted()
                .peek(player -> numberEmojis.getEmoteForNumber(i.getAndIncrement())
                        .ifPresent(number -> player.setNumber(number))
                )
                .collect(Collectors.toCollection(HashSet::new));

        this.players = sortedPlayers;
        return this.players;
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
