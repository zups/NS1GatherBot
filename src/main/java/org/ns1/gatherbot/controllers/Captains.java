package org.ns1.gatherbot.controllers;

import java.util.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.util.GatherRules;

@NoArgsConstructor
public class Captains {
    private List<Captain> captains = new ArrayList<>();

    public Optional<Captain> addCaptain(Captain captain) {
        boolean added = false;
        if (captains.size() < GatherRules.getRules().getMaxCaptains()) {
            added = captains.add(captain);
        }
        return added ? Optional.of(captain) : Optional.empty();
    }

    public Optional<Captain> removeCaptain(Captain captain) {
        return captains.remove(captain) ? Optional.of(captain) : Optional.empty();
    }

    public List<Captain> getCaptains() {
        return new ArrayList<>(captains);
    }

    public void setPickingTurn(Captain captain) {

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
