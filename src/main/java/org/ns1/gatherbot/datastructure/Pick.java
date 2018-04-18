package org.ns1.gatherbot.datastructure;

import java.util.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Pick {
    private final Map<Integer, Player> pickables = new TreeMap();
    private Captains captains;
    private String pickMessageId;
    private Stream<Captain> order;
    private Iterator<Captain> pickingNow;

    public Pick(List<Player> players, Captains captains) {
        AtomicInteger i = new AtomicInteger(1);
        players.forEach(player -> {
            this.pickables.put(i.getAndIncrement(), player);
        });
        this.captains = captains;
        setOrder(captains.getCaptains().get(0), captains.getCaptains().get(1));
    }

    public Optional<Player> pick(int key, Captain captain) {
        Optional<Player> pickedPlayer = Optional.empty();
        if (pickingNow.next().equals(captain)) {
            captains.getCaptain(captain).ifPresent(capt -> {
                capt.getTeam().pickPlayer(pickables.remove(key));
            });
        }
        return pickedPlayer;
    }

    private void setOrder(Captain firstCaptain, Captain secondCaptain) {
        this.order = Arrays.asList(firstCaptain, secondCaptain, secondCaptain, firstCaptain, firstCaptain, secondCaptain, secondCaptain, firstCaptain, firstCaptain, secondCaptain).stream();
        this.pickingNow = order.iterator();
    }

    public void setPickMessageId(String pickMessageId) {
        if (this.pickMessageId == null) {
            this.pickMessageId = pickMessageId;
        }
    }

    public String getPickMessageId() {
        return pickMessageId;
    }

    public List<Captain> getCaptains() {
        return captains.getCaptains();
    }

    public Map<Integer, Player> getPickables() {
        return pickables;
    }
}