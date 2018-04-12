package org.ns1.gatherbot.datastructure;

import java.util.HashSet;
import java.util.Optional;

public class Captains {
    private HashSet<Captain> captains = new HashSet<>();
    private final int maxCaptains;

    public Captains(int maxCaptains) {
        this.maxCaptains = maxCaptains;
    }

    public Optional<Captain> addCaptain(Player player) {
        if (captains.size() < maxCaptains) {
            Captain captain = new Captain(player);
            boolean added = captains.add(captain);
            if (added) {
                return Optional.of(captain);
            }
            else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Optional<String> removeCaptain(Captain captain) {
        boolean removed = captains.remove(captain);
        if (removed) {
            return Optional.of("Removed " + captain.getCaptain().getUser().getName() + " as captain.");
        }
        return Optional.empty();
    }

    public HashSet<Captain> getCaptains() {
        return this.captains;
    }


}
