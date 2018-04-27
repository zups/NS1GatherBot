package org.ns1.gatherbot.controllers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.core.entities.Emote;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.util.GatherRules;
import org.ns1.gatherbot.util.TestiUser;

@NoArgsConstructor
public class Players {
    private final HashSet<Player> players = new LinkedHashSet<>();

    public Players(boolean kissa) {
        IntStream.range(0, 10).forEachOrdered(i -> addPlayer(new Player(new TestiUser(i))));
    }

    public Optional<Player> addPlayer(Player player) {
        boolean added = false;
        if (players.size() < GatherRules.getRules().getMaxPlayers()) {
            added = players.add(player);
        }

        return added ? Optional.of(player) : Optional.empty();
    }

    public Optional<Player> removePlayer(Player player) {
        return players.remove(player) ? Optional.of(player) : Optional.empty();
    }

    public List<Player> getPlayersWillingToCaptain() {
        return players.stream()
                .filter(Player::isWillingToCaptain)
                .collect(Collectors.toList());
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public int howManyWillingToCaptain() {
        return getPlayersWillingToCaptain().size();
    }

    public void updateRoles(Player playerFromParameters, Emote role, String messageId) {
        players.stream()
                .filter(player -> player.equals(playerFromParameters))
                .findFirst()
                .ifPresent(plr -> plr.updateRoles(role, messageId));
    }

    public boolean isFull() {
        return players.size() == GatherRules.getRules().getMaxPlayers();
    }

}
