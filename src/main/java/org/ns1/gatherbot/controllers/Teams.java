package org.ns1.gatherbot.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Team;

public class Teams {
    public List<Team> teams = new ArrayList<>();

    public Teams(Captains captains) {
        captains.getCaptains().forEach(captain -> {
            teams.add(new Team(captain));
        });
    }

    public Optional<Player> pickPlayerToTeam(Captain captain, Player player) {
        return teams.stream().filter(team -> team.isItMyTeam(captain))
                .findFirst().get().pickPlayer(player);

    }

    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

}
