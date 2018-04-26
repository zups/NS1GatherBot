package org.ns1.gatherbot.datastructure;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.StringJoiner;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Team {
    private final HashSet<Player> players = new LinkedHashSet<>();
    private Captain captain;

    public Optional<Player> pickPlayer(Player player) {
            return players.add(player) ? Optional.of(player) : Optional.empty();
    }

    public boolean isItMyTeam(Captain captain) {
        return this.captain.equals(captain);
    }

    @Override
    public String toString() {
        StringJoiner team = new StringJoiner("\n");
        team.add("**" + captain.toString() + "**");

        players.forEach(player -> {
            team.add(player.toString());
        });

        return team.toString();
    }
}