package org.ns1.gatherbot.datastructure;

import org.json.simple.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class Maps {
    private List<Map> maps = new ArrayList<>();
    private int mapNumbers = 1;

    public Maps(HashMap<String, JSONArray> maps) {
        parseMaps(maps.get("maps"));
    }

    private void parseMaps (JSONArray maps) {
        maps.forEach(map -> {
            this.maps.add(new Map(map.toString(), mapNumbers));
            mapNumbers++;
        });
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");

        this.maps.forEach(map -> joiner.add(map.toString()));

        return joiner.toString();
    }
}
