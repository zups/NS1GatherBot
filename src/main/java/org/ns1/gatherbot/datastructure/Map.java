package org.ns1.gatherbot.datastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Map extends Voteable {
    @Getter private final String name;
    @Getter private final int mapNumber;

    @Override
    public String toString() {
        return name;
    }
}
