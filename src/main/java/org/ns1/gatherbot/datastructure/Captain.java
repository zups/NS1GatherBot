package org.ns1.gatherbot.datastructure;

public class Captain {
    private final Player captain;

    public Captain(Player player) {
        this.captain = player;
    }

    public Player getCaptain() {
        return captain;
    }

    public boolean isPlayerCaptain(Player player) {
        return this.captain.equals(player);
    }

    @Override
    public int hashCode() {
        return captain.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Captain captain = (Captain) obj;
        if (captain.hashCode() == this.captain.hashCode()) {
            return true;
        }
        return false;
    }
}
