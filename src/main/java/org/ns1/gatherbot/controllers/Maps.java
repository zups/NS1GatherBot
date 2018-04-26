package org.ns1.gatherbot.controllers;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.ns1.gatherbot.datastructure.Map;
import org.ns1.gatherbot.util.Utils;

public class Maps {
    @Getter private final List<Map> maps = new ArrayList<>();

    public Maps(HashMap<String, JSONArray> maps) {
        parseMaps(maps.get("maps"));
    }

    private void parseMaps(JSONArray maps) {
        AtomicInteger i = new AtomicInteger(1);
        maps.forEach(map -> {
            this.maps.add(new Map(map.toString(), i.getAndIncrement()));
        });
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");

        maps.forEach(map -> joiner.add(map.toString()));

        return joiner.toString();
    }
}
