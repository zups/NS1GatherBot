package org.ns1.gatherbot.controllers;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import org.ns1.gatherbot.datastructure.Captain;
import org.ns1.gatherbot.datastructure.Player;

public class PickController {
    private final Map<Integer, Player> pickables = new TreeMap();
    private TeamController teamController;
    private String pickMessageId;
    private Stream<Captain> order;
    private PeekingIterator<Captain> pickingNow;

    public PickController(List<Player> players, CaptainController captainController) {
        AtomicInteger index = new AtomicInteger(1);
        players.forEach(player -> {
            this.pickables.put(index.getAndIncrement(), player);
        });
        this.teamController = new TeamController(captainController);
        setOrder(captainController.getCaptains().get(0), captainController.getCaptains().get(1));
    }

    public Optional<Player> pick(int key, Captain captain) {
        AtomicReference<Optional<Player>> pickedPlayer = new AtomicReference<>(Optional.empty());
        if (pickingNow.peek().equals(captain)) {
            teamController.pickPlayerToTeam(captain,pickables.remove(key))
                    .ifPresent(picked -> {
                        pickedPlayer.set(Optional.of(picked));
                        pickingNow.next();
                    });
        }
        return pickedPlayer.get().isPresent() ? Optional.of(pickedPlayer.get().get()) : Optional.empty();
    }

    private void setOrder(Captain firstCaptain, Captain secondCaptain) {
        this.order = Arrays.asList(firstCaptain, secondCaptain, secondCaptain, firstCaptain, firstCaptain, secondCaptain, secondCaptain, firstCaptain, firstCaptain, secondCaptain).stream();
        this.pickingNow = Iterators.peekingIterator(order.iterator());
    }

    public void setPickMessageId(String pickMessageId) {
        if (this.pickMessageId == null) {
            this.pickMessageId = pickMessageId;
        }
    }

    public boolean pickContainsKey(int key) {
        return pickables.containsKey(key);
    }

    public TeamController getTeamController() {
        return teamController;
    }

    public String getPickMessageId() {
        return pickMessageId;
    }

    public Map<Integer, Player> getPickables() {
        return pickables;
    }
}