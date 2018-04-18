package org.ns1.gatherbot.datastructure;

public class Captain {
    private final Player captain;
    private Team team;
    private boolean pickingRight = false;
    private int howManyPicked = 0;

    public Captain(Player player, int teamSize) {
        this.captain = player;
        this.team = new Team(teamSize, this);
    }

    public Player getCaptain() {
        return captain;
    }

    public Team getTeam() {
        return team;
    }

    public boolean setPickingRight() {
        if (pickingRight) {
            pickingRight = false;
        } else {
            pickingRight = true;
        }
        return pickingRight;
    }

    public int getHowManyPicked() {
        return howManyPicked;
    }

    public void increaseHowManyPicked() {
        howManyPicked++;
    }

    public boolean hasPickingRight() {
        return pickingRight;
    }

    public boolean isPlayerCaptain(Player player) {
        return this.captain.equals(player);
    }

    @Override
    public int hashCode() {
        return captain.hashCode();
    }

    public long getId() {
        return captain.getId();
    }

    @Override
    public boolean equals(Object obj) {
        Captain captain = (Captain) obj;
        if (captain.getId() == this.captain.getId()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return captain.toString();
    }
}
