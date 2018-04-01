package org.ns1.gatherbot.datastructure;

public class Map {
    private String name;
    private int mapNumber;

    public Map(String name, int mapNumber) {
        this.name = name;
        this.mapNumber = mapNumber;
    }

    public String getName() {
        return "**" + mapNumber + ")** " +this.name;
    }
}
