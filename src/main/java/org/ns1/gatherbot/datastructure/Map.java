package org.ns1.gatherbot.datastructure;

public class Map extends Voteable {
    private final String name;
    private final int mapNumber;

    public Map(String name, int mapNumber) {
        this.name = name;
        this.mapNumber = mapNumber;
    }

    @Override
    public String toString() {
        return name;
    }
}
