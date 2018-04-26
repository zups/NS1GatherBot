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
    private static Maps instance;

    private Maps() {
        Optional<JSONObject> mapsJson = Utils.readJson("src\\main\\resources\\maps.json");
        if (mapsJson.isPresent()) {
            HashMap<String, JSONArray> mapsFromJson = mapsJson.get();
            AtomicInteger i = new AtomicInteger(1);
            mapsFromJson.get("maps").forEach(map -> {
                this.maps.add(new Map(map.toString(), i.getAndIncrement()));
            });
        }
    }

    public static Maps getInstance() {
        if (instance == null) {
            instance = new Maps();
        }
        return instance;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");

        maps.forEach(map -> joiner.add(map.getMapNumber() + ") " + map.getName()));

        return joiner.toString();
    }
}
