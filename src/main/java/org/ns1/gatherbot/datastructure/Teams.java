package org.ns1.gatherbot.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Teams {
    public List<Team> teams = new ArrayList<>();

    public Teams(Captains captains) {
        captains.getCaptains().forEach(captain -> {
            teams.add(new Team(6, captain));
        });
    }

    public Optional<Player> addPlayerToTeam(Captain captain, Player player) {
        return teams.stream().filter(team -> team.isItMyTeam(captain))
                .findFirst().get().addPlayer(player);

    }

}
