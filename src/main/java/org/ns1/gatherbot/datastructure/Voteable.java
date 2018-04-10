package org.ns1.gatherbot.datastructure;

public abstract class Voteable {
    private int votes = 0;

    public int vote() {
        return ++votes;
    }



}
