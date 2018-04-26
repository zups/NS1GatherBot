package org.ns1.gatherbot.util;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter @Setter(AccessLevel.PRIVATE)
public class GatherRules {
    private int maxPlayers = 12;
    private int maxCaptains = 2;
    private int teamSize = 6;
    private int howManyMaps = 2;
    private int votesPerPlayer = 2;
    private final String rulesPath = "src\\main\\resources\\rules.json";
    private static GatherRules rules;

    private GatherRules() {
        Optional<JSONObject> jsonObject = Utils.readJson(rulesPath);
        if (jsonObject.isPresent()) {
            JSONObject json = jsonObject.get();
            setMaxPlayers(Integer.parseInt((String) json.get("maxPlayers")));
            setMaxCaptains(Integer.parseInt((String) json.get("maxCaptains")));
            setTeamSize(Integer.parseInt((String) json.get("teamSize")));
            setHowManyMaps(Integer.parseInt((String) json.get("howManyMaps")));
            setVotesPerPlayer(Integer.parseInt((String) json.get("votesPerPlayer")));
        }
 }

    public static GatherRules getRules() {
        if (rules == null) {
            rules = new GatherRules();
        }
        return rules;
    }
}
