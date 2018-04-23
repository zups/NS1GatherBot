package org.ns1.gatherbot.controllers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Voteable;

public class VoteController {
    private String voteMessageId = "";
    private final int votesPerPlayer = 2;
    private final Map<Integer,Voteable> voteables = new TreeMap();
    private final Multimap<Player, Integer> votedPlayers = HashMultimap.create();


    public VoteController(List<? extends Voteable> voteables) {
        AtomicInteger i = new AtomicInteger(1);
        voteables.forEach(vote -> {
            this.voteables.put(i.getAndIncrement(), vote);
        });
    }

    public Optional<Integer> vote(int key, Player voter) {
        if (key > voteables.size() || !doesPlayerHaveVotesLeft(voter)) {
            return Optional.empty();
        }

        votedPlayers.put(voter, key);

        return Optional.of(voteables.get(key).vote());
    }

    public Optional<Integer> unvote(int key, Player voter) {
        if (key > voteables.size()) {
            return Optional.empty();
        }

        return votedPlayers.remove(voter, key) ? Optional.of(voteables.get(key).unvote()) : Optional.empty();
    }

    private boolean doesPlayerHaveVotesLeft(Player voter) {
        return votedPlayers.get(voter).size() < votesPerPlayer;
    }

    public boolean isThisSameVote(String messageId) {
        return messageId.equals(voteMessageId);
    }

    public void setVoteMessageId(String voteMessageId) {
        if (this.voteMessageId.isEmpty()) {
            this.voteMessageId = voteMessageId;
        }
    }

    public Map<Integer, Voteable> getVoteables() {
        return voteables;
    }

    public String getVoteMessageId() {
        return voteMessageId;
    }

}
