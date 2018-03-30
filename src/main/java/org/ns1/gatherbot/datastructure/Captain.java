package org.ns1.gatherbot.datastructure;

public class Captain {
    private Player captain;

    public Captain(Player player) {
        this.captain = player;
    }

    public Player getCaptain() {
        return this.captain;
    }
}
