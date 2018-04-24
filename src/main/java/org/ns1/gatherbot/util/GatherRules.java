package org.ns1.gatherbot.util;

public class GatherRules {
    private int maxPlayers = 12;
    private int maxCaptains = 2;
    private int teamSize = 6;
    private final String rulesPath = "src\\main\\resources\\rules.json";
    private static GatherRules rules;

    private GatherRules() {
        Utils.readFieldFromJson(rulesPath, "maxPlayers").ifPresent(field -> setMaxPlayers(Integer.parseInt(field)));
        Utils.readFieldFromJson(rulesPath, "maxCaptains").ifPresent(field -> setMaxCaptains(Integer.parseInt(field)));
        Utils.readFieldFromJson(rulesPath, "teamSize").ifPresent(field -> setTeamSize(Integer.parseInt(field)));
    }

    public static GatherRules getRules() {
        if (rules == null) {
            rules = new GatherRules();
        }
        return rules;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    private void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMaxCaptains() {
        return maxCaptains;
    }

    private void setMaxCaptains(int maxCaptains) {
        this.maxCaptains = maxCaptains;
    }

    public int getTeamSize() {
        return teamSize;
    }

    private void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
}
