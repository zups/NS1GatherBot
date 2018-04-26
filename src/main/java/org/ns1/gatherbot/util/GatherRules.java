package org.ns1.gatherbot.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
        Utils.readFieldFromJson(rulesPath, "maxPlayers").ifPresent(field -> setMaxPlayers(Integer.parseInt(field)));
        Utils.readFieldFromJson(rulesPath, "maxCaptains").ifPresent(field -> setMaxCaptains(Integer.parseInt(field)));
        Utils.readFieldFromJson(rulesPath, "teamSize").ifPresent(field -> setTeamSize(Integer.parseInt(field)));
        Utils.readFieldFromJson(rulesPath, "howManyMaps").ifPresent(field -> setHowManyMaps(Integer.parseInt(field)));
        Utils.readFieldFromJson(rulesPath, "votesPerPlayer").ifPresent(field -> setVotesPerPlayer(Integer.parseInt(field)));
    }

    public static GatherRules getRules() {
        if (rules == null) {
            rules = new GatherRules();
        }
        return rules;
    }
}
