package org.ns1.gatherbot.datastructure;

public abstract class Voteable {
    private int votes = 0;

    public int vote() {
        return ++votes;
    }

    public int unvote() {
        return --votes;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public abstract String toString();
}
