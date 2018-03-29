package org.ns1.gather.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Players {
    private List<Player> players = new ArrayList<>();

    public String addPlayer(Player player) {
        players.add(player);
        return "Added" + player.getUser().getName();
    }

    public String removePlayer(Player player) {
        players.remove(player);

        return "Removed" + player.getUser().getName();
    }
}
