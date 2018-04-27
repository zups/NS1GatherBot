package org.ns1.gatherbot.datastructure;

import lombok.Getter;

public abstract class Voteable {
    @Getter private int votes = 0;

    public int vote() {
        return ++votes;
    }

    public int unvote() {
        return --votes;
    }

    @Override
    public abstract String toString();
}
