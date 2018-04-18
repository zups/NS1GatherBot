package org.ns1.gatherbot.datastructure;

import java.util.*;

public class Captains {
    private List<Captain> captains = new ArrayList<>();
    private final int maxCaptains;

    public Captains(int maxCaptains) {
        this.maxCaptains = maxCaptains;
    }

    public Optional<Captain> addCaptain(Captain captain) {
        boolean added = false;
        if (captains.size() < maxCaptains) {
            added = captains.add(captain);
        }
        return added ? Optional.of(captain) : Optional.empty();
    }

    public Optional<Captain> removeCaptain(Captain captain) {
        return captains.remove(captain) ? Optional.of(captain) : Optional.empty();
    }

    public void setPickingTurn(Captain captain) {

    }

    public List<Captain> getCaptains() {
        return captains;
    }

    public Optional<Captain> getCaptain(Captain captain) {

        for (Captain captainLooking : captains) {
            if (captainLooking.equals(captain)) {
                return Optional.of(captainLooking);
            }
        }

        return Optional.empty();
    }

    public String getCaptainsAsMention() {
        StringBuilder string = new StringBuilder();

        captains.forEach(capt -> {
            string.append(capt.getCaptain().getAsMention() + " ");
        });

        return string.toString();
    }


}
