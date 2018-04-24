package org.ns1.gatherbot.util;

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

    public int getHowManyMaps() {
        return howManyMaps;
    }

    private void setHowManyMaps(int howManyMaps) {
        this.howManyMaps = howManyMaps;
    }

    public int getVotesPerPlayer() {
        return votesPerPlayer;
    }

    private void setVotesPerPlayer(int votesPerPlayer) {
        this.votesPerPlayer = votesPerPlayer;
    }
}
