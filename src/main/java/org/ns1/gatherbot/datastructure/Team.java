package org.ns1.gatherbot.datastructure;

import java.util.Optional;
import java.util.StringJoiner;
import org.ns1.gatherbot.controllers.PlayerController;

public class Team {
    private PlayerController playerController;
    private Captain captain;

    public Team(int teamSize, Captain captain) {
        this.playerController = new PlayerController(teamSize);
        this.captain = captain;
    }

    public Optional<Player> pickPlayer(Player player) {
            return playerController.addPlayer(player);
    }

    public boolean isItMyTeam(Captain captain) {
        return this.captain.equals(captain);
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    @Override
    public String toString() {
        StringJoiner team = new StringJoiner("\n");
        team.add("**" + captain.toString() + "**");

        playerController.getPlayers().forEach(player -> {
            team.add(player.toString());
        });

        return team.toString();
    }
}