package org.ns1.gatherbot.datastructure;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Maps {
    private List<Map> maps = new ArrayList<>();

    public Maps(HashMap<String, JSONArray> maps) {
        parseMaps(maps.get("maps"));
    }

    private void parseMaps (JSONArray maps) {
        maps.forEach(map -> {
            this.maps.add(new Map(map.toString()));
        });
    }

    public List<Map> getMaps() {
        return this.maps;
    }
}
