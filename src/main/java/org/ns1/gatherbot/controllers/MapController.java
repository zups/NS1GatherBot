package org.ns1.gatherbot.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.simple.JSONArray;
import org.ns1.gatherbot.datastructure.Map;

public class MapController {
    private final List<Map> maps = new ArrayList<>();

    public MapController(HashMap<String, JSONArray> maps) {
        parseMaps(maps.get("maps"));
    }

    private void parseMaps(JSONArray maps) {
        AtomicInteger i = new AtomicInteger(1);
        maps.forEach(map -> {
            this.maps.add(new Map(map.toString(), i.getAndIncrement()));
        });
    }

    public List<Map> getMaps() {
        return maps;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");

        maps.forEach(map -> joiner.add(map.toString()));

        return joiner.toString();
    }
}
