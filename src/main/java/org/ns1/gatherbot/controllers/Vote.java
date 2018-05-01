package org.ns1.gatherbot.controllers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;
import org.ns1.gatherbot.datastructure.Player;
import org.ns1.gatherbot.datastructure.Voteable;
import org.ns1.gatherbot.util.GatherRules;

public class Vote {
    @Getter private final Map<Integer, Voteable> voteables = new TreeMap();
    private final Multimap<Player, Integer> votedPlayers = HashMultimap.create();
    private final int votesPerPlayer = GatherRules.getRules().getVotesPerPlayer();
    private final List<Player> allowedToVote;
    @Getter @Setter private String voteMessageId = "";
    @Getter private String voteName;
    @Getter private String description;


    public Vote(List<? extends Voteable> voteables, List<Player> allowedToVote, String voteName, String description) {
        this.voteName = voteName;
        this.description = description;
        this.allowedToVote = allowedToVote;
        AtomicInteger i = new AtomicInteger(1);
        voteables.forEach(vote -> {
            this.voteables.put(i.getAndIncrement(), vote);
        });
    }

    public boolean vote(int key, Player voter) {
        if (!isPlayerAllowedToVote(voter) || key > voteables.size() || !doesPlayerHaveVotesLeft(voter)) {
            return false;
        }

        votedPlayers.put(voter, key);
        voteables.get(key).vote();

        return true;
    }

    public boolean unvote(int key, Player voter) {
        if (!isPlayerAllowedToVote(voter) && key > voteables.size()) {
            return false;
        }
        else if (votedPlayers.remove(voter, key)) {
            voteables.get(key).unvote();
            return true;
         }

        return false;
    }

    private boolean doesPlayerHaveVotesLeft(Player voter) {
        return votedPlayers.get(voter).size() < votesPerPlayer;
    }

    public boolean isThisSameVote(String messageId) {
        return messageId.equals(voteMessageId);
    }

    public boolean isPlayerAllowedToVote(Player player) {
        return allowedToVote.contains(player);
    }
}
