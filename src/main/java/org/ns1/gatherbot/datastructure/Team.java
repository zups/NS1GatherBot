package org.ns1.gatherbot.datastructure;

import java.util.Optional;

public class Team {
    private Players players;
    private Captain captain;
    private boolean isMarine;

    public Team(int teamSize, Captain captain) {
        this.players = new Players(teamSize);
        this.captain = captain;
        this.players.addPlayer(captain.getCaptain());
    }

    public Optional<Player> addPlayer(Player player) {
            return players.addPlayer(player);
    }

    public boolean isItMyTeam(Captain captain) {
        return this.captain.equals(captain);
    }

    public Players getPlayers() {
        return players;
    }
}