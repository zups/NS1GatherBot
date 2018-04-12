package org.ns1.gatherbot.datastructure;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Vote {
    private final Map<Integer,Voteable> voteables = new TreeMap();

    public Vote(List<? extends Voteable> voteables) {
        AtomicInteger i = new AtomicInteger(1);
        voteables.forEach(vote -> {
            this.voteables.put(i.getAndIncrement(), vote);
        });
    }

    public Optional<Integer> vote(int key) {
        if (key > voteables.size()) {
            return Optional.empty();
        }

        return Optional.of(voteables.get(key).vote());
    }

    public Optional<Integer> unvote(int key) {
        if (key > voteables.size()) {
            return Optional.empty();
        }

        return Optional.of(voteables.get(key).unvote());
    }

    public Map<Integer, Voteable> getVoteables() {
        return this.voteables;
    }

}
