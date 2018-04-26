package org.ns1.gatherbot.datastructure;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Map extends Voteable {
    private final String name;
    private final int mapNumber;

    @Override
    public String toString() {
        return name;
    }
}
