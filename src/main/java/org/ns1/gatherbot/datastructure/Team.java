package org.ns1.gatherbot.datastructure;

import java.util.Optional;
import java.util.StringJoiner;

public class Team {
    private Players players;
    private Captain captain;
    private boolean isMarine;

    public Team(int teamSize, Captain captain) {
        this.players = new Players(teamSize);
        this.captain = captain;
    }

    public Optional<Player> pickPlayer(Player player) {
            return players.addPlayer(player);
    }

    public boolean isItMyTeam(Captain captain) {
        return this.captain.equals(captain);
    }

    public Players getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        StringJoiner team = new StringJoiner("\n");
        team.add("**" + captain.toString() + "**");

        players.getPlayers().forEach(player -> {
            team.add(player.toString());
        });

        return team.toString();
    }
}