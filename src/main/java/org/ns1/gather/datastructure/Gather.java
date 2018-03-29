package org.ns1.gather.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Gather {
    private Players players = new Players();
    private List<Captain> captains = new ArrayList<>();
    private List<Map> maps = new ArrayList<>();
    private List<Team> teams = new ArrayList<>();

    public String addPlayer(Player player) {
        return players.addPlayer(player);
    }


}
